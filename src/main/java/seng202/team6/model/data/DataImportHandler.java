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

public class DataImportHandler {
    private static DataImportHandler Instance;
    private final Connection databaseConnection;

    /**
     * Singleton method to ensure only one instance to the class at one time
     * @return Single DataExportHandler object
     */
    public static DataImportHandler GetInstance() {
        if (Instance == null) {
            Instance = new DataImportHandler();
        }
        return Instance;
    }

    /**
     * Private constructor for class, grabs database connection
     */
    private DataImportHandler() {
        databaseConnection = DataHandler.GetInstance().GetConnection();
    }

    /**
     * Insert an airline into the database
     * @param airline Airlines to insert
     * @throws SQLException SQLException
     */
    public void InsertAirline(Airline airline) throws SQLException {
        Statement stmt = this.databaseConnection.createStatement();
        String sql = format("INSERT INTO airline (id_airline, name, alias, iata, icao, callsign, country, " +
                        "active) VALUES (\"%d\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%c\");",
                airline.getAirlineID(), airline.getName(), airline.getAlias(), airline.getIATA(), airline.getICAO(),
                airline.getCallsign(), airline.getCountry(), airline.getActive()
        );
        stmt.executeUpdate(sql);
    }

    /**
     * Insert all airports into database
     * @param airport Airport to insert
     * @throws SQLException SQLException
     */
    public void InsertAirport(Airport airport) throws SQLException {
        Statement stmt = this.databaseConnection.createStatement();
        String sql = format("INSERT INTO airport (id_airport, name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst)" +
                        "VALUES (\"%d\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%f\", \"%f\", \"%d\", \"%f\", \"%c\");",
                airport.getAirportID(), airport.getName(), airport.getCity(), airport.getCountry(),
                airport.getIATA(), airport.getICAO(), airport.getLatitude(), airport.getLongitude(), airport.getAltitude(),
                airport.getTimezone(), airport.getDST()
        );
        stmt.executeUpdate(sql);
    }

    /**
     * Insert all routes into database
     * @param route Route to insert
     * @throws SQLException SQLException
     */
    public void InsertRoute(Route route) throws SQLException{
        Statement stmt = this.databaseConnection.createStatement();
        String sql = format("INSERT INTO route (airline, id_airline, source_airport, source_airport_id, " +
                        "destination_airport, destination_airport_id, codeshare, stops, equipment)" +
                        "VALUES (\"%s\", \"%d\", \"%s\", \"%d\", \"%s\", \"%d\", \"%c\", \"%d\", \"%s\");",
                route.getAirline(), route.getAirlineID(), route.getSourceAirport(), route.getSourceAirportID(),
                route.getDestinationAirport(), route.getDestinationAirportID(), route.getCodeshare(),
                route.getStops(), route.getEquipment()
        );
        stmt.executeUpdate(sql);
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
            setSQL += format("name = '%s',", Name);
        }
        if (Alias != null && !Alias.trim().isEmpty()) {
            setSQL += format("alias = '%s',", Alias);
        }
        if (IATA != null && !IATA.trim().isEmpty()) {
            if (IATA.length() == 2) {
                setSQL += format("iata = '%s',", IATA);
            } else {
                throw new Exception("The provided IATA was not two characters long!");
            }
        }
        if (ICAO != null && !ICAO.trim().isEmpty()) {
            if (ICAO.length() == 3) {
                setSQL += format("icao = '%s',", ICAO);
            } else {
                throw new Exception("The provided ICAO was not three characters long!");
            }
        }
        if (Callsign != null && !Callsign.trim().isEmpty()) {
            setSQL += format("callsign = '%s',", Callsign);
        }
        if (Country != null && !Country.trim().isEmpty()) {
            setSQL += format("country = '%s',", Country);
        }
        if (Active != null) {
            setSQL += format("active = '%c',", Active);
        }
        if (setSQL.length() > 0) {
            setSQL = setSQL.substring(0, setSQL.length() - 1);
        } else {
            throw new Exception("No parameters to update were provided!");
        }

        String query = format("UPDATE airline SET %s WHERE id_airline == %d;", setSQL, AirlineID);
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
            setSQL += format("name = '%s',", Name);
        }
        if (City != null && !City.trim().isEmpty()) {
            setSQL += format("city = '%s',", City);
        }
        if (Country != null && !Country.trim().isEmpty()) {
            setSQL += format("country = '%s',", Country);
        }
        if (IATA != null && !IATA.trim().isEmpty()) {
            if (IATA.length() == 3) {
                setSQL += format("iata = '%s',", IATA);
            } else {
                throw new Exception("The provided IATA was not three characters long!");
            }
        }
        if (ICAO != null && !ICAO.trim().isEmpty()) {
            if (ICAO.length() == 4) {
                setSQL += format("icao = '%s',", ICAO);
            } else {
                throw new Exception("The provided ICAO was not four characters long!");
            }
        }
        if (Latitude != null) {
            setSQL += format("latitude = %f,", Latitude);
        }
        if (Longitude != null) {
            setSQL += format("longitude = %f,", Longitude);
        }
        if (Altitude != null) {
            setSQL += format("altitude = %d,", Altitude);
        }
        if (Timezone != null) {
            setSQL += format("timezone = %f,", Timezone);
        }
        if (setSQL.length() > 0) {
            setSQL = setSQL.substring(0, setSQL.length() - 1);
        } else {
            throw new Exception("No parameters to update were provided!");
        }

        String query = format("UPDATE airport SET %s WHERE id_airport == %d;", setSQL, AirportID);
        stmt.executeUpdate(query);
    }

    /**
     * Finds the route in the database, based on a combination of their unique AirlineID, SourceAirportID, and
     * DestinationAirportID. Then, the fields are updated based on the provided parameters.
     * Parameters that are null will not be updated.
     * @param AirlineID The ID of the Airline undertaking the route and one of the primary keys for route
     * @param SourceAirportID The ID of the airport the route will depart from and one of the primary keys for route
     * @param DestinationAirportID The ID of the airport the route will arrive at and one of the primary keys for route
     * @param newAirlineID The new ID of the airline undertaking the route
     * @param newSourceAirportID The new ID of the airport the route will depart from
     * @param newDestinationAirportID The new ID of the airport the route will arrive at
     * @param Codeshare A character stating whether the route is a codeshare
     * @param Stops The number of stops the route has
     * @param Equipment A three character code for plane types
     * @throws SQLException Throws an SQLException when the update query is invalid
     */
    public void updateRoute(int AirlineID, int SourceAirportID, int DestinationAirportID, Integer newAirlineID,
                            Integer newSourceAirportID, Integer newDestinationAirportID, Character Codeshare,
                            Integer Stops, String Equipment) throws Exception {
        Statement stmt = this.databaseConnection.createStatement();

        String setSQL = "";

        ArrayList<Filter> filters = new ArrayList<Filter>();
        try {
            if (newAirlineID != null) {
                Filter filter1 = new Filter(format("id_airline = %d", newAirlineID), "");
                filters.add(filter1);
                Airline newAirline = DataExportHandler.GetInstance().FetchAirlines(filters).get(0);
                filters.clear();
                setSQL += format("id_airline = %d,", newAirline.getAirlineID());
                setSQL += format("airline = '%s',", newAirline.getICAO());
            }
            if (newSourceAirportID != null) {
                Filter filter2 = new Filter(format("id_airport", newSourceAirportID), "");
                filters.add(filter2);
                Airport newSourceAirport = DataExportHandler.GetInstance().FetchAirports(filters).get(0);
                filters.clear();
                setSQL += format("source_airport_id = %d,", newSourceAirport.getAirportID());
                setSQL += format("source_airport = '%s',", newSourceAirport.getICAO());
            }
            if (newDestinationAirportID != null) {
                Filter filter3 = new Filter(format("id_airport", newDestinationAirportID), "");
                filters.add(filter3);
                Airport newDestinationAirport = DataExportHandler.GetInstance().FetchAirports(filters).get(0);
                filters.clear();
                setSQL += format("destination_airport_id = %d,", newDestinationAirport.getAirportID());
                setSQL += format("destination_airport = '%s',", newDestinationAirport.getICAO());
            }
        } catch (Exception e) { e.getMessage(); }

        if (Codeshare != null) {
            setSQL += format("codeshare = '%c',", Codeshare);
        }
        if (Stops != null) {
            setSQL += format("stops = %d,", Stops);
        }
        if (Equipment != null && !Equipment.trim().isEmpty()) {
            setSQL += format("equipment = '%s',", Equipment);
        }
        if (setSQL.length() > 0) {
            setSQL = setSQL.substring(0, setSQL.length() - 1);
        } else {
            throw new Exception("No parameters to update were provided!");
        }

        String query = format("UPDATE route SET %s WHERE id_airline == %d AND source_airport_id == %d AND destination_airport_id == %d;"
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
        String query = format("DELETE FROM Airline WHERE id_airline == %d;", AirlineID);
        if (stmt.executeUpdate(query) <= 0) {
            throw new SQLException("Nothing was deleted");
        }
    }

    /**
     * Deletes the Airport from the Database based on the AirportID provided
     * @param AirportID The unique ID associated with the airport.
     * @throws SQLException Throws an SQLException when the delete query is invalid
     */
    public void deleteAirport(int AirportID) throws SQLException {
        Statement stmt = this.databaseConnection.createStatement();
        String query = format("DELETE FROM Airport WHERE id_airport == %d;", AirportID);
        if (stmt.executeUpdate(query) <= 0) {
            throw new SQLException("Nothing was deleted");
        }
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
        String query = format("DELETE FROM Route WHERE id_airline == %d AND source_airport_id == %d AND destination_airport_id == %d;",
                AirlineID, SourceAirportID, DestinationAirportID);
        if (stmt.executeUpdate(query) <= 0) {
            throw new SQLException("Nothing was deleted");
        }
    }


    /**
     * Finds the source and destination airport IDs based off the respective airport's ICAOs
     * @param routePath the route path object
     * @return an array list of size two where the first element is the source airport ID and the second element is the
     * destination airport ID
     */
    public ArrayList<Integer> getAirportIDsFromRoutePath(RoutePath routePath) throws SQLException{
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
    //TODO Move to DataExportHandler
    public String fetchFlightPaths(int sourceAirportID, int destinationAirportID) throws SQLException {
        String query = format("SELECT directory FROM airport WHERE AirportSourceID = %d AND DestinationSourceID = %d",
                sourceAirportID, destinationAirportID);
        Statement stmt = this.databaseConnection.createStatement();
        String directory;
        directory = stmt.executeQuery(query).getString("directory");
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
    //TODO Move to DataExportHandler
    public String fetchFlightPaths(RoutePath routePath) throws SQLException{
        ArrayList<Integer> airportIDsPair = new ArrayList<Integer>(2);
        airportIDsPair = getAirportIDsFromRoutePath(routePath);
        int sourceAirportID = airportIDsPair.get(0);
        int destinationAirportID = airportIDsPair.get(1);
        return fetchFlightPaths(sourceAirportID, destinationAirportID);
    }

    /**
     * Inserts a flight path into the database
     * @param routePath the route path object, also known as the flight path
     * @param directory The local directory the flight path object is stored at
     */
    public void insertFlightPath(RoutePath routePath, String directory) throws SQLException {
        ArrayList<Integer> airportIDsPair = new ArrayList<Integer>(2);
        airportIDsPair = getAirportIDsFromRoutePath(routePath);
        int sourceAirportID = airportIDsPair.get(0);
        int destinationAirportID = airportIDsPair.get(1);
        String sql = format("INSERT INTO flight_paths (source_airport_id, destination_airport_id, directory)" +
                "VALUES (%d, %d, '%s')", sourceAirportID, destinationAirportID, directory);
        Statement stmt = this.databaseConnection.createStatement();
        int results = stmt.executeUpdate(sql);
        if (results <= 0) {
            throw new SQLException("The flight path was not inserted into the database");
        }
    }

    /**
     * Updates the directory of the flight path in the database
     * @param sourceAirportID The source airport ID of the airport the flight path departs from
     * @param destinationAirportID the destination airport ID of the airport the flight path arrives at
     * @param newDirectory The new directory the RoutePath object will be stored
     */
    public void updateFlightPath(int sourceAirportID, int destinationAirportID, String newDirectory) throws SQLException,
            Exception {
        if (newDirectory == null || newDirectory.isEmpty()) {
            throw new Exception("No directory was provided");
        }
        String sql = format("UPDATE flight_paths SET directory = %s WHERE source_airport_id = %d AND destination_airport_id = %d",
                newDirectory, sourceAirportID, destinationAirportID);
        Statement stmt = this.databaseConnection.createStatement();
        int results = stmt.executeUpdate(sql);
        if (results <= 0) {
            throw new SQLException("The flight path in the database was not updated");
        }
    }

    /**
     * Updates the directory of the flight path in the database
     * @param routePath the route path object
     * @param newDirectory The new directory the RoutePath object will be stored
     */
    public void updateFlightPath(RoutePath routePath, String newDirectory) throws SQLException, Exception {
        ArrayList<Integer> airportIDsPair = new ArrayList<Integer>(2);
        try {
            airportIDsPair = getAirportIDsFromRoutePath(routePath);
        } catch (Exception e) { throw e; }
        int sourceAirportID = airportIDsPair.get(0);
        int destinationAirportID = airportIDsPair.get(1);
        updateFlightPath(sourceAirportID, destinationAirportID, newDirectory);
    }

    /**
     * Deletes the flight path in the database
     * @param sourceAirportID The source airport ID of the airport the flight path departs from
     * @param destinationAirportID the destination airport ID of the airport the flight path arrives at
     */
    public void deleteFlightPath(int sourceAirportID, int destinationAirportID) throws SQLException {
        Statement stmt = this.databaseConnection.createStatement();
        String sql = format("DELETE FROM flight_paths WHERE sourceAirportID = %d AND destinationAirportID = %d",
                sourceAirportID, destinationAirportID);
        int results = stmt.executeUpdate(sql);
        if (results <= 0) {
            throw new SQLException("The flight path was not deleted from then database");
        }
    }

    /**
     * Deletes the flight path in the database
     * @param routePath routePath the route path object
     */
    public void deleteFlightPath(RoutePath routePath) throws SQLException {
        ArrayList<Integer> airportIDsPair = new ArrayList<Integer>(2);
        try {
            airportIDsPair = getAirportIDsFromRoutePath(routePath);
        } catch (Exception e) { throw e; }
        int sourceAirportID = airportIDsPair.get(0);
        int destinationAirportID = airportIDsPair.get(1);
        deleteFlightPath(sourceAirportID, destinationAirportID);
    }




    /**
     * Retrieves the directory of the holiday plan object from the database
     * @param holidayPlanIndex the index of the holiday plan object in the array of holiday plans
     * @return the directory of the holiday plan object
     */
    //TODO Move to DataExportHandler
    public String fetchHolidayPlan(int holidayPlanIndex) {
        return null;
    }

    /**
     * Inserts the holiday plan object's directory location into the database
     * @param holidayPlanIndex the index of the holiday plan object in the array of holiday plans
     * @param directory the directory in which the holiday plan object resides
     */
    public void insertHolidayPlan(int holidayPlanIndex, String directory) {

    }

    /**
     * Updates the holiday plan in the database with a new location for the object
     * @param holidayPlanIndex the index of the holiday plan object in the array of holiday plans
     * @param newDirectory the new directory in which the holiday plan object will reside
     */
    public void updateHolidayPlan(int holidayPlanIndex, String newDirectory) {

    }

    /**
     * Deletes the holiday plan entry from the database
     * @param holidayPlanIndex the index of the holiday plan object in the array of holiday plans
     */
    public void deleteHolidayPlan(int holidayPlanIndex) {

    }
}
