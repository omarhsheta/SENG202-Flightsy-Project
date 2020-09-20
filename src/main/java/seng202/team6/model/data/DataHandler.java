package seng202.team6.model.data;

import seng202.team6.model.entities.Airline;
import seng202.team6.model.entities.Airport;
import seng202.team6.model.entities.Route;
import seng202.team6.model.entities.RoutePath;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
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

    private final String databaseSourceFolder = "database";
    private final String databaseOutputFolder = "data";
    private final String databaseFile = "database.sqlite";
    /**
     * Constructor class creates the connection to the SQLite database.
     */
    private DataHandler() {
        try {
            //JDBC requires databases to be an external file
            TryCopyDatabase();
            // relative url to database
            String url = "jdbc:sqlite:" + databaseOutputFolder + "/" + databaseFile;
            // Create a connection to the database
            this.databaseConnection = DriverManager.getConnection(url);
            System.out.println("Successfully connected to SQLite.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method copies the database into an output folder
     * Required as JDBC loads databases from hard disk rather than
     * from embedded jar files
     */
    private void TryCopyDatabase() {
        String currentDir = System.getProperty("user.dir");
        boolean result = new File(currentDir + "/" + databaseOutputFolder).mkdir();
        File file = new File(databaseOutputFolder + "/" + databaseFile);
        if (!file.isFile()) {
            try {
                InputStream inStream = DataHandler.class.getResourceAsStream("/" + databaseSourceFolder + "/" + databaseFile);
                Files.copy(inStream, file.toPath());
            } catch (Exception e) {
                System.out.println("Failed to copy database into directory \n " + e.toString());
            }
        }
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

        builder.append(String.format("GROUP BY a.id_airport\nORDER BY count(a.id_airport) %s;", sortType));

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
     * Select and return all the RoutePath objects in the SQLite database.
     * @param sourceAirports Source airports list
     * @param destinationAirports Destination airports list
     * @return All routepaths from source to destination airports
     * TODO: Connor make work with database
     */
    public ArrayList<RoutePath> FetchRoutePaths(ArrayList<Airport> sourceAirports, ArrayList<Airport> destinationAirports) {
        //Use SQLHelper.GetAirportICAOList for ICAO's
        //Use SQLHelper.GetAirportIATAList for IATA's
        return null;
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
                    airline.getAirlineID(), airline.getName(), airline.getAlias(), airline.getIATA(), airline.getICAO(),
                    airline.getCallsign(), airline.getCountry(), airline.getActive()
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
                    route.getAirline(), route.getAirlineID(), route.getSourceAirport(), route.getSourceAirportID(),
                    route.getDestinationAirport(), route.getDestinationAirportID(), route.getCodeshare(),
                    route.getStops(), route.getEquipment()
            );
            stmt.executeUpdate(sql);
        }
    }
}