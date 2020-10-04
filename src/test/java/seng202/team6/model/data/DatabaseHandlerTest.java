package seng202.team6.model.data;

import org.junit.*;
import seng202.team6.model.entities.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Collections;
import java.util.ArrayList;

import static java.lang.String.format;
import static org.junit.Assert.*;

public class DatabaseHandlerTest {
    private DataImportHandler dataImport;
    private DataExportHandler dataExport;

    private ArrayList<Filter> filters;

    private Airline testAirline1;
    private Airline testAirline2;
    private Airline testAirline3;
    private Airline testAirline4;
    private Airline testAirline5;
    private ArrayList<Airline> testAirlines;
    private ArrayList<Airline> actualAirlines;

    private Airport testAirport1;
    private Airport testAirport2;
    private Airport testAirport3;
    private Airport testAirport4;
    private Airport testAirport5;
    private ArrayList<Airport> testAirports;
    private ArrayList<Airport> actualAirports;

    private Route testRoute1;
    private Route testRoute2;
    private Route testRoute3;
    private Route testRoute4;
    private Route testRoute5;
    private ArrayList<Route> testRoutes;
    private ArrayList<Route> actualRoutes;

    private Connection databaseConnection;
    private int databaseRowsAirline;
    private int databaseRowsAirport;
    private int databaseRowsRoute;

    public void fullClear() {
        filters.clear();
        testAirlines.clear();
        actualAirlines.clear();
        testAirports.clear();
        actualAirports.clear();
        testRoutes.clear();
        actualRoutes.clear();
    }

    public void cleanUp(Object object) {
        try {
            if (object instanceof Airline) {
                dataImport.DeleteAirline(((Airline) object).GetAirlineID());
            } else if (object instanceof Airport) {
                dataImport.DeleteAirport(((Airport) object).GetAirportID());
            } else if (object instanceof Route) {
                Route route = (Route) object;
                dataImport.DeleteRoute(route.GetAirlineID(), route.GetSourceAirportID(), route.GetDestinationAirportID());
            }
        } catch (Exception e) { System.out.println(e.getMessage()); }
    }

    public void cleanUpListArl(ArrayList<Airline> objects) {
        for (int i = 0; i < objects.size(); i++) {
            cleanUp(objects.get(i));
        }
    }
    public void cleanUpListApt(ArrayList<Airport> objects) {
        for (int i = 0; i < objects.size(); i++) {
            cleanUp(objects.get(i));
        }
    }
    public void cleanUpListRte(ArrayList<Route> objects) {
        for (int i = 0; i < objects.size(); i++) {
            cleanUp(objects.get(i));
        }
    }

    private boolean setup = false;
    @Before
    public void InitialiseTest() {
        if (!setup) {
            setup = true;
            dataImport = DataImportHandler.GetInstance();
            dataExport = DataExportHandler.GetInstance();
            filters = new ArrayList<Filter>();

            try {
                databaseConnection = DataHandler.GetInstance().GetConnection();
                Statement stmt = this.databaseConnection.createStatement();
                databaseRowsAirline = stmt.executeQuery("SELECT COUNT(*) FROM airline").getInt(1);
                databaseRowsAirport = stmt.executeQuery("SELECT COUNT(*) FROM airport").getInt(1);
                databaseRowsRoute = stmt.executeQuery("SELECT COUNT(*) FROM route").getInt(1);
            } catch (Exception e) { System.out.println(e.getMessage()); }

            testAirline1 = new Airline(1, "Virgin Airlines", "Virgin", "VI",
                    "VRG", "VIRGIN", "Australia", 'Y');
            testAirline2 = new Airline(2, "Singapore Airlines", "Singapore", "SN",
                    "SRE", "SINGAPORE", "Signapore", 'Y');
            testAirline3 = new Airline(3, "Qatar Airways", "Qatar", "QA",
                    "QAR", "QATAR", "Qatar", 'Y');
            testAirline4 = new Airline(4, "Emirates", "Emirates", "EM",
                    "ERS", "EMIRATES", "United Arab Emirates", 'Y');
            testAirline5 = new Airline(5, "Lufthansa", "Luft", "LF",
                    "LFS", "LUFTHANSA", "Germany", 'Y');
            testAirlines = new ArrayList<Airline>();
            actualAirlines = new ArrayList<Airline>();

            testAirport1 = new Airport(1, "London Heathrow Airport", "London", "England",
                    "LHR", "LOND", (float)51.470020, (float)-0.454295, 25, 1, 'U');
            testAirport2 = new Airport(2, "Los Angeles Airport", "Los Angeles", "United States of America",
                    "LAX", "LOSX", (float)33.94279, (float)-118.410042, 38, -7, 'U');
            testAirport3 = new Airport(3, "Tokyo Haneda Airport", "Tokyo", "Japan",
                    "HND", "HNDA", (float)35.5494, (float)139.7798, 11, 9, 'A');
            testAirport4 = new Airport(4, "Amsterdam Airport Schipol", "Amsterdam", "Netherlands",
                    "AMS", "AMSD", (float)52.3105, (float)4.7683, -3, 1, 'A');
            testAirport5 = new Airport(5, "Hong Kong Airport", "Hong Kong", "Hong Kong",
                    "HKG", "HGKG", (float)22.3080, (float)113.9185, 9, 8, 'A');
            testAirports = new ArrayList<Airport>();
            actualAirports = new ArrayList<Airport>();

            testRoute1 = new Route(testAirline1.GetAirlineID(), testAirline1.GetName(), testAirport1.GetName(), testAirport1.GetAirportID(),
                    testAirport2.GetName(), testAirport2.GetAirportID(), ' ', 0, "CR2");
            testRoute2 = new Route(testAirline2.GetAirlineID(), testAirline2.GetName(), testAirport3.GetName(), testAirport3.GetAirportID(),
                    testAirport4.GetName(), testAirport4.GetAirportID(), ' ', 0, "CR2");
            testRoute3 = new Route(testAirline3.GetAirlineID(), testAirline3.GetName(), testAirport5.GetName(), testAirport5.GetAirportID(),
                    testAirport1.GetName(), testAirport1.GetAirportID(), ' ', 0, "A81");
            testRoute4 = new Route(testAirline4.GetAirlineID(), testAirline4.GetName(), testAirport2.GetName(), testAirport2.GetAirportID(),
                    testAirport3.GetName(), testAirport3.GetAirportID(), ' ', 0, "AN4");
            testRoute5 = new Route(testAirline5.GetAirlineID(), testAirline5.GetName(), testAirport4.GetName(), testAirport4.GetAirportID(),
                    testAirport5.GetName(), testAirport5.GetAirportID(), ' ', 0, "142");
            testRoutes = new ArrayList<Route>();
            actualRoutes = new ArrayList<Route>();
        }
    }

    @After
    public void checkNothingWasAddedToDatabase() {
        try {
            databaseConnection = DataHandler.GetInstance().GetConnection();
            Statement stmt = this.databaseConnection.createStatement();
            int afterDatabaseRowsAirline = stmt.executeQuery("SELECT COUNT(*) FROM airline").getInt(1);
            int afterDatabaseRowsAirport = stmt.executeQuery("SELECT COUNT(*) FROM airport").getInt(1);
            int afterDatabaseRowsRoute = stmt.executeQuery("SELECT COUNT(*) FROM route").getInt(1);
            if (afterDatabaseRowsAirline != databaseRowsAirline ||
                    afterDatabaseRowsAirport != databaseRowsAirport ||
                    afterDatabaseRowsRoute != databaseRowsRoute) {
                Assert.fail("Test case rows were added to the database. A test case needs to be fixed to prevent this!");
            }
        } catch (Exception e) { System.out.println(e.getMessage()); }
    }

    /**
     * Test inserting one airline into the database
     */
    @Test
    public void testInsertOneAirline() {
        try {
            dataImport.InsertAirline(testAirline1);
            Filter filter = new Filter(format("icao = '%s'", testAirline1.GetICAO()), "");
            filters.add(filter);
            actualAirlines = dataExport.FetchAirlines(filters);
            testAirline1.SetAirlineID(actualAirlines.get(0).GetAirlineID());
        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
        Assert.assertEquals(testAirline1, actualAirlines.get(0));
        cleanUp(testAirline1);
        fullClear();
    }

    /**
     * Test inserting two airlines into the database
     */
    @Test
    public void TestInsertTwoAirlines() {
        testAirlines.add(testAirline1);
        testAirlines.add(testAirline2);
        try {
            for (Airline airline: testAirlines) {
                dataImport.InsertAirline(airline);
            }
            Filter filter1 = new Filter(format("icao = '%s'", testAirline1.GetICAO()), "OR");
            filters.add(filter1);
            Filter filter2 = new Filter(format("icao = '%s'", testAirline2.GetICAO()), "");
            filters.add(filter2);
            actualAirlines = dataExport.FetchAirlines(filters);
        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
        testAirlines.get(0).SetAirlineID(actualAirlines.get(0).GetAirlineID());
        testAirlines.get(1).SetAirlineID(actualAirlines.get(1).GetAirlineID());

        for (int i = 0; i < 2; i++) {
            Assert.assertEquals(testAirlines.get(i), actualAirlines.get(i));
        }
        cleanUpListArl(testAirlines);
        fullClear();
    }

    /**
     * Test inserting five airlines into the database
     */
    @Test
    public void TestInsertFiveAirlines() {
        testAirlines.add(testAirline1);
        testAirlines.add(testAirline2);
        testAirlines.add(testAirline3);
        testAirlines.add(testAirline4);
        testAirlines.add(testAirline5);
        try {
            for (Airline airline: testAirlines) {
                dataImport.InsertAirline(airline);
            }
            Filter filter1 = new Filter(format("icao = '%s'", testAirline1.GetICAO()), "OR");
            filters.add(filter1);
            Filter filter2 = new Filter(format("icao = '%s'", testAirline2.GetICAO()), "OR");
            filters.add(filter2);
            Filter filter3 = new Filter(format("icao = '%s'", testAirline3.GetICAO()), "OR");
            filters.add(filter3);
            Filter filter4 = new Filter(format("icao = '%s'", testAirline4.GetICAO()), "OR");
            filters.add(filter4);
            Filter filter5 = new Filter(format("icao = '%s'", testAirline5.GetICAO()), "");
            filters.add(filter5);
            actualAirlines = dataExport.FetchAirlines(filters);
        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
        testAirlines.get(0).SetAirlineID(actualAirlines.get(0).GetAirlineID());
        testAirlines.get(1).SetAirlineID(actualAirlines.get(1).GetAirlineID());
        testAirlines.get(2).SetAirlineID(actualAirlines.get(2).GetAirlineID());
        testAirlines.get(3).SetAirlineID(actualAirlines.get(3).GetAirlineID());
        testAirlines.get(4).SetAirlineID(actualAirlines.get(4).GetAirlineID());

        for (int i = 0; i < 5; i++) {
            Assert.assertEquals(testAirlines.get(i), actualAirlines.get(i));
        }
        cleanUpListArl(testAirlines);
        fullClear();
    }

    /**
     * Test inserting one airport into the database
     */
    @Test
    public void TestInsertOneAirport() {
        try {
            dataImport.InsertAirport(testAirport1);
            Filter filter = new Filter(format("icao = '%s'", testAirport1.GetICAO()), "");
            filters.add(filter);
            actualAirports = dataExport.FetchAirports(filters);
            testAirport1.SetAirportID(actualAirports.get(0).GetAirportID());
        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
        Assert.assertEquals(testAirport1, actualAirports.get(0));
        cleanUp(testAirport1);
        fullClear();
    }

    /**
     * Test inserting two airports into the database
     */
    @Test
    public void TestInsertTwoAirports() {
        testAirports.add(testAirport1);
        testAirports.add(testAirport3);
        try {
            for (Airport airport: testAirports) {
                dataImport.InsertAirport(airport);
            }
            Filter filter1 = new Filter(format("icao = '%s'", testAirport1.GetICAO()), "OR");
            filters.add(filter1);
            Filter filter2 = new Filter(format("icao = '%s'", testAirport3.GetICAO()), "");
            filters.add(filter2);
            actualAirports = dataExport.FetchAirports(filters);
        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
        testAirports.get(0).SetAirportID(actualAirports.get(0).GetAirportID());
        testAirports.get(1).SetAirportID(actualAirports.get(1).GetAirportID());

        for (int i = 0; i < 2; i++) {
            Assert.assertEquals(testAirports.get(i), actualAirports.get(i));
        }
        cleanUpListApt(testAirports);
        fullClear();
    }

    /**
     * Test inserting five airports in the database
     */
    @Test
    public void TestInsertFiveAirports() {
        testAirports.add(testAirport1);
        testAirports.add(testAirport2);
        testAirports.add(testAirport3);
        testAirports.add(testAirport4);
        testAirports.add(testAirport5);
        try {
            for (Airport airport: testAirports) {
                dataImport.InsertAirport(airport);
            }
            Filter filter1 = new Filter(format("icao = '%s'", testAirport1.GetICAO()), "OR");
            filters.add(filter1);
            Filter filter2 = new Filter(format("icao = '%s'", testAirport2.GetICAO()), "OR");
            filters.add(filter2);
            Filter filter3 = new Filter(format("icao = '%s'", testAirport3.GetICAO()), "OR");
            filters.add(filter3);
            Filter filter4 = new Filter(format("icao = '%s'", testAirport4.GetICAO()), "OR");
            filters.add(filter4);
            Filter filter5 = new Filter(format("icao = '%s'", testAirport5.GetICAO()), "");
            filters.add(filter5);
            actualAirports = dataExport.FetchAirports(filters);
        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
        testAirports.get(0).SetAirportID(actualAirports.get(0).GetAirportID());
        testAirports.get(1).SetAirportID(actualAirports.get(1).GetAirportID());
        testAirports.get(2).SetAirportID(actualAirports.get(2).GetAirportID());
        testAirports.get(3).SetAirportID(actualAirports.get(3).GetAirportID());
        testAirports.get(4).SetAirportID(actualAirports.get(4).GetAirportID());

        for (int i = 0; i < 5; i++) {
            Assert.assertEquals(testAirports.get(i), actualAirports.get(i));
        }
        cleanUpListApt(testAirports);
        fullClear();
    }

    /**
     * Test inserting one route into the database
     */
    @Test
    public void TestInsertOneRoute() {
        try {
            dataImport.InsertRoute(testRoute1);
            Filter filter = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id " +
                            " = %d", testRoute1.GetAirlineID(), testRoute1.GetSourceAirportID(),
                    testRoute1.GetDestinationAirportID()), "");
            filters.add(filter);
            actualRoutes = dataExport.FetchRoutes(filters);
        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
        Assert.assertEquals(testRoute1, actualRoutes.get(0));
        cleanUp(testRoute1);
        fullClear();
    }

    /**
     * Test inserting two routes into the database
     */
    @Test
    public void TestInsertTwoRoutes() {
        testRoutes.add(testRoute1);
        testRoutes.add(testRoute2);
        try {
            for (Route route: testRoutes) {
                dataImport.InsertRoute(route);
            }
            Filter filter1 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id" +
                            " = %d", testRoute1.GetAirlineID(), testRoute1.GetSourceAirportID(),
                    testRoute1.GetDestinationAirportID()), "OR");
            filters.add(filter1);
            Filter filter2 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id" +
                            " = %d", testRoute2.GetAirlineID(), testRoute2.GetSourceAirportID(),
                    testRoute2.GetDestinationAirportID()), "");
            filters.add(filter2);
            actualRoutes = dataExport.FetchRoutes(filters);
        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
        Collections.sort(testRoutes);
        Collections.sort(actualRoutes);
        for (int i = 0; i < 2; i++) {
            Assert.assertEquals(testRoutes.get(i), actualRoutes.get(i));
        }
        cleanUpListRte(testRoutes);
        fullClear();
    }

    /**
     * Test inserting five routes into the database
     */
    @Test
    public void TestInsertFiveRoutes() {
        testRoutes.add(testRoute1);
        testRoutes.add(testRoute2);
        testRoutes.add(testRoute3);
        testRoutes.add(testRoute4);
        testRoutes.add(testRoute5);
        try {
            for (Route route: testRoutes) {
                dataImport.InsertRoute(route);
            }
            Filter filter1 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id" +
                            " = %d", testRoute1.GetAirlineID(), testRoute1.GetSourceAirportID(),
                    testRoute1.GetDestinationAirportID()), "OR");
            filters.add(filter1);
            Filter filter2 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id" +
                            " = %d", testRoute2.GetAirlineID(), testRoute2.GetSourceAirportID(),
                    testRoute2.GetDestinationAirportID()), "OR");
            filters.add(filter2);
            Filter filter3 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id" +
                            " = %d", testRoute3.GetAirlineID(), testRoute3.GetSourceAirportID(),
                    testRoute3.GetDestinationAirportID()), "OR");
            filters.add(filter3);
            Filter filter4 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id" +
                            " = %d", testRoute4.GetAirlineID(), testRoute4.GetSourceAirportID(),
                    testRoute4.GetDestinationAirportID()), "OR");
            filters.add(filter4);
            Filter filter5 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id" +
                            " = %d", testRoute5.GetAirlineID(), testRoute5.GetSourceAirportID(),
                    testRoute5.GetDestinationAirportID()), "");
            filters.add(filter5);

            actualRoutes = dataExport.FetchRoutes(filters);
        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
        Collections.sort(testRoutes);
        Collections.sort(actualRoutes);
        for (int i = 0; i < 5; i++) {
            Assert.assertEquals(testRoutes.get(i), actualRoutes.get(i));
        }
        cleanUpListRte(testRoutes);
        fullClear();
    }

    /**
     * Test updating one airline within the database
     */
    @Test
    public void TestUpdateOneAirline() {
        try {
            dataImport.InsertAirline(testAirline1);
            filters.add(new Filter(format("icao = '%s'", testAirline1.GetICAO()), ""));
            testAirline1.SetAirlineID(dataExport.FetchAirlines(filters).get(0).GetAirlineID());
            filters.clear();
        } catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.UpdateAirline(testAirline1.GetAirlineID(), "VirginBlue", null, null, null, null,
                    null, null);
            Filter filter = new Filter(format("id_airline = %d", testAirline1.GetAirlineID()), "");
            filters.add(filter);
            actualAirlines = dataExport.FetchAirlines(filters);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        testAirline1.SetName("VirginBlue");
        assertEquals(testAirline1, actualAirlines.get(0));
        cleanUp(testAirline1);
        fullClear();
    }

    /**
     * Test updating two airlines within the database
     */
    @Test
    public void TestUpdateTwoAirlines() {
        testAirlines.add(testAirline1);
        testAirlines.add(testAirline2);
        try {
            for (Airline airline: testAirlines) {
                dataImport.InsertAirline(airline);
                filters.add(new Filter(format("icao = '%s'", airline.GetICAO()), ""));
                airline.SetAirlineID(dataExport.FetchAirlines(filters).get(0).GetAirlineID());
                filters.clear();
            }
        } catch (Exception e) {Assert.fail(e.getMessage());}
        int j = 0;
        ArrayList<String> newNames = new ArrayList<>();
        newNames.add("VirginGreen");
        newNames.add("VirginPurple");
        try {
            for (Airline airline: testAirlines) {
                dataImport.UpdateAirline(airline.GetAirlineID(), newNames.get(j), null, null, null, null,
                        null, null);
                Filter filter = new Filter(format("id_airline = %d", airline.GetAirlineID()), "OR");
                filters.add(filter);
                j++;
            }
            actualAirlines = dataExport.FetchAirlines(filters);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        for (int k = 0; k < 2; k++) {
            testAirlines.get(k).SetName(newNames.get(k));
        }
        for (int i = 0; i < 2; i++) {
            assertEquals(testAirlines.get(i), actualAirlines.get(i));
        }
        cleanUpListArl(testAirlines);
        fullClear();
    }

    /**
     * Test updating five airlines within the database
     */
    @Test
    public void TestUpdateFiveAirlines() {
        testAirlines.add(testAirline1);
        testAirlines.add(testAirline2);
        testAirlines.add(testAirline3);
        testAirlines.add(testAirline4);
        testAirlines.add(testAirline5);
        try {
            for (Airline airline: testAirlines) {
                dataImport.InsertAirline(airline);
                filters.add(new Filter(format("icao = '%s'", airline.GetICAO()), ""));
                airline.SetAirlineID(dataExport.FetchAirlines(filters).get(0).GetAirlineID());
                filters.clear();
            }
        } catch (Exception e) {Assert.fail(e.getMessage());}
        int j = 0;
        ArrayList<String> newNames = new ArrayList<>();
        newNames.add("VirginAlpha");
        newNames.add("VirginBeta");
        newNames.add("VirginCharline");
        newNames.add("VirginDelta");
        newNames.add("VirginEcho");
        try {
            for (Airline airline: testAirlines) {
                dataImport.UpdateAirline(airline.GetAirlineID(), newNames.get(j), null, null, null, null,
                        null, null);
                Filter filter = new Filter(format("id_airline = %d", airline.GetAirlineID()), "OR");
                filters.add(filter);
                j++;
            }
            actualAirlines = dataExport.FetchAirlines(filters);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        for (int k = 0; k < 5; k++) {
            testAirlines.get(k).SetName(newNames.get(k));
        }
        for (int i = 0; i < 5; i++) {
            assertEquals(testAirlines.get(i), actualAirlines.get(i));
        }
        cleanUpListArl(testAirlines);
        fullClear();
    }

    /**
     * Test updating an airline with empty parameters (should throw exception) within the database
     */
    @Test
    public void TestUpdateAirlineEmpty() {
        try {
            dataImport.InsertAirline(testAirline1);
            filters.add(new Filter(format("icao = '%s'", testAirline1.GetICAO()), ""));
            testAirline1.SetAirlineID(dataExport.FetchAirlines(filters).get(0).GetAirlineID());
            filters.clear();
        } catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.UpdateAirline(testAirline1.GetAirlineID(), null, null, null, null, null,
                    null, null);
            Assert.fail("Should have failed by now.");
        } catch (Exception e) {
            String message = "No parameters to update were provided!";
            assertEquals(message, e.getMessage());
        }
        cleanUp(testAirline1);
        fullClear();
    }

    /**
     * Test updating invalid IATA with one character within the database
     */
    @Test
    public void TestUpdateAirlineOneCharIATA() {
        try {
            dataImport.InsertAirline(testAirline1);
            filters.add(new Filter(format("icao = '%s'", testAirline1.GetICAO()), ""));
            testAirline1.SetAirlineID(dataExport.FetchAirlines(filters).get(0).GetAirlineID());
            filters.clear();
        } catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.UpdateAirline(testAirline1.GetAirlineID(), null, null, "W", null, null,
                    null, null);
            Assert.fail("Should have failed by now.");
        } catch (Exception e) {
            String message = "The provided IATA was not two characters long!";
            assertEquals(message, e.getMessage());
        }
        cleanUp(testAirline1);
        fullClear();
    }

    /**
     * Test updating invalid IATA with three characters within the database
     */
    @Test
    public void TestUpdateAirlineThreeCharIATA() {
        try {
            dataImport.InsertAirline(testAirline1);
            filters.add(new Filter(format("icao = '%s'", testAirline1.GetICAO()), ""));
            testAirline1.SetAirlineID(dataExport.FetchAirlines(filters).get(0).GetAirlineID());
            filters.clear();
        } catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.UpdateAirline(testAirline1.GetAirlineID(), null, null, "WAP", null, null,
                    null, null);
            Assert.fail("Should have failed by now.");
        } catch (Exception e) {
            String message = "The provided IATA was not two characters long!";
            assertEquals(message, e.getMessage());
        }
        cleanUp(testAirline1);
        fullClear();
    }

    /**
     * Test updating invalid ICAO with two characters within the database
     */
    @Test
    public void TestUpdateAirlineTwoCharICAO() {
        try {
            dataImport.InsertAirline(testAirline1);
            filters.add(new Filter(format("icao = '%s'", testAirline1.GetICAO()), ""));
            testAirline1.SetAirlineID(dataExport.FetchAirlines(filters).get(0).GetAirlineID());
            filters.clear();
        } catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.UpdateAirline(testAirline1.GetAirlineID(), null, null, null, "WA", null,
                    null, null);
            Assert.fail("Should have failed by now.");
        } catch (Exception e) {
            String message = "The provided ICAO was not three characters long!";
            assertEquals(message, e.getMessage());
        }
        cleanUp(testAirline1);
        fullClear();
    }

    /**
     * Test updating invalid ICAO with four characters within the database
     */
    @Test
    public void TestUpdateAirlineFourCharICAO() {
        try {
            dataImport.InsertAirline(testAirline1);
            filters.add(new Filter(format("icao = '%s'", testAirline1.GetICAO()), ""));
            testAirline1.SetAirlineID(dataExport.FetchAirlines(filters).get(0).GetAirlineID());
            filters.clear();
        } catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.UpdateAirline(testAirline1.GetAirlineID(), null, null, null, "WAPP", null,
                    null, null);
            Assert.fail("Should have failed by now.");
        } catch (Exception e) {
            String message = "The provided ICAO was not three characters long!";
            assertEquals(message, e.getMessage());
        }
        cleanUp(testAirline1);
        fullClear();
    }

    /**
     * Test updating one airport within the database
     */
    @Test
    public void TestUpdateOneAirport() {
        try {
            dataImport.InsertAirport(testAirport1);
            filters.add(new Filter(format("icao = '%s'", testAirport1.GetICAO()), ""));
            testAirport1.SetAirportID(dataExport.FetchAirports(filters).get(0).GetAirportID());
            filters.clear();
        } catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.UpdateAirport(testAirport1.GetAirportID(), "Alpha", null, null, null, null,
                    null, null, null, null, null);
            Filter filter = new Filter(format("icao = '%s'", testAirport1.GetICAO()), "");
            filters.add(filter);
            actualAirports = dataExport.FetchAirports(filters);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        testAirport1.SetName("Alpha");
        assertEquals(testAirport1, actualAirports.get(0));
        cleanUp(testAirport1);
        fullClear();
    }

    /**
     * Test updating two airports within the database
     */
    @Test
    public void TestUpdateTwoAirports() {
        testAirports.add(testAirport1);
        testAirports.add(testAirport2);
        try {
            for (Airport airport: testAirports) {
                dataImport.InsertAirport(airport);
                filters.add(new Filter(format("icao = '%s'", airport.GetICAO()), ""));
                airport.SetAirportID(dataExport.FetchAirports(filters).get(0).GetAirportID());
                filters.clear();
            }
        } catch (Exception e) {Assert.fail(e.getMessage());}
        int j = 0;
        ArrayList<String> newNames = new ArrayList<>();
        newNames.add("Bravo");
        newNames.add("Charlie");
        try {
            for (Airport airport: testAirports) {
                dataImport.UpdateAirport(airport.GetAirportID(), newNames.get(j), null, null, null, null,
                        null, null, null, null, null);
                Filter filter = new Filter(format("id_airport = %d", airport.GetAirportID()), "OR");
                filters.add(filter);
                j++;
            }
            actualAirports = dataExport.FetchAirports(filters);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        for (int k = 0; k < 2; k++) {
            testAirports.get(k).SetName(newNames.get(k));
        }
        for (int i = 0; i < 2; i++) {
            assertEquals(testAirports.get(i), actualAirports.get(i));
        }
        cleanUpListApt(testAirports);
        fullClear();
    }

    /**
     * Test updating five airports within the database
     */
    @Test
    public void TestUpdateFiveAirports() {
        testAirports.add(testAirport1);
        testAirports.add(testAirport2);
        testAirports.add(testAirport3);
        testAirports.add(testAirport4);
        testAirports.add(testAirport5);
        try {
            for (Airport airport: testAirports) {
                dataImport.InsertAirport(airport);
                filters.add(new Filter(format("icao = '%s'", airport.GetICAO()), ""));
                airport.SetAirportID(dataExport.FetchAirports(filters).get(0).GetAirportID());
                filters.clear();
            }
        } catch (Exception e) {Assert.fail(e.getMessage());}
        int j = 0;
        ArrayList<String> newNames = new ArrayList<>();
        newNames.add("Delta");
        newNames.add("Echo");
        newNames.add("Foxtrot");
        newNames.add("Golf");
        newNames.add("Hotel");
        try {
            for (Airport airport: testAirports) {
                dataImport.UpdateAirport(airport.GetAirportID(), newNames.get(j), null, null, null, null,
                        null, null, null, null, null);
                Filter filter = new Filter(format("id_airport = %d", airport.GetAirportID()), "OR");
                filters.add(filter);
                j++;
            }
            actualAirports = dataExport.FetchAirports(filters);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        for (int k = 0; k < 5; k++) {
            testAirports.get(k).SetName(newNames.get(k));
        }
        for (int i = 0; i < 5; i++) {
            assertEquals(testAirports.get(i), actualAirports.get(i));
        }
        cleanUpListApt(testAirports);
        fullClear();
    }

    /**
     * Test updating an airport with empty parameters within the database
     */
    @Test
    public void TestUpdateAirportEmpty() {
        try {
            dataImport.InsertAirport(testAirport1);
            filters.add(new Filter(format("icao = '%s'", testAirport1.GetICAO()), ""));
            testAirport1.SetAirportID(dataExport.FetchAirports(filters).get(0).GetAirportID());
            filters.clear();
        } catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.UpdateAirport(testAirport1.GetAirportID(), null, null, null, null, null,
                    null, null, null, null, null);
            Assert.fail("Should have failed by now.");
        } catch (Exception e) {
            String message = "No parameters to update were provided!";
            assertEquals(message, e.getMessage());
        }
        cleanUp(testAirport1);
        fullClear();
    }

    /**
     * Test updating invalid IATA with two characters within the database
     */
    @Test
    public void TestUpdateAirportTwoCharIATA() {
        try {
            dataImport.InsertAirport(testAirport1);
            filters.add(new Filter(format("icao = '%s'", testAirport1.GetICAO()), ""));
            testAirport1.SetAirportID(dataExport.FetchAirports(filters).get(0).GetAirportID());
            filters.clear();
        } catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.UpdateAirport(testAirport1.GetAirportID(), null, null, null, "BO", null,
                    null, null, null, null, null);
            Assert.fail("Should have failed by now.");
        } catch (Exception e) {
            String message = "The provided IATA was not three characters long!";
            assertEquals(message, e.getMessage());
        }
        cleanUp(testAirport1);
        fullClear();
    }

    /**
     * Test updating invalid IATA with four characters within the database
     */
    @Test
    public void TestUpdateAirportFourCharIATA() {
        try {
            dataImport.InsertAirport(testAirport1);
            filters.add(new Filter(format("icao = '%s'", testAirport1.GetICAO()), ""));
            testAirport1.SetAirportID(dataExport.FetchAirports(filters).get(0).GetAirportID());
            filters.clear();
        } catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.UpdateAirport(testAirport1.GetAirportID(), null, null, null, "BOII", null,
                    null, null, null, null, null);
            Assert.fail("Should have failed by now.");
        } catch (Exception e) {
            String message = "The provided IATA was not three characters long!";
            assertEquals(message, e.getMessage());
        }
        cleanUp(testAirport1);
        fullClear();
    }

    /**
     * Test updating invalid ICAO with three characters within the database
     */
    @Test
    public void TestUpdateAirportThreeCharICAO() {
        try {
            dataImport.InsertAirport(testAirport1);
            filters.add(new Filter(format("icao = '%s'", testAirport1.GetICAO()), ""));
            testAirport1.SetAirportID(dataExport.FetchAirports(filters).get(0).GetAirportID());
            filters.clear();
        } catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.UpdateAirport(testAirport1.GetAirportID(), null, null, null, null, "SUG",
                    null, null, null, null, null);
            Assert.fail("Should have failed by now.");
        } catch (Exception e) {
            String message = "The provided ICAO was not four characters long!";
            assertEquals(message, e.getMessage());
        }
        cleanUp(testAirport1);
        fullClear();
    }

    /**
     * Test updating invalid ICAO with five characters within the database
     */
    @Test
    public void TestUpdateAirportFiveCharICAO() {
        try {
            dataImport.InsertAirport(testAirport1);
            filters.add(new Filter(format("icao = '%s'", testAirport1.GetICAO()), ""));
            testAirport1.SetAirportID(dataExport.FetchAirports(filters).get(0).GetAirportID());
            filters.clear();
        } catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.UpdateAirport(testAirport1.GetAirportID(), null, null, null, null, "SUGAR",
                    null, null, null, null, null);
            Assert.fail("Should have failed by now.");
        } catch (Exception e) {
            String message = "The provided ICAO was not four characters long!";
            assertEquals(message, e.getMessage());
        }
        cleanUp(testAirport1);
        fullClear();
    }

    /**
     * Test updating a route within the database
     */
    @Test
    public void TestUpdateOneRoute() {
        try {
            dataImport.InsertRoute(testRoute1);} catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.UpdateRoute(testRoute1.GetAirlineID(), testRoute1.GetSourceAirportID(), testRoute1.GetDestinationAirportID(),
                    null, null, null, null, 5, null);
            Filter filter = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                    testRoute1.GetAirlineID(), testRoute1.GetSourceAirportID(), testRoute1.GetDestinationAirportID()), "");
            filters.add(filter);
            actualRoutes = dataExport.FetchRoutes(filters);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        testRoute1.SetStops(5);
        assertEquals(testRoute1, actualRoutes.get(0));
        cleanUp(testRoute1);
        fullClear();
    }

    /**
     * Test updating two routes within the database
     */
    @Test
    public void TestUpdateTwoRoutes() {
        testRoutes.add(testRoute1);
        testRoutes.add(testRoute2);
        try {
            for (Route route: testRoutes) {
                dataImport.InsertRoute(route);
            }
        } catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.UpdateRoute(testRoute1.GetAirlineID(), testRoute1.GetSourceAirportID(), testRoute1.GetDestinationAirportID(),
                    null, null, null, null, 6, null);
            Filter filter1 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                    testRoute1.GetAirlineID(), testRoute1.GetSourceAirportID(), testRoute1.GetDestinationAirportID()), "OR");
            filters.add(filter1);
            dataImport.UpdateRoute(testRoute2.GetAirlineID(), testRoute2.GetSourceAirportID(), testRoute2.GetDestinationAirportID(),
                    null, null, null, null, 7, null);
            Filter filter2 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                    testRoute2.GetAirlineID(), testRoute2.GetSourceAirportID(), testRoute2.GetDestinationAirportID()), "");
            filters.add(filter2);
            actualRoutes = dataExport.FetchRoutes(filters);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        testRoute1.SetStops(6);
        testRoute2.SetStops(7);
        Collections.sort(testRoutes);
        Collections.sort(actualRoutes);
        for (int i = 0; i < 2; i++) {
            assertEquals(testRoutes.get(i), actualRoutes.get(i));
        }
        cleanUpListRte(testRoutes);
        fullClear();
    }

    /**
     * Test updating five routes within the database
     */
    @Test
    public void TestUpdateFiveRoutes() {
        testRoutes.add(testRoute1);
        testRoutes.add(testRoute2);
        testRoutes.add(testRoute3);
        testRoutes.add(testRoute4);
        testRoutes.add(testRoute5);
        try {
            for (Route route: testRoutes) {
                dataImport.InsertRoute(route);
            }
        } catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.UpdateRoute(testRoute1.GetAirlineID(), testRoute1.GetSourceAirportID(), testRoute1.GetDestinationAirportID(),
                    null, null, null, null, 8, null);
            Filter filter1 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                    testRoute1.GetAirlineID(), testRoute1.GetSourceAirportID(), testRoute1.GetDestinationAirportID()), "OR");
            filters.add(filter1);
            dataImport.UpdateRoute(testRoute2.GetAirlineID(), testRoute2.GetSourceAirportID(), testRoute2.GetDestinationAirportID(),
                    null, null, null, null, 9, null);
            Filter filter2 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                    testRoute2.GetAirlineID(), testRoute2.GetSourceAirportID(), testRoute2.GetDestinationAirportID()), "OR");
            filters.add(filter2);
            dataImport.UpdateRoute(testRoute3.GetAirlineID(), testRoute3.GetSourceAirportID(), testRoute3.GetDestinationAirportID(),
                    null, null, null, null, 10, null);
            Filter filter3 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                    testRoute3.GetAirlineID(), testRoute3.GetSourceAirportID(), testRoute3.GetDestinationAirportID()), "OR");
            filters.add(filter3);
            dataImport.UpdateRoute(testRoute4.GetAirlineID(), testRoute4.GetSourceAirportID(), testRoute4.GetDestinationAirportID(),
                    null, null, null, null, 11, null);
            Filter filter4 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                    testRoute4.GetAirlineID(), testRoute4.GetSourceAirportID(), testRoute4.GetDestinationAirportID()), "OR");
            filters.add(filter4);
            dataImport.UpdateRoute(testRoute5.GetAirlineID(), testRoute5.GetSourceAirportID(), testRoute5.GetDestinationAirportID(),
                    null, null, null, null, 12, null);
            Filter filter5 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                    testRoute5.GetAirlineID(), testRoute5.GetSourceAirportID(), testRoute5.GetDestinationAirportID()), "");
            filters.add(filter5);
            actualRoutes = dataExport.FetchRoutes(filters);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        testRoute1.SetStops(8);
        testRoute2.SetStops(9);
        testRoute3.SetStops(10);
        testRoute4.SetStops(11);
        testRoute5.SetStops(12);
        Collections.sort(testRoutes);
        Collections.sort(actualRoutes);
        for (int i = 0; i < 2; i++) {
            assertEquals(testRoutes.get(i), actualRoutes.get(i));
        }
        cleanUpListRte(testRoutes);
        fullClear();
    }

    /**
     * Test updating a route with empty parameters (should throw exception) within the database
     */
    @Test
    public void TestUpdateRouteEmpty() {
        try {
            dataImport.InsertRoute(testRoute1);} catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.UpdateRoute(testRoute1.GetAirlineID(), testRoute1.GetSourceAirportID(), testRoute1.GetDestinationAirportID(),
                    null, null, null, null, null, null);
            Assert.fail("Should have failed by now.");
        } catch (Exception e) {
            String message = "No parameters to update were provided!";
            assertEquals(message, e.getMessage());
        }
        cleanUp(testRoute1);
        fullClear();
    }

    /**
     * Test deleting an airline in the database
     */
    @Test
    public void TestDeleteOneAirline() {
        try {
            dataImport.InsertAirline(testAirline1);
            filters.add(new Filter(format("icao = '%s'", testAirline1.GetICAO()), ""));
            testAirline1.SetAirlineID(dataExport.FetchAirlines(filters).get(0).GetAirlineID());
            filters.clear();
            Filter filter = new Filter(format("id_airline = %d", testAirline1.GetAirlineID()), "");
            filters.add(filter);
            dataImport.DeleteAirline(testAirline1.GetAirlineID());
        } catch (Exception e) {Assert.fail(e.getMessage());}
        actualAirlines = dataExport.FetchAirlines(filters);
        assertTrue(actualAirlines.isEmpty());
        cleanUp(testAirline1);
        fullClear();
    }

    /**
     * Test deleting two airlines in the database
     */
    @Test
    public void TestDeleteTwoAirlines() {
        testAirlines.add(testAirline1);
        testAirlines.add(testAirline2);
        try {
            for (Airline airline: testAirlines) {
                dataImport.InsertAirline(airline);
                filters.add(new Filter(format("icao = '%s'", airline.GetICAO()), ""));
                airline.SetAirlineID(dataExport.FetchAirlines(filters).get(0).GetAirlineID());
                filters.clear();
            }
        } catch (Exception e) {Assert.fail(e.getMessage());}
        Filter filter1 = new Filter(format("id_airline = %d", testAirline1.GetAirlineID()), "OR");
        filters.add(filter1);
        Filter filter2 = new Filter(format("id_airline = %d", testAirline2.GetAirlineID()), "");
        filters.add(filter2);
        try {
            dataImport.DeleteAirline(testAirline1.GetAirlineID());
            dataImport.DeleteAirline(testAirline2.GetAirlineID());
        } catch (Exception e) {Assert.fail(e.getMessage());}
        actualAirlines = dataExport.FetchAirlines(filters);
        assertTrue(actualAirlines.isEmpty());
        cleanUpListArl(testAirlines);
        fullClear();
    }

    /**
     * Test deleting five airlines in the database
     */
    @Test
    public void TestDeleteFiveAirlines() {
        testAirlines.add(testAirline1);
        testAirlines.add(testAirline2);
        testAirlines.add(testAirline3);
        testAirlines.add(testAirline4);
        testAirlines.add(testAirline5);
        try {
            for (Airline airline: testAirlines) {
                dataImport.InsertAirline(airline);
                filters.add(new Filter(format("icao = '%s'", airline.GetICAO()), ""));
                airline.SetAirlineID(dataExport.FetchAirlines(filters).get(0).GetAirlineID());
                filters.clear();
            }
        } catch (Exception e) {Assert.fail(e.getMessage());}
        Filter filter1 = new Filter(format("id_airline = %d", testAirline1.GetAirlineID()), "OR");
        filters.add(filter1);
        Filter filter2 = new Filter(format("id_airline = %d", testAirline2.GetAirlineID()), "OR");
        filters.add(filter2);
        Filter filter3 = new Filter(format("id_airline = %d", testAirline3.GetAirlineID()), "OR");
        filters.add(filter3);
        Filter filter4 = new Filter(format("id_airline = %d", testAirline4.GetAirlineID()), "OR");
        filters.add(filter4);
        Filter filter5 = new Filter(format("id_airline = %d", testAirline5.GetAirlineID()), "");
        filters.add(filter5);
        try {
            dataImport.DeleteAirline(testAirline1.GetAirlineID());
            dataImport.DeleteAirline(testAirline2.GetAirlineID());
            dataImport.DeleteAirline(testAirline3.GetAirlineID());
            dataImport.DeleteAirline(testAirline4.GetAirlineID());
            dataImport.DeleteAirline(testAirline5.GetAirlineID());
        } catch (Exception e) {Assert.fail(e.getMessage());}
        actualAirlines = dataExport.FetchAirlines(filters);
        assertTrue(actualAirlines.isEmpty());
        cleanUpListArl(testAirlines);
        fullClear();
    }

    /**
     * Test deleting an airline with an invalid ID in the database
     */
    @Test
    public void TestDeleteAirlineInvalidID() {
        try {
            dataImport.DeleteAirline(8008);
            Assert.fail("SQLException was supposed to be thrown.");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        fullClear();
    }

    /**
     * Test deleting an airport in the database
     */
    @Test
    public void TestDeleteOneAirport() {
        try {
            dataImport.InsertAirport(testAirport1);
            filters.add(new Filter(format("icao = '%s'", testAirport1.GetICAO()), ""));
            testAirport1.SetAirportID(dataExport.FetchAirports(filters).get(0).GetAirportID());
            filters.clear();
            Filter filter = new Filter(format("id_airport = %d", testAirport1.GetAirportID()), "");
            filters.add(filter);
            dataImport.DeleteAirport(testAirport1.GetAirportID());
        } catch (Exception e) {Assert.fail(e.getMessage());}
        actualAirports = dataExport.FetchAirports(filters);
        assertTrue(actualAirports.isEmpty());
        cleanUp(testAirport1);
        fullClear();
    }

    /**
     * Test deleting two airports in the database
     */
    @Test
    public void TestDeleteTwoAirports() {
        testAirports.add(testAirport1);
        testAirports.add(testAirport2);
        try {
            for (Airport airport: testAirports) {
                dataImport.InsertAirport(airport);
                filters.add(new Filter(format("icao = '%s'", airport.GetICAO()), ""));
                airport.SetAirportID(dataExport.FetchAirports(filters).get(0).GetAirportID());
                filters.clear();
            }
        } catch (Exception e) {Assert.fail(e.getMessage());}
        Filter filter1 = new Filter(format("id_airport = %d", testAirport1.GetAirportID()), "OR");
        filters.add(filter1);
        Filter filter2 = new Filter(format("id_airport = %d", testAirport2.GetAirportID()), "");
        filters.add(filter2);
        try {
            dataImport.DeleteAirport(testAirport1.GetAirportID());
            dataImport.DeleteAirport(testAirport2.GetAirportID());
        } catch (Exception e) {Assert.fail(e.getMessage());}
        actualAirports = dataExport.FetchAirports(filters);
        assertTrue(actualAirports.isEmpty());
        cleanUpListApt(testAirports);
        fullClear();
    }

    /**
     * Test deleting five airports in the database
     */
    @Test
    public void TestDeleteFiveAirports() {
        testAirports.add(testAirport1);
        testAirports.add(testAirport2);
        testAirports.add(testAirport3);
        testAirports.add(testAirport4);
        testAirports.add(testAirport5);
        try {
            for (Airport airport: testAirports) {
                dataImport.InsertAirport(airport);
                filters.add(new Filter(format("icao = '%s'", airport.GetICAO()), ""));
                airport.SetAirportID(dataExport.FetchAirports(filters).get(0).GetAirportID());
                filters.clear();
            }
        } catch (Exception e) {Assert.fail(e.getMessage());}
        Filter filter1 = new Filter(format("id_airport = %d", testAirport1.GetAirportID()), "OR");
        filters.add(filter1);
        Filter filter2 = new Filter(format("id_airport = %d", testAirport2.GetAirportID()), "OR");
        filters.add(filter2);
        Filter filter3 = new Filter(format("id_airport = %d", testAirport3.GetAirportID()), "OR");
        filters.add(filter3);
        Filter filter4 = new Filter(format("id_airport = %d", testAirport4.GetAirportID()), "OR");
        filters.add(filter4);
        Filter filter5 = new Filter(format("id_airport = %d", testAirport5.GetAirportID()), "");
        filters.add(filter5);
        try {
            dataImport.DeleteAirport(testAirport1.GetAirportID());
            dataImport.DeleteAirport(testAirport2.GetAirportID());
            dataImport.DeleteAirport(testAirport3.GetAirportID());
            dataImport.DeleteAirport(testAirport4.GetAirportID());
            dataImport.DeleteAirport(testAirport5.GetAirportID());
        } catch (Exception e) {Assert.fail(e.getMessage());}
        actualAirports = dataExport.FetchAirports(filters);
        assertTrue(actualAirports.isEmpty());
        cleanUpListApt(testAirports);
        fullClear();
    }

    /**
     * Test deleting an airport with an invalid ID in the database
     */
    @Test
    public void TestDeleteAirportInvalidID() {
        try {
            dataImport.DeleteAirport(9009);
            Assert.fail("SQLException was supposed to be thrown.");
            fullClear();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
    }

    /**
     * Test deleting one route in the database
     */
    @Test
    public void TestDeleteOneRoute() {
        try {
            dataImport.InsertRoute(testRoute1);} catch (Exception e) {Assert.fail(e.getMessage());}
        Filter filter = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                testRoute1.GetAirlineID(), testRoute1.GetSourceAirportID(), testRoute1.GetDestinationAirportID()), "");
        filters.add(filter);
        try {
            dataImport.DeleteRoute(testRoute1.GetAirlineID(), testRoute1.GetSourceAirportID(), testRoute1.GetDestinationAirportID());
        } catch (Exception e) {Assert.fail(e.getMessage());}
        actualRoutes = dataExport.FetchRoutes(filters);
        assertTrue(actualRoutes.isEmpty());
        cleanUp(testRoute1);
        fullClear();
    }

    /**
     * Test deleting two routes in the database
     */
    @Test
    public void TestDeleteTwoRoutes() {
        testRoutes.add(testRoute1);
        testRoutes.add(testRoute2);
        try {
            for (Route route: testRoutes) {
                dataImport.InsertRoute(route);
            }
        } catch (Exception e) {Assert.fail(e.getMessage());}
        Filter filter1 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                testRoute1.GetAirlineID(), testRoute1.GetSourceAirportID(), testRoute1.GetDestinationAirportID()), "OR");
        filters.add(filter1);
        Filter filter2 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                testRoute2.GetAirlineID(), testRoute2.GetSourceAirportID(), testRoute2.GetDestinationAirportID()), "");
        filters.add(filter2);
        try {
            dataImport.DeleteRoute(testRoute1.GetAirlineID(), testRoute1.GetSourceAirportID(), testRoute1.GetDestinationAirportID());
            dataImport.DeleteRoute(testRoute2.GetAirlineID(), testRoute2.GetSourceAirportID(), testRoute2.GetDestinationAirportID());
        } catch (Exception e) {Assert.fail(e.getMessage());}
        actualRoutes = dataExport.FetchRoutes(filters);
        assertTrue(actualRoutes.isEmpty());
        cleanUpListRte(testRoutes);
        fullClear();
    }

    /**
     * Test deleting five routes in the database
     */
    @Test
    public void TestDeleteFiveRoutes() {
        testRoutes.add(testRoute1);
        testRoutes.add(testRoute2);
        testRoutes.add(testRoute3);
        testRoutes.add(testRoute4);
        testRoutes.add(testRoute5);
        try {
            for (Route route: testRoutes) {
                dataImport.InsertRoute(route);
            }
        } catch (Exception e) {Assert.fail(e.getMessage());}
        Filter filter1 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                testRoute1.GetAirlineID(), testRoute1.GetSourceAirportID(), testRoute1.GetDestinationAirportID()), "OR");
        filters.add(filter1);
        Filter filter2 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                testRoute2.GetAirlineID(), testRoute2.GetSourceAirportID(), testRoute2.GetDestinationAirportID()), "OR");
        filters.add(filter2);
        Filter filter3 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                testRoute3.GetAirlineID(), testRoute3.GetSourceAirportID(), testRoute3.GetDestinationAirportID()), "OR");
        filters.add(filter3);
        Filter filter4 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                testRoute4.GetAirlineID(), testRoute4.GetSourceAirportID(), testRoute4.GetDestinationAirportID()), "OR");
        filters.add(filter4);
        Filter filter5 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                testRoute5.GetAirlineID(), testRoute5.GetSourceAirportID(), testRoute5.GetDestinationAirportID()), "");
        filters.add(filter5);
        try {
            dataImport.DeleteRoute(testRoute1.GetAirlineID(), testRoute1.GetSourceAirportID(), testRoute1.GetDestinationAirportID());
            dataImport.DeleteRoute(testRoute2.GetAirlineID(), testRoute2.GetSourceAirportID(), testRoute2.GetDestinationAirportID());
            dataImport.DeleteRoute(testRoute3.GetAirlineID(), testRoute3.GetSourceAirportID(), testRoute3.GetDestinationAirportID());
            dataImport.DeleteRoute(testRoute4.GetAirlineID(), testRoute4.GetSourceAirportID(), testRoute4.GetDestinationAirportID());
            dataImport.DeleteRoute(testRoute5.GetAirlineID(), testRoute5.GetSourceAirportID(), testRoute5.GetDestinationAirportID());
        } catch (Exception e) {Assert.fail(e.getMessage());}
        actualRoutes = dataExport.FetchRoutes(filters);
        assertTrue(actualRoutes.isEmpty());
        cleanUpListRte(testRoutes);
        fullClear();
    }

    /**
     * Test deleting a route with an invalid ID in the database
     */
    @Test
    public void TestDeleteRouteInvalidAirlineID() {
        try {
            dataImport.InsertRoute(testRoute1);} catch (Exception e) {Assert.fail(e.getMessage());}
        Filter filter = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                testRoute1.GetAirlineID(), testRoute1.GetSourceAirportID(), testRoute1.GetDestinationAirportID()), "");
        filters.add(filter);
        try {
            dataImport.DeleteRoute(8008, testRoute1.GetSourceAirportID(), testRoute1.GetDestinationAirportID());
            Assert.fail("SQLException was supposed to be thrown.");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        cleanUp(testRoute1);
        fullClear();
    }

    /**
     * Test deleting a route with an invalid source airport ID in the database
     */
    @Test
    public void TestDeleteRouteInvalidSourceID() {
        try {
            dataImport.InsertRoute(testRoute1);} catch (Exception e) {Assert.fail(e.getMessage());}
        Filter filter = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                testRoute1.GetAirlineID(), testRoute1.GetSourceAirportID(), testRoute1.GetDestinationAirportID()), "");
        filters.add(filter);
        try {
            dataImport.DeleteRoute(testRoute1.GetAirlineID(), 8008, testRoute1.GetDestinationAirportID());
            Assert.fail("SQLException was supposed to be thrown.");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        cleanUp(testRoute1);
        fullClear();
    }

    /**
     * Test deleting a route with an invalid destination airport ID in the database
     */
    @Test
    public void TestDeleteRouteInvalidDestinationID() {
        try {
            dataImport.InsertRoute(testRoute1);} catch (Exception e) {Assert.fail(e.getMessage());}
        Filter filter = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                testRoute1.GetAirlineID(), testRoute1.GetSourceAirportID(), testRoute1.GetDestinationAirportID()), "");
        filters.add(filter);
        try {
            dataImport.DeleteRoute(testRoute1.GetAirlineID(), testRoute1.GetSourceAirportID(), 8008);
            Assert.fail("SQLException was supposed to be thrown.");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        cleanUp(testRoute1);
        fullClear();
    }
}
