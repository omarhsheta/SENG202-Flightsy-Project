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
     * Extract list of paths from result set
     * @param resultSet Results from query
     * @return List of route paths
     * @throws SQLException SQLException
     */
    private ArrayList<RoutePath> ExtractRoutePaths(ResultSet resultSet) throws SQLException {
        ArrayList<RoutePath> paths = new ArrayList<>();
        // Loop through the result set and create Airport objects from data
        while (resultSet.next()) {
            String directory = resultSet.getString("directory");
            String json = DataHandler.GetInstance().ReadDataFile(directory);
            RoutePath path = RoutePath.FromJSON(json);
            paths.add(path);
        }
        return paths;
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
     * Select and return all the Route tuples in the SQLite database.
     * @param sourceAirports Source airport list
     * @param destinationAirports Destination airport list
     * @param maxStops Maximum stops the route has
     * @return All routes that fit the database query
     */
    public ArrayList<Route> FetchRoutes(ArrayList<Airport> sourceAirports, ArrayList<Airport> destinationAirports, int maxStops) {
        //This is big oof query, joining two tables
        String query = String.format("SELECT * FROM route " +
                        "JOIN (SELECT airport.iata FROM airport) " +
                        "WHERE iata = route.source_airport " +
                        "AND route.source_airport in (%s) AND route.destination_airport in (%s) " +
                        "AND route.stops <= (%s);",
                SQLHelper.GetAirportIATAList(sourceAirports), SQLHelper.GetAirportIATAList(destinationAirports), maxStops);
        try {
            Statement stmt = this.databaseConnection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            return ExtractRoutes(rs);
        } catch (Exception ignored) {
            return null;
        }
    }

    /**
     * Select and return all the Route Paths in the SQLite database.
     * @param sourceAirports Source airport list
     * @param destinationAirports Destination airport list
     * @return All Route paths that fit the database query
     */
    public ArrayList<RoutePath> FetchRoutePaths(ArrayList<Airport> sourceAirports, ArrayList<Airport> destinationAirports) {
        String query = String.format("SELECT directory FROM flight_path " +
                        "WHERE source_airport_id IN (%s) AND destination_airport_id IN (%s);",
                SQLHelper.GetAirportIDList(sourceAirports), SQLHelper.GetAirportIDList(destinationAirports));
        try {
            Statement stmt = this.databaseConnection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            return ExtractRoutePaths(rs);

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
     * Inserts the holiday plan object's directory location into the database
     * @param name the name of the holiday plan object in the array of holiday plans
     * @param directory the directory in which the holiday plan object resides
     */
    public void InsertHolidayPlan(String name, String directory) throws SQLException {
        String sql = format("INSERT INTO holiday_plan (name, directory), VALUES ('%s', '%s')", name, directory);
        Statement stmt = this.databaseConnection.createStatement();
        if (stmt.executeUpdate(sql) <= 0) {
            throw new SQLException("The holiday plan was not inserted into the database");
        }
    }

    /**
     * Updates the holiday plan in the database with a new location for the object
     * @param name the primary key
     * @param newName the new name
     * @param newDirectory the new directory in which the holiday plan object will reside
     */
    public void UpdateHolidayPlan(String name, String newName, String newDirectory) throws SQLException, Exception{
        String setSQL = "";
        if (newDirectory == null || newDirectory.isEmpty()) {
            setSQL += format("directory = '%s', ", newDirectory);
        }
        if (newName != null && !newName.isEmpty()) {
            setSQL += format("name = '%s', ", newName);
        }
        if (setSQL.length() > 0) {
            setSQL = setSQL.substring(0, setSQL.length() - 1);
        } else {
            throw new Exception("No parameters to update were provided!");
        }

        String sql = format("UPDATE holiday_path SET %s WHERE name = '%s'", setSQL, name);
        Statement stmt = this.databaseConnection.createStatement();
        if (stmt.executeUpdate(sql) <= 0) {
            throw new SQLException("The holiday plan in the database was not updated");
        }
    }

    /**
     * Deletes the holiday plan entry from the database
     * @param name the name of the holiday
     */
    public void DeleteHolidayPlan(String name) throws SQLException {
        String sql = format("DELETE FROM holiday_plan WHERE index_holiday = '%s'", name);
        Statement stmt = this.databaseConnection.createStatement();
        if (stmt.executeUpdate(sql) <= 0) {
            throw new SQLException("Nothing was deleted");
        }
    }

    /**
     * Fetches the holiday plan entry from the database
     * @param filters Filters on fetch
     */
    public ArrayList<String> FetchHolidayPlans(ArrayList<Filter> filters) throws SQLException {
        String query = SQLHelper.ExtractQuery("holiday_plan", filters);
        try {
            Statement stmt = this.databaseConnection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ArrayList<String> directories = new ArrayList<>();
            while (rs.next()) {
                String dir = rs.getString("directory");
                if (dir != null) {
                    directories.add(dir);
                }
            }
            return directories;

        } catch (Exception ignored) {
            return null;
        }
    }
}
