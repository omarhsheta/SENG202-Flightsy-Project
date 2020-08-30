package seng202.team6.model.data;

import seng202.team6.model.entities.Airline;
import seng202.team6.model.entities.Airport;
import seng202.team6.model.entities.Route;

import java.sql.*;
import java.util.ArrayList;

/**
 * Class for connecting to the SQLite database and executing queries.
 */
public class DataHandler {
    private static DataHandler Instance;

    /**
     * Singleton method to ensure only one connection to the database at one time
     * @return Single DataHandler object
     */
    public static DataHandler GetInstance() {
        if (Instance == null) {
            Instance = new DataHandler();
        }
        return Instance;
    }

    private Connection databaseConnection;

    /**
     * Constructor class creates the connection to the SQLite database.
     */
    private DataHandler() {
        // relative url to database
        String url = "jdbc:sqlite:src/main/database/database.sqlite";
        this.databaseConnection = null;
        try {
            // Create a connection to the database
            this.databaseConnection = DriverManager.getConnection(url);
            System.out.println("Successfully connected to SQLite.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Extract query from list of filter objects
     * @param tableName Database table
     * @param filters List of filters
     * @return SQL String query.
     */
    public String ExtractQuery(String tableName, ArrayList<Filter> filters) {
        if (filters == null || filters.size() == 0) {
            return String.format("SELECT * FROM %s;", tableName);
        }

        StringBuilder builder = new StringBuilder();
        builder.append(String.format("SELECT * FROM %s WHERE ", tableName));
        for (int i = 0; i < filters.size() - 1; i++) {
            Filter filter = filters.get(i);
            builder.append(filter.GetFilter());
            builder.append(" ");
            builder.append(filter.GetConnection());
            builder.append(" ");
        }
        builder.append(filters.get(filters.size() - 1).GetFilter());
        builder.append(';');

        return builder.toString();
    }

    /**
     * Extract a SQL query string list of airport IATAs
     * @param airports Airport list
     * @return String of IATAs in SQL List form
     */
    private String GetAirportIATAList(ArrayList<Airport> airports) {
        StringBuilder list = new StringBuilder();
        for (int i = 0; i < airports.size() - 1; i++) {
            list.append(String.format("'%s', ", airports.get(i).getIATA()));
        }
        list.append(String.format("'%s'", airports.get(airports.size() - 1).getIATA()));

        return list.toString();
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
        String query = ExtractQuery("airline", filters);
        try {
            Statement stmt = this.databaseConnection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            return ExtractAirlines(rs);
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
        String query = ExtractQuery("airport", filters);
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
        String query = ExtractQuery("route", filters);
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
     * @return All routes that fit the database query
     */
    public ArrayList<Route> FetchRoutes(ArrayList<Airport> sourceAirports, ArrayList<Airport> destinationAirports) {
        //This is big oof query, joining two tables
        String query = String.format("SELECT * FROM route " +
                        "JOIN (SELECT airport.iata FROM airport) " +
                        "WHERE iata = route.source_airport " +
                        "AND route.source_airport in (%s) AND route.destination_airport in (%s);",
                GetAirportIATAList(sourceAirports), GetAirportIATAList(destinationAirports));
        try {
            Statement stmt = this.databaseConnection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            return ExtractRoutes(rs);
        } catch (Exception ignored) {
            return null;
        }
    }

    /**
     * Insert all airlines into database
     * @param airlines Airlines to insert
     * @throws SQLException SQLException
     */
    public void InsertAirlines(ArrayList<Airline> airlines) throws SQLException {
        Statement stmt = this.databaseConnection.createStatement();
        for(Airline airline: airlines) {
            String sql = String.format("INSERT INTO airline (id_airline, name, alias, iata, icao, callsign, country, " +
                    "active) VALUES (\"%d\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%c\");",
                    airline.GetAirlineID(), airline.GetName(), airline.GetAlias(), airline.GetIATA(), airline.GetICAO(),
                    airline.GetCallsign(), airline.GetCountry(), airline.GetActive()
            );
            stmt.executeUpdate(sql);

        }
    }

    /**
     * Insert all airports into database
     * @param airports Airports to insert
     * @throws SQLException SQLException
     */
    public void InsertAirports(ArrayList<Airport> airports) throws SQLException {
        Statement stmt = this.databaseConnection.createStatement();
        for(Airport airport: airports) {
            String sql = String.format("INSERT INTO airport (id_airport, name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst)" +
                    "VALUES (\"%d\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%f\", \"%f\", \"%d\", \"%f\", \"%c\");",
                    airport.getAirportID(), airport.getName(), airport.getCity(), airport.getCountry(),
                    airport.getIATA(), airport.getICAO(), airport.getLatitude(), airport.getLongitude(), airport.getAltitude(),
                    airport.getTimezone(), airport.getDST()
            );
            stmt.executeUpdate(sql);
        }
    }

    /**
     * Insert all routes into database
     * @param routes Routes to insert
     * @throws SQLException SQLException
     */
    public void InsertRoutes(ArrayList<Route> routes) throws SQLException{
        Statement stmt = this.databaseConnection.createStatement();
        for(Route route: routes) {
            String sql = String.format("INSERT INTO route (airline, id_airline, source_airport, source_airport_id, " +
                    "destination_airport, destination_airport_id, codeshare, stops, equipment)" +
                    "VALUES (\"%s\", \"%d\", \"%s\", \"%d\", \"%s\", \"%d\", \"%c\", \"%d\", \"%s\");",
                    route.GetAirline(), route.GetAirlineID(), route.GetSourceAirport(), route.GetSourceAirportID(),
                    route.GetDestinationAirport(), route.GetDestinationAirportID(), route.GetCodeshare(),
                    route.GetStops(), route.GetEquipment()
            );
            stmt.executeUpdate(sql);
        }
    }
}