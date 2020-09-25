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
    public DataHandler() {
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
        File currentDir = new File(System.getProperty("user.dir"));
        File newDir = new File(currentDir, databaseOutputFolder);
        boolean ignored = newDir.mkdir();
        File file = new File(newDir, databaseFile);
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
     * Catches SQLException and discards entry if invalid
     * @param entry ArrayList of strings corresponding to each field of data to insert
     * @throws SQLException Exception if cannot input into the database
     */
    public void InsertAirline(ArrayList<String> entry) throws SQLException {
        Statement stmt = this.databaseConnection.createStatement();

        String sql = String.format("INSERT INTO airline (id_airline, name, alias, iata, icao, callsign, country, " +
                        "active) VALUES (\"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\");",
                entry.get(0), entry.get(1), entry.get(2), entry.get(3), entry.get(4), entry.get(5),
                entry.get(6), entry.get(7)
        );

        stmt.executeUpdate(sql);
    }

    /**
     * Insert all airports into database
     * Catches SQLException and discards entry if invalid
     * @param entry ArrayList of strings corresponding to each field of data to insert
     * @throws SQLException Exception if cannot input into the database
     */
    public void InsertAirport(ArrayList<String> entry) throws SQLException {
        Statement stmt = this.databaseConnection.createStatement();

        String sql = String.format("INSERT INTO airport (id_airport, name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst)" +
                        "VALUES (\"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\");",
                entry.get(0), entry.get(1), entry.get(2), entry.get(3), entry.get(4), entry.get(5),
                entry.get(6), entry.get(7), entry.get(8), entry.get(9), entry.get(10)
        );

        stmt.executeUpdate(sql);
    }

    /**
     * Inserts a Route data entry into database
     * Catches SQLException and discards entry if invalid
     * @param entry ArrayList of strings corresponding to each field of data to insert
     * @throws SQLException Exception if cannot input into the database
     */
    public void InsertRoute(ArrayList<String> entry) throws SQLException{
        Statement stmt = this.databaseConnection.createStatement();

        String sql = String.format("INSERT INTO route (airline, id_airline, source_airport, source_airport_id, " +
                        "destination_airport, destination_airport_id, codeshare, stops, equipment)" +
                        "VALUES (\"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\");",
                entry.get(0), entry.get(1), entry.get(2), entry.get(3), entry.get(4), entry.get(5),
                entry.get(6), entry.get(7), entry.get(8)
        );

        stmt.executeUpdate(sql);
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


    /**
     * Finds the airline in the database, based on there unique AirlineID and updates the fields based on the provided
     * parameters. Parameters that are null will not be updated.
     * @param AirlineID The ID of the Airline and the primary key of the airline within the database
     * @param Name The name of the airline
     * @param Alias The Alias of the airline
     * @param IATA The International Air Transport Association unique two character code
     * @param ICAO The International Civil Aviation Organisation unique three character code
     * @param Callsign The call sign of the airline
     * @param Country The airline's country of origin
     * @param Active Stating whether the airline is active
     * @throws SQLException Throws an SQLException when the update query is invalid
     */
    public void updateAirline(int AirlineID, String Name, String Alias, String IATA, String ICAO, String Callsign,
                              String Country, Character Active) throws Exception {
        Statement stmt = this.databaseConnection.createStatement();

        String setSQL = "";
        if (Name != null && !Name.trim().isEmpty()) {
            setSQL += String.format("name = '%s',", Name);
        }
        if (Alias != null && !Alias.trim().isEmpty()) {
            setSQL += String.format("alias = '%s',", Alias);
        }
        if (IATA != null && !IATA.trim().isEmpty()) {
            if (IATA.length() == 2) {
                setSQL += String.format("iata = '%s',", IATA);
            } else {
                throw new Exception("The provided IATA was not two characters long!");
            }
        }
        if (ICAO != null && !ICAO.trim().isEmpty()) {
            if (ICAO.length() == 3) {
                setSQL += String.format("icao = '%s',", ICAO);
            } else {
                throw new Exception("The provided ICAO was not three characters long!");
            }
        }
        if (Callsign != null && !Callsign.trim().isEmpty()) {
            setSQL += String.format("callsign = '%s',", Callsign);
        }
        if (Country != null && !Country.trim().isEmpty()) {
            setSQL += String.format("country = '%s',", Country);
        }
        if (Active != null) {
            setSQL += String.format("active = '%c',", Active);
        }
        if (setSQL.length() > 0) {
            setSQL = setSQL.substring(0, setSQL.length() - 1);
        } else {
            throw new Exception("No parameters to update were provided!");
        }

        String query = String.format("UPDATE airline SET %s WHERE id_airline == %d;", setSQL, AirlineID);
        stmt.executeUpdate(query);
    }

    /**
     * Finds the airport in the database, based on there unique AirportID and updates the fields based on the provided
     * parameters. Parameters that are null will not be updated.
     * @param AirportID The ID of the Airport and the primary key of the airport within the database
     * @param Name The name of the airport
     * @param City The name of the city the airport resides in
     * @param Country The name of the country the airport resides in
     * @param IATA The International Air Transport Association unique three character code
     * @param ICAO The International Civil Aviation Organisation unique four character code
     * @param Latitude The Latitudinal coordinate of the airport
     * @param Longitude The Longitudinal coordinate of the airport
     * @param Altitude The Altitude of the airport
     * @param Timezone The timezone the airport operates on
     * @param DST DST the airport is in
     * @throws SQLException Throws an SQLException when the update query is invalid
     */
    public void updateAirport(int AirportID, String Name, String City, String Country, String IATA, String ICAO,
                              Float Latitude, Float Longitude, Integer Altitude, Float Timezone,
                              Character DST) throws Exception {
        Statement stmt = this.databaseConnection.createStatement();

        String setSQL = "";
        if (Name != null && !Name.trim().isEmpty()) {
            setSQL += String.format("name = '%s',", Name);
        }
        if (City != null && !City.trim().isEmpty()) {
            setSQL += String.format("city = '%s',", City);
        }
        if (Country != null && !Country.trim().isEmpty()) {
            setSQL += String.format("country = '%s',", Country);
        }
        if (IATA != null && !IATA.trim().isEmpty()) {
            if (IATA.length() == 3) {
                setSQL += String.format("iata = '%s',", IATA);
            } else {
                throw new Exception("The provided IATA was not three characters long!");
            }
        }
        if (ICAO != null && !ICAO.trim().isEmpty()) {
            if (ICAO.length() == 4) {
                setSQL += String.format("icao = '%s',", ICAO);
            } else {
                throw new Exception("The provided ICAO was not four characters long!");
            }
        }
        if (Latitude != null) {
            setSQL += String.format("latitude = %f,", Latitude);
        }
        if (Longitude != null) {
            setSQL += String.format("longitude = %f,", Longitude);
        }
        if (Altitude != null) {
            setSQL += String.format("altitude = %d,", Altitude);
        }
        if (Timezone != null) {
            setSQL += String.format("timezone = %f,", Timezone);
        }
        if (setSQL.length() > 0) {
            setSQL = setSQL.substring(0, setSQL.length() - 1);
        } else {
            throw new Exception("No parameters to update were provided!");
        }

        String query = String.format("UPDATE airport SET %s WHERE id_airport == %d;", setSQL, AirportID);
        stmt.executeUpdate(query);
    }

    /**
     * Finds the route in the database, based on a combination of their unique AirlineID, SourceAirportID, and
     * DestinationAirportID. Then, the fields are updated based on the provided parameters.
     * Parameters that are null will not be updated.
     * @param AirlineID The ID of the Airline undertaking the route and one of the primary keys for route
     * @param SourceAirportID The ID of the airport the route will depart from and one of the primary keys for route
     * @param DestinationAirportID The ID of the airport the route will arrive at and one of the primary keys for route
     * @param Codeshare A character stating whether the route is a codeshare
     * @param Stops The number of stops the route has
     * @param Equipment A three character code for plane types
     * @throws SQLException Throws an SQLException when the update query is invalid
     */
    public void updateRoute(Integer AirlineID, int SourceAirportID, int DestinationAirportID, Character Codeshare,
                            Integer Stops, String Equipment) throws Exception {
        Statement stmt = this.databaseConnection.createStatement();

        String setSQL = "";
        if (Codeshare != null) {
            setSQL += String.format("codeshare = '%c',", Codeshare);
        }
        if (Stops != null) {
            setSQL += String.format("stops = %d,", Stops);
        }
        if (Equipment != null && !Equipment.trim().isEmpty()) {
            setSQL += String.format("equipment = '%s',", Equipment);
        }
        if (setSQL.length() > 0) {
            setSQL = setSQL.substring(0, setSQL.length() - 1);
        } else {
            throw new Exception("No parameters to update were provided!");
        }

        String query = String.format("UPDATE airline SET %s WHERE id_airline == %d AND source_airport_id == %d AND destination_airport_id == %d;"
                , setSQL, AirlineID, SourceAirportID, DestinationAirportID);

        stmt.executeUpdate(query);
    }

    /**
     * Deletes the Airline from the Database based on the AirlineID provided
     * @param AirlineID The unique ID associated with the airline.
     * @throws SQLException Throws an SQLException when the delete query is invalid
     */
    public void deleteAirline(int AirlineID) throws SQLException {
        Statement stmt = this.databaseConnection.createStatement();
        String query = String.format("DELETE FROM Airline WHERE id_airline == %d;", AirlineID);
        stmt.executeQuery(query);
    }

    /**
     * Deletes the Airport from the Database based on the AirportID provided
     * @param AirportID The unique ID associated with the airport.
     * @throws SQLException Throws an SQLException when the delete query is invalid
     */
    public void deleteAirport(int AirportID) throws SQLException {
        Statement stmt = this.databaseConnection.createStatement();
        String query = String.format("DELETE FROM Airport WHERE id_airport == %d;", AirportID);
        stmt.executeQuery(query);
    }

    /**
     * Deletes the Route from the Database based on the AirportID, SourceAirportID and DestinationAirportID provided
     * @param AirlineID The unique ID associated with the airline undertaking this route.
     * @param SourceAirportID The unique ID associated with the airport the route departed from.
     * @param DestinationAirportID The unique ID associated with the airport the route arrived at.
     * @throws SQLException Throws an SQLException when the delete query is invalid
     */
    public void deleteRoute(int AirlineID, int SourceAirportID, int DestinationAirportID) throws SQLException {
        Statement stmt = this.databaseConnection.createStatement();
        String query = String.format("DELETE FROM Route WHERE id_airline == %d AND source_airport_id == %d AND destination_airport_id == %d;"
                , AirlineID, SourceAirportID, DestinationAirportID);
        stmt.executeQuery(query);

    }
}