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

    private Connection conn;

    /**
     * Constructor class creates the connection to the SQLite database.
     */
    private DataHandler() {
        // relative url to database
        String url = "jdbc:sqlite:src/main/database/database.sqlite";
        this.conn = null;
        try {
            // Create a connection to the database
            this.conn = DriverManager.getConnection(url);
            System.out.println("Successfully connected to SQLite.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Select and return all the Airline tuples in the SQLite database.
     * @return An arraylist containing all Airlines in the database
     */
    public ArrayList<Airline> FetchAirlines() throws SQLException {
        ArrayList<Airline> airlines = new ArrayList<>();

        String sql = "SELECT * FROM airline";
        Statement stmt = this.conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

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
     * @return An arraylist containing all Airports in the database
     */
    public ArrayList<Airport> FetchAirports() throws SQLException {
        ArrayList<Airport> airports = new ArrayList<>();

        String sql = "SELECT * FROM airport";
        Statement stmt = this.conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

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
     * @return An arraylist containing all Airports in the database
     */
    public ArrayList<Route> FetchRoutes() throws SQLException {
        ArrayList<Route> routes = new ArrayList<>();

        String sql = "SELECT * FROM route";
        Statement stmt = this.conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

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
     * TEST METHOD
     * @param args no u
     * @throws SQLException no u
     */
    public static void main(String[] args) throws SQLException {
        DataHandler database = new DataHandler();

        ArrayList<Airline> airlines;
        airlines = database.FetchAirlines();
        for (Airline airline : airlines) {
            System.out.println(airline.GetActive());
        }

//        ArrayList<Airport> airports;
//        airports = database.FetchAirports();
//        for (Airport airport : airports) {
//            System.out.println(airport.GetDST());
//        }

//        ArrayList<Route> routes;
//        routes = database.FetchRoutes();
//        for (Route route : routes) {
//            System.out.println(route.GetCodeshare());
//        }
    }

}