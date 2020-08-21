package seng202.team6.model.data;
import seng202.team6.model.entities.Airline;

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

        // Loop through the result set
        while (rs.next()) {
            Airline airline = new Airline(rs.getInt("id_airline"), rs.getString("name"),rs.getString("alias"),
                    rs.getString("iata"), rs.getString("icao"), rs.getString("callsign"),
                    rs.getString("country"), rs.getString("active").charAt(0));
            airlines.add(airline);
        }
        return airlines;
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
            System.out.println(airline.GetName());
        }
    }

}