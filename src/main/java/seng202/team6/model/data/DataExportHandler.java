package seng202.team6.model.data;

import seng202.team6.model.entities.Airline;
import seng202.team6.model.entities.Airport;
import seng202.team6.model.entities.Route;
import seng202.team6.model.entities.RoutePath;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static java.lang.String.format;

public class DataExportHandler {
    private static DataExportHandler Instance;
    private final Connection databaseConnection;

    /**
     * Singleton method to ensure only one instance to the class at one time
     * @return Single DataExportHandler object
     */
    public static DataExportHandler GetInstance() {
        if (Instance == null) {
            Instance = new DataExportHandler();
        }
        return Instance;
    }

    /**
     * Private constructor for class, grabs database connection
     */
    private DataExportHandler() {
        databaseConnection = DataHandler.GetInstance().GetConnection();
    }

    /**
     * Extract list of airlines from result set
     * @param resultSet Results from query
     * @return List of airlines
     * @throws SQLException SQLException
     */
    private ArrayList<Airline> ExtractAirlines(ResultSet resultSet) throws SQLException {
        ArrayList<Airline> airlines = new ArrayList<>();
        // Loop through the result set and create Airline objects from data
        while (resultSet.next()) {
            String active = resultSet.getString("active");
            char char_active = (active.length() > 0 ? active.charAt(0) : ' ');
            Airline airline = new Airline(
                    resultSet.getInt("id_airline"), resultSet.getString("name"), resultSet.getString("alias"),
                    resultSet.getString("iata"), resultSet.getString("icao"), resultSet.getString("callsign"),
                    resultSet.getString("country"), char_active
            );
            airlines.add(airline);
        }

        return airlines;
    }

    /**
     * Extract list of airports from result set
     * @param resultSet Results from query
     * @return List of airports
     * @throws SQLException SQLException
     */
    private ArrayList<Airport> ExtractAirports(ResultSet resultSet) throws SQLException {
        ArrayList<Airport> airports = new ArrayList<>();
        // Loop through the result set and create Airport objects from data
        while (resultSet.next()) {
            String dst = resultSet.getString("dst");
            char char_dst = (dst.length() > 0 ? dst.charAt(0) : ' ');
            Airport airport = new Airport(
                    resultSet.getInt("id_airport"), resultSet.getString("name"), resultSet.getString("city"),
                    resultSet.getString("country"), resultSet.getString("iata"), resultSet.getString("icao"),
                    resultSet.getFloat("latitude"), resultSet.getFloat("longitude"), resultSet.getInt("altitude"),
                    resultSet.getInt("timezone"), char_dst
            );
            airports.add(airport);
        }
        return airports;
    }

    /**
     * Extract list of routes from result set
     * @param resultSet Results from query
     * @return List of routes
     * @throws SQLException SQLException
     */
    private ArrayList<Route> ExtractRoutes(ResultSet resultSet) throws SQLException {
        ArrayList<Route> routes = new ArrayList<>();
        // Loop through the result set and create Airport objects from data
        while (resultSet.next()) {
            String codeshare = resultSet.getString("codeshare");
            char char_codeshare = (codeshare.length() > 0 ? codeshare.charAt(0) : 'N');
            Route route = new Route(
                    resultSet.getInt("id_airline"), resultSet.getString("airline"), resultSet.getString("source_airport"),
                    resultSet.getInt("source_airport_id"), resultSet.getString("destination_airport"),
                    resultSet.getInt("destination_airport_id"), char_codeshare,
                    resultSet.getInt("stops"), resultSet.getString("equipment")
            );
            routes.add(route);
        }
        return routes;
    }

    /**
     * Select and return all the Airline tuples in the SQLite database.
     * @param filters List of filters to apply to search query
     * @return List of airline objects
     */
    public ArrayList<Airline> FetchAirlines(ArrayList<Filter> filters) {
        String query = SQLHelper.ExtractQuery("airline", filters);
        try {
            Statement stmt = this.databaseConnection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            return ExtractAirlines(rs);
        } catch (Exception ignored) {
            return null;
        }
    }

    /**
     * Select and return all the Airport tuples in the SQLite database with additional sorting based on number of routes.
     * @param filters Query filters
     * @param sortType Type of sort required, either "ASC" or "DESC"
     * @return All airports that fit the database query
     */
    public ArrayList<Airport> FetchAirports(ArrayList<Filter> filters, String sortType) {
        ArrayList<Airport> airports = new ArrayList<>();

        StringBuilder builder = new StringBuilder();
        builder.append("SELECT count(a.id_airport), a.id_airport, a.name, a.city, a.country, a.iata, a.icao, " +
                "a.latitude, a.longitude, a.altitude, a.timezone, a.dst\n" +
                "FROM airport a join route r on a.id_airport = r.source_airport_id\n");

        //If there are filters
        if (filters != null && filters.size() != 0) {
            builder.append("WHERE ");
            for (int i = 0; i < filters.size() - 1; i++) {
                Filter filter = filters.get(i);
                builder.append(filter.GetFilter());
                builder.append(" ");
                builder.append(filter.GetConnection());
                builder.append(" ");
            }
            builder.append(filters.get(filters.size() - 1).GetFilter());
            builder.append("\n");
        }

        builder.append(format("GROUP BY a.id_airport\nORDER BY count(a.id_airport) %s;", sortType));

        String query = builder.toString();

        try {
            Statement stmt = this.databaseConnection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            return ExtractAirports(rs);
        } catch (Exception ignored) {
            return null;
        }
    }

    /**
     * Select and return all the Airport tuples in the SQLite database.
     * @param filters Query filters
     * @return All airports that fit the database query
     */
    public ArrayList<Airport> FetchAirports(ArrayList<Filter> filters) {
        String query = SQLHelper.ExtractQuery("airport", filters);
        try {
            Statement stmt = this.databaseConnection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            return ExtractAirports(rs);
        } catch (Exception ignored) {
            return null;
        }
    }

    /**
     * Select and return all the Route tuples in the SQLite database.
     * @param filters Query filters
     * @return All routes that fit the database query
     */
    public ArrayList<Route> FetchRoutes(ArrayList<Filter> filters) {
        String query = SQLHelper.ExtractQuery("route", filters);
        try {
            Statement stmt = this.databaseConnection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            return ExtractRoutes(rs);
        } catch (Exception ignored) {
            return null;
        }
    }

    /**
     * Finds the source and destination airport IDs based off the respective airport's ICAOs
     * @param routePath the route path object
     * @return an array list of size two where the first element is the source airport ID and the second element is the
     * destination airport ID
     */
    //!!!THIS IS A DUPLICATE METHOD FROM DataExportHandler, CANNOT FIND A WAY AROUND THE STATIC METHOD YET!!!
    public ArrayList<Integer> getAirportIDsFromRoutePath2(RoutePath routePath) throws SQLException{
        String sourceAirportICAO = routePath.GetSource();
        String destinationAirportICAO = routePath.GetDestination();
        Statement stmt = this.databaseConnection.createStatement();
        String sourceQuery = format("SELECT id_airport FROM airport WHERE icao = '%s'", sourceAirportICAO);
        String destinationQuery = format("SELECT id_airport FROM airport WHERE icao = '%s'", destinationAirportICAO);
        Integer sourceAirportID = stmt.executeQuery(sourceQuery).getInt("id_airport");
        Integer destinationAirportID = stmt.executeQuery(destinationQuery).getInt("id_airport");
        if (sourceAirportID == null || destinationAirportID == null) {
            throw new SQLException("An airport id did not match with its ICAO");
        }
        ArrayList<Integer> airportIDsPair = new ArrayList<>(2);
        airportIDsPair.add(sourceAirportID);
        airportIDsPair.add(destinationAirportID);
        return airportIDsPair;
    }

    /**
     * Finds the flight path row in the database and returns its directory
     * @param sourceAirportID The source airport ID of the airport the flight path departs from
     * @param destinationAirportID the destination airport ID of the airport the flight path arrives at
     * @return The directory associated with the route path object
     */
    public String FetchFlightPaths(int sourceAirportID, int destinationAirportID) throws SQLException {
        String query = format("SELECT directory FROM flight_path WHERE AirportSourceID = %d AND DestinationSourceID = %d",
                sourceAirportID, destinationAirportID);
        Statement stmt = this.databaseConnection.createStatement();
        String directory = stmt.executeQuery(query).getString("directory");
        if (directory == null) {
            throw new SQLException("No flight path directory was found");
        }
        return directory;
    }

    /**
     * Finds the flight path row in the database and returns its directory
     * @param routePath the route path object
     * @return The directory associated with the route path object
     */
    public String FetchFlightPaths(RoutePath routePath) throws SQLException{
        ArrayList<Integer> airportIDsPair = new ArrayList<Integer>(2);
        airportIDsPair = getAirportIDsFromRoutePath2(routePath);
        int sourceAirportID = airportIDsPair.get(0);
        int destinationAirportID = airportIDsPair.get(1);
        return FetchFlightPaths(sourceAirportID, destinationAirportID);
    }

    /**
     * Retrieves the directory of the holiday plan object from the database
     * @param holidayPlanIndex the index of the holiday plan object in the array of holiday plans
     * @return the directory of the holiday plan object
     */
    public String FetchHolidayPlan(int holidayPlanIndex) throws SQLException{
        String query = format("SELECT directory FROM holiday_plan WHERE index_holiday_plan = %s", holidayPlanIndex);
        Statement stmt = this.databaseConnection.createStatement();
        String directory = stmt.executeQuery(query).getString("directory");
        if (directory == null) {
            throw new SQLException("No flight path directory was found");
        }
        return directory;
    }
}
