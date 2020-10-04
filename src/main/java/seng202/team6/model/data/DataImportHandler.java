package seng202.team6.model.data;

import seng202.team6.model.entities.Airline;
import seng202.team6.model.entities.Airport;
import seng202.team6.model.entities.Route;
import seng202.team6.model.entities.RoutePath;

import java.sql.Connection;
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
        String sql = format("INSERT INTO airline (name, alias, iata, icao, callsign, country, " +
                        "active) VALUES (\"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%c\");",
                airline.GetName(), airline.GetAlias(), airline.GetIATA(), airline.GetICAO(),
                airline.GetCallsign(), airline.getCountry(), airline.GetActive()
        );
        if (stmt.executeUpdate(sql) <= 0) {
            throw new SQLException("The airline was not inserted into the database");
        }
    }

    /**
     * Insert all airports into database
     * @param airport Airport to insert
     * @throws SQLException SQLException
     */
    public void InsertAirport(Airport airport) throws SQLException {
        Statement stmt = this.databaseConnection.createStatement();
        String sql = format("INSERT INTO airport (name, city, country, iata, icao, latitude, longitude, altitude, timezone, dst)" +
                        "VALUES (\"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%f\", \"%f\", \"%d\", \"%f\", \"%c\");",
                airport.GetName(), airport.GetCity(), airport.GetCountry(),
                airport.GetIATA(), airport.GetICAO(), airport.GetLatitude(), airport.GetLongitude(), airport.GetAltitude(),
                airport.GetTimezone(), airport.GetDST()
        );
        int pass = stmt.executeUpdate(sql);
        if (pass <= 0) {
            throw new SQLException("The airport was not inserted into the database");
        }
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
                route.GetAirline(), route.GetAirlineID(), route.GetSourceAirport(), route.GetSourceAirportID(),
                route.GetDestinationAirport(), route.GetDestinationAirportID(), route.GetCodeshare(),
                route.GetStops(), route.GetEquipment()
        );
        if (stmt.executeUpdate(sql) <= 0) {
            throw new SQLException("The route was not inserted into the database");
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
    public void UpdateAirline(int AirlineID, String Name, String Alias, String IATA, String ICAO, String Callsign,
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
    public void UpdateAirport(int AirportID, String Name, String City, String Country, String IATA, String ICAO,
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
     * @param newAirline The new airline object
     * @param newSourceAirport The new source airport object
     * @param newDestinationAirport The new destination object
     * @param Codeshare A character stating whether the route is a codeshare
     * @param Stops The number of stops the route has
     * @param Equipment A three character code for plane types
     * @throws SQLException Throws an SQLException when the update query is invalid
     */
    public void UpdateRoute(int AirlineID, int SourceAirportID, int DestinationAirportID, Airline newAirline,
                            Airport newSourceAirport, Airport newDestinationAirport, Character Codeshare,
                            Integer Stops, String Equipment) throws Exception {
        Statement stmt = this.databaseConnection.createStatement();

        String setSQL = "";

        ArrayList<Filter> filters = new ArrayList<Filter>();
        try {
            if (newAirline != null) {
                Filter filter1 = new Filter(format("icao = '%s'", newAirline.GetICAO()), "");
                filters.add(filter1);
                Airline newAirlineDB = DataExportHandler.GetInstance().FetchAirlines(filters).get(0);
                filters.clear();
                setSQL += format("id_airline = %d,", newAirlineDB.GetAirlineID());
                setSQL += format("airline = '%s',", newAirlineDB.GetICAO());
            }
            if (newSourceAirport != null) {
                Filter filter2 = new Filter(format("icao = '%s'", newSourceAirport.GetICAO()), "");
                filters.add(filter2);
                Airport newSourceAirportDB = DataExportHandler.GetInstance().FetchAirports(filters).get(0);
                filters.clear();
                setSQL += format("source_airport_id = %d,", newSourceAirportDB.GetAirportID());
                setSQL += format("source_airport = '%s',", newSourceAirportDB.GetICAO());
            }
            if (newDestinationAirport != null) {
                Filter filter3 = new Filter(format("icao = '%s'", newDestinationAirport.GetICAO()), "");
                filters.add(filter3);
                Airport newDestinationAirportDB = DataExportHandler.GetInstance().FetchAirports(filters).get(0);
                filters.clear();
                setSQL += format("destination_airport_id = %d,", newDestinationAirportDB.GetAirportID());
                setSQL += format("destination_airport = '%s',", newDestinationAirportDB.GetICAO());
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
    public void DeleteAirline(int AirlineID) throws SQLException {
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
    public void DeleteAirport(int AirportID) throws SQLException {
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
    public void DeleteRoute(int AirlineID, int SourceAirportID, int DestinationAirportID) throws SQLException {
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
    public ArrayList<Integer> GetAirportIDsFromRoutePath(RoutePath routePath) throws SQLException{
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
     * Inserts a flight path into the database
     * @param routePath the route path object, also known as the flight path
     * @param directory The local directory the flight path object is stored at
     */
    public void InsertFlightPath(RoutePath routePath, String directory) throws SQLException {
        ArrayList<Integer> airportIDsPair = new ArrayList<Integer>(2);
        airportIDsPair = GetAirportIDsFromRoutePath(routePath);
        int sourceAirportID = airportIDsPair.get(0);
        int destinationAirportID = airportIDsPair.get(1);
        String sql = format("INSERT INTO flight_path (source_airport_id, destination_airport_id, directory)" +
                "VALUES (%d, %d, '%s')", sourceAirportID, destinationAirportID, directory);
        Statement stmt = this.databaseConnection.createStatement();
        if (stmt.executeUpdate(sql) <= 0) {
            throw new SQLException("The flight path was not inserted into the database");
        }
    }

    /**
     * Updates the directory of the flight path in the database
     * @param sourceAirportID The source airport ID of the airport the flight path departs from
     * @param destinationAirportID the destination airport ID of the airport the flight path arrives at
     * @param newDirectory The new directory the RoutePath object will be stored
     */
    public void UpdateFlightPath(int sourceAirportID, int destinationAirportID, String newDirectory) throws SQLException,
            Exception {
        if (newDirectory == null || newDirectory.isEmpty()) {
            throw new Exception("No directory was provided");
        }
        String sql = format("UPDATE flight_path SET directory = %s WHERE source_airport_id = %d AND destination_airport_id = %d",
                newDirectory, sourceAirportID, destinationAirportID);
        Statement stmt = this.databaseConnection.createStatement();
        if (stmt.executeUpdate(sql) <= 0) {
            throw new SQLException("The flight path in the database was not updated");
        }
    }

    /**
     * Updates the directory of the flight path in the database
     * @param routePath the route path object
     * @param newDirectory The new directory the RoutePath object will be stored
     */
    public void UpdateFlightPath(RoutePath routePath, String newDirectory) throws SQLException, Exception {
        ArrayList<Integer> airportIDsPair = new ArrayList<Integer>(2);
        try {
            airportIDsPair = GetAirportIDsFromRoutePath(routePath);
        } catch (Exception e) { throw e; }
        int sourceAirportID = airportIDsPair.get(0);
        int destinationAirportID = airportIDsPair.get(1);
        UpdateFlightPath(sourceAirportID, destinationAirportID, newDirectory);
    }

    /**
     * Deletes the flight path in the database
     * @param sourceAirportID The source airport ID of the airport the flight path departs from
     * @param destinationAirportID the destination airport ID of the airport the flight path arrives at
     */
    public void DeleteFlightPath(int sourceAirportID, int destinationAirportID) throws SQLException {
        Statement stmt = this.databaseConnection.createStatement();
        String sql = format("DELETE FROM flight_path WHERE sourceAirportID = %d AND destinationAirportID = %d",
                sourceAirportID, destinationAirportID);
        if (stmt.executeUpdate(sql) <= 0) {
            throw new SQLException("The flight path was not deleted from then database");
        }
    }

    /**
     * Deletes the flight path in the database
     * @param routePath routePath the route path object
     */
    public void DeleteFlightPath(RoutePath routePath) throws SQLException {
        ArrayList<Integer> airportIDsPair = new ArrayList<Integer>(2);
        try {
            airportIDsPair = GetAirportIDsFromRoutePath(routePath);
        } catch (Exception e) { throw e; }
        int sourceAirportID = airportIDsPair.get(0);
        int destinationAirportID = airportIDsPair.get(1);
        DeleteFlightPath(sourceAirportID, destinationAirportID);
    }

    /**
     * Inserts the holiday plan object's directory location into the database
     * @param name the name of the holiday plan object
     * @param directory the directory in which the holiday plan object resides
     */
    public void InsertHolidayPlan(String name, String directory) throws SQLException {
        String sql = format("INSERT INTO holiday_plan VALUES ('%s', '%s')",
                name, directory);
        Statement stmt = this.databaseConnection.createStatement();
        if (stmt.executeUpdate(sql) <= 0) {
            throw new SQLException("The holiday plan was not inserted into the database");
        }
    }

    /**
     * Updates the holiday plan in the database with a new location for the object
     * @param holidayPlanIndex the index of the holiday plan object in the array of holiday plans
     * @param newDirectory the new directory in which the holiday plan object will reside
     */
    public void UpdateHolidayPlan(String holidayPlanIndex, String newName, String newDirectory) throws Exception {
        String setSQL = "";
        if (newDirectory == null || newDirectory.isEmpty()) {
            setSQL += format("directory = %s, ", newDirectory);
        }
        if (newName != null && !newName.isEmpty()) {
            setSQL += format("name = %s, ", newName);
        }
        if (setSQL.length() > 0) {
            setSQL = setSQL.substring(0, setSQL.length() - 1);
        } else {
            throw new Exception("No parameters to update were provided!");
        }

        String sql = format("UPDATE holiday_path SET %s WHERE index_holiday_plan = '%s'", setSQL, holidayPlanIndex);
        Statement stmt = this.databaseConnection.createStatement();
        if (stmt.executeUpdate(sql) <= 0) {
            throw new SQLException("The holiday plan in the database was not updated");
        }
    }

    /**
     * Deletes the holiday plan entry from the database
     * @param name the name/key of the holiday plan object in the array of holiday plans
     */
    public void DeleteHolidayPlan(String name) throws SQLException {
        String sql = format("DELETE FROM holiday_plan WHERE name = '%s'", name);
        Statement stmt = this.databaseConnection.createStatement();
        if (stmt.executeUpdate(sql) <= 0) {
            throw new SQLException("Nothing was deleted");
        }
    }
}
