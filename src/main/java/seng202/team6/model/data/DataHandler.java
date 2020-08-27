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
     * Select and return all the Airline tuples in the SQLite database.
     * @param filters List of filters to apply to search query
     * @return List of airline objects
     * @throws SQLException SQLException
     */
    public ArrayList<Airline> FetchAirlines(ArrayList<Filter> filters) throws SQLException {
        ArrayList<Airline> airlines = new ArrayList<>();

        String query = ExtractQuery("airline", filters);
        Statement stmt = this.databaseConnection.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        // Loop through the result set and create Airline objects from data
        while (rs.next()) {
            String active = rs.getString("active");
            char char_active = (active.length() > 0 ? active.charAt(0) : ' ');
            Airline airline = new Airline(
                    rs.getInt("id_airline"), rs.getString("name"),rs.getString("alias"),
                    rs.getString("iata"), rs.getString("icao"), rs.getString("callsign"),
                    rs.getString("country"), char_active
            );
            airlines.add(airline);
        }
        return airlines;
    }

    /**
     * Select and return all the Airport tuples in the SQLite database.
     * @param filters Query filters
     * @return All airports that fit the database query
     * @throws SQLException SQLException
     */
    public ArrayList<Airport> FetchAirports(ArrayList<Filter> filters) throws SQLException {
        ArrayList<Airport> airports = new ArrayList<>();

        String query = ExtractQuery("airport", filters);

        Statement stmt = this.databaseConnection.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        // Loop through the result set and create Airport objects from data
        while (rs.next()) {
            String dst = rs.getString("dst");
            char char_dst = (dst.length() > 0 ? dst.charAt(0) : ' ');
            Airport airport = new Airport(
                    rs.getInt("id_airport"), rs.getString("name"), rs.getString("city"),
                    rs.getString("country"), rs.getString("iata"), rs.getString("icao"),
                    rs.getFloat("latitude"), rs.getFloat("longitude"), rs.getInt("altitude"),
                    rs.getInt("timezone"), char_dst
            );
            airports.add(airport);
        }
        return airports;
    }

    /**
     * Select and return all the Airport tuples in the SQLite database.
     * @param filters Query filters
     * @return All routes that fit the database query
     * @throws SQLException SQLException
     */
    public ArrayList<Route> FetchRoutes(ArrayList<Filter> filters) throws SQLException {
        ArrayList<Route> routes = new ArrayList<>();

        String query = ExtractQuery("route", filters);
        Statement stmt = this.databaseConnection.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        // Loop through the result set and create Airport objects from data
        while (rs.next()) {
            String codeshare = rs.getString("codeshare");
            char char_codeshare = (codeshare.length() > 0 ? codeshare.charAt(0) : 'N');
            Route route = new Route(
                    rs.getInt("id_airline"), rs.getString("airline"), rs.getString("source_airport"),
                    rs.getInt("source_airport_id"), rs.getString("destination_airport"),
                    rs.getInt("destination_airport_id"), char_codeshare,
                    rs.getInt("stops"), rs.getString("equipment")
            );
            routes.add(route);
        }
        return routes;
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
                    airport.GetAirportID(), airport.GetName(), airport.GetCity(), airport.GetCountry(),
                    airport.GetIATA(), airport.GetICAO(), airport.GetLatitude(), airport.GetLongitude(), airport.GetAltitude(),
                    airport.GetTimezone(), airport.GetDST()
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