package seng202.team6.model.data;

import javafx.util.Pair;
import org.junit.*;
import seng202.team6.model.entities.*;
import seng202.team6.model.user.HolidayPlan;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Collections;
import java.util.Random;
import java.util.ArrayList;

import static java.lang.String.format;
import static java.lang.String.join;
import static org.junit.Assert.*;

public class DatabaseHandlerTest {
    private Random random;
    private int randomBound = 10000000;

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
                dataImport.DeleteAirline(((Airline) object).getAirlineID());
            } else if (object instanceof Airport) {
                dataImport.DeleteAirport(((Airport) object).getAirportID());
            } else if (object instanceof Route) {
                Route route = (Route) object;
                dataImport.DeleteRoute(route.getAirlineID(), route.getSourceAirportID(), route.getDestinationAirportID());
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
            random = new Random();
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

            testAirline1 = new Airline(random.nextInt(randomBound), "Virgin Airlines", "Virgin", "VI",
                    "VIR", "VIRGIN", "Australia", 'Y');
            testAirline2 = new Airline(random.nextInt(randomBound), "Singapore Airlines", "Singapore", "SN",
                    "SNG", "SINGAPORE", "Signapore", 'Y');
            testAirline3 = new Airline(random.nextInt(randomBound), "Qatar Airways", "Qatar", "QA",
                    "QAT", "QATAR", "Qatar", 'Y');
            testAirline4 = new Airline(random.nextInt(randomBound), "Emirates", "Emirates", "EM",
                    "EMI", "EMIRATES", "United Arab Emirates", 'Y');
            testAirline5 = new Airline(random.nextInt(randomBound), "Lufthansa", "Luft", "LF",
                    "LFT", "LUFTHANSA", "Germany", 'Y');
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

            testRoute1 = new Route(testAirline1.getAirlineID(), testAirline1.getName(), testAirport1.getName(), testAirport1.getAirportID(),
                    testAirport2.getName(), testAirport2.getAirportID(), ' ', 0, "CR2");
            testRoute2 = new Route(testAirline2.getAirlineID(), testAirline2.getName(), testAirport3.getName(), testAirport3.getAirportID(),
                    testAirport4.getName(), testAirport4.getAirportID(), ' ', 0, "CR2");
            testRoute3 = new Route(testAirline3.getAirlineID(), testAirline3.getName(), testAirport5.getName(), testAirport5.getAirportID(),
                    testAirport1.getName(), testAirport1.getAirportID(), ' ', 0, "A81");
            testRoute4 = new Route(testAirline4.getAirlineID(), testAirline4.getName(), testAirport2.getName(), testAirport2.getAirportID(),
                    testAirport3.getName(), testAirport4.getAirportID(), ' ', 0, "AN4");
            testRoute5 = new Route(testAirline5.getAirlineID(), testAirline5.getName(), testAirport4.getName(), testAirport4.getAirportID(),
                    testAirport5.getName(), testAirport5.getAirportID(), ' ', 0, "142");
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

    // For Flight Paths
    private RoutePath testRoutePath1;
    private RoutePath testRoutePath2;
    private RoutePath testRoutePath3;
    private RoutePath testRoutePath4;
    private RoutePath testRoutePath5;
    private ArrayList<RoutePath> actualRoutePaths;

    @Before
    public void InitialiseFlightPathTest() {
        ArrayList<Pair<Double, Double>> coordinates1 = new ArrayList<Pair<Double, Double>>();
        coordinates1.add(new Pair <Double,Double> (1.11111, 1.22222));
        testRoutePath1 = new RoutePath("LOND", "LOSX", coordinates1);
        ArrayList<Pair<Double, Double>> coordinates2 = new ArrayList<Pair<Double, Double>>();
        coordinates2.add(new Pair <Double,Double> (2.33333, 2.44444));
        testRoutePath1 = new RoutePath("LOSX", "HNDA", coordinates1);
        ArrayList<Pair<Double, Double>> coordinates3 = new ArrayList<Pair<Double, Double>>();
        coordinates3.add(new Pair <Double,Double> (3.55555, 3.66666));
        testRoutePath1 = new RoutePath("HNDA", "AMSD", coordinates1);
        ArrayList<Pair<Double, Double>> coordinates4 = new ArrayList<Pair<Double, Double>>();
        coordinates4.add(new Pair <Double,Double> (4.77777, 4.88888));
        testRoutePath1 = new RoutePath("AMSD", "HGKG", coordinates1);
        ArrayList<Pair<Double, Double>> coordinates5 = new ArrayList<Pair<Double, Double>>();
        coordinates5.add(new Pair <Double,Double> (5.99999, 5.00009));
        testRoutePath1 = new RoutePath("HGKG", "LOND", coordinates1);
        actualRoutePaths = new ArrayList<RoutePath>();

        /*
        try {
            dataImport.InsertAirport(testAirport1);
            dataImport.InsertAirport(testAirport2);
            dataImport.InsertAirport(testAirport3);
            dataImport.InsertAirport(testAirport4);
            dataImport.InsertAirport(testAirport5);
        } catch (Exception e) { System.out.println(e.getMessage()); }
         */
    }

    // For Holiday Plans
    private HolidayPlan testHolidayPlan1;
    private HolidayPlan testHolidayPlan2;
    private HolidayPlan testHolidayPlan3;
    private HolidayPlan testHolidayPlan4;
    private HolidayPlan testHolidayPlan5;
    private ArrayList<HolidayPlan> actualHolidayPlans;

    /**
     * Test inserting one airline into the database
     */
    @Test
    public void testInsertOneAirline() {
        try {
            dataImport.InsertAirline(testAirline1);
            Filter filter = new Filter(format("id_airline = %d", testAirline1.getAirlineID()), "");
            filters.add(filter);
            actualAirlines = dataExport.FetchAirlines(filters);
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
            Filter filter1 = new Filter(format("id_airline = %d", testAirline1.getAirlineID()), "OR");
            filters.add(filter1);
            Filter filter2 = new Filter(format("id_airline = %d", testAirline2.getAirlineID()), "");
            filters.add(filter2);
            actualAirlines = dataExport.FetchAirlines(filters);
        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
        Collections.sort(testAirlines);
        Collections.sort(actualAirlines);
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
            Filter filter1 = new Filter(format("id_airline = %d", testAirline1.getAirlineID()), "OR");
            filters.add(filter1);
            Filter filter2 = new Filter(format("id_airline = %d", testAirline2.getAirlineID()), "OR");
            filters.add(filter2);
            Filter filter3 = new Filter(format("id_airline = %d", testAirline3.getAirlineID()), "OR");
            filters.add(filter3);
            Filter filter4 = new Filter(format("id_airline = %d", testAirline4.getAirlineID()), "OR");
            filters.add(filter4);
            Filter filter5 = new Filter(format("id_airline = %d", testAirline5.getAirlineID()), "");
            filters.add(filter5);
            actualAirlines = dataExport.FetchAirlines(filters);
        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
        Collections.sort(testAirlines);
        Collections.sort(actualAirlines);
        for (int i = 0; i < 5; i++) {
            Assert.assertEquals(testAirlines.get(i), actualAirlines.get(i));
        }
        cleanUpListArl(testAirlines);
        fullClear();
    }

    /**
     * Test inserting an two of the same airlines into the database
     */
    @Test
    public void TestInsertTwoSameAirlines() {
        try {
            dataImport.InsertAirline(testAirline1);
            dataImport.InsertAirline(testAirline1);
            Assert.fail("SQLException was supposed to be thrown.");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        cleanUp(testAirline1);
        fullClear();
    }

    /**
     * Test inserting one airport into the database
     */
    @Test
    public void TestInsertOneAirport() {
        try {
            dataImport.InsertAirport(testAirport1);
            Filter filter = new Filter(format("icao = '%s'", testAirport1.getICAO()), "");
            filters.add(filter);
            actualAirports = dataExport.FetchAirports(filters);
            testAirport1.SetAirportID(actualAirports.get(0).getAirportID());
            Assert.assertEquals(testAirport1, actualAirports.get(0));
        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
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
            Filter filter1 = new Filter(format("icao = '%s'", testAirport1.getICAO()), "OR");
            filters.add(filter1);
            Filter filter2 = new Filter(format("icao = '%s'", testAirport3.getICAO()), "");
            filters.add(filter2);
            actualAirports = dataExport.FetchAirports(filters);
        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
        testAirports.get(0).SetAirportID(actualAirports.get(0).getAirportID());
        testAirports.get(1).SetAirportID(actualAirports.get(1).getAirportID());

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
            Filter filter1 = new Filter(format("icao = '%s'", testAirport1.getICAO()), "OR");
            filters.add(filter1);
            Filter filter2 = new Filter(format("icao = '%s'", testAirport2.getICAO()), "OR");
            filters.add(filter2);
            Filter filter3 = new Filter(format("icao = '%s'", testAirport3.getICAO()), "OR");
            filters.add(filter3);
            Filter filter4 = new Filter(format("icao = '%s'", testAirport4.getICAO()), "OR");
            filters.add(filter4);
            Filter filter5 = new Filter(format("icao = '%s'", testAirport5.getICAO()), "");
            filters.add(filter5);
            actualAirports = dataExport.FetchAirports(filters);
        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
        testAirports.get(0).SetAirportID(actualAirports.get(0).getAirportID());
        testAirports.get(1).SetAirportID(actualAirports.get(1).getAirportID());
        testAirports.get(2).SetAirportID(actualAirports.get(2).getAirportID());
        testAirports.get(3).SetAirportID(actualAirports.get(3).getAirportID());
        testAirports.get(4).SetAirportID(actualAirports.get(4).getAirportID());

        for (int i = 0; i < 5; i++) {
            Assert.assertEquals(testAirports.get(i), actualAirports.get(i));
        }
        cleanUpListApt(testAirports);
        fullClear();
    }

    /**
     * Test inserting an two of the same airports into the database
     */
    @Test @Ignore
    public void TestInsertTwoSameAirports() {
        try {
            dataImport.InsertAirport(testAirport1);
            dataImport.InsertAirport(testAirport1);
            Assert.fail("SQLException was supposed to be thrown.");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        cleanUp(testAirport1);
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
                            " = %d", testRoute1.getAirlineID(), testRoute1.getSourceAirportID(),
                    testRoute1.getDestinationAirportID()), "");
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
                            " = %d", testRoute1.getAirlineID(), testRoute1.getSourceAirportID(),
                    testRoute1.getDestinationAirportID()), "OR");
            filters.add(filter1);
            Filter filter2 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id" +
                            " = %d", testRoute2.getAirlineID(), testRoute2.getSourceAirportID(),
                    testRoute2.getDestinationAirportID()), "");
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
                            " = %d", testRoute1.getAirlineID(), testRoute1.getSourceAirportID(),
                    testRoute1.getDestinationAirportID()), "OR");
            filters.add(filter1);
            Filter filter2 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id" +
                            " = %d", testRoute2.getAirlineID(), testRoute2.getSourceAirportID(),
                    testRoute2.getDestinationAirportID()), "OR");
            filters.add(filter2);
            Filter filter3 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id" +
                            " = %d", testRoute3.getAirlineID(), testRoute3.getSourceAirportID(),
                    testRoute3.getDestinationAirportID()), "OR");
            filters.add(filter3);
            Filter filter4 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id" +
                            " = %d", testRoute4.getAirlineID(), testRoute4.getSourceAirportID(),
                    testRoute4.getDestinationAirportID()), "OR");
            filters.add(filter4);
            Filter filter5 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id" +
                            " = %d", testRoute5.getAirlineID(), testRoute5.getSourceAirportID(),
                    testRoute5.getDestinationAirportID()), "");
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
            dataImport.InsertAirline(testAirline1);} catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.UpdateAirline(testAirline1.getAirlineID(), "VirginBlue", null, null, null, null,
                    null, null);
            Filter filter = new Filter(format("id_airline = %d", testAirline1.getAirlineID()), "");
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
            }
        } catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.UpdateAirline(testAirline1.getAirlineID(), "VirginGreen", null, null, null, null,
                    null, null);
            Filter filter1 = new Filter(format("id_airline = %d", testAirline1.getAirlineID()), "OR");
            filters.add(filter1);
            dataImport.UpdateAirline(testAirline2.getAirlineID(), "VirginPurple", null, null, null, null,
                    null, null);
            Filter filter2 = new Filter(format("id_airline = %d", testAirline2.getAirlineID()), "");
            filters.add(filter2);
            actualAirlines = dataExport.FetchAirlines(filters);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        testAirlines.get(0).SetName("VirginGreen");
        testAirlines.get(1).SetName("VirginPurple");
        Collections.sort(testAirlines);
        Collections.sort(actualAirlines);
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
            }
        } catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.UpdateAirline(testAirline1.getAirlineID(), "VirginAlpha", null, null, null, null,
                    null, null);
            Filter filter1 = new Filter(format("id_airline = %d", testAirline1.getAirlineID()), "OR");
            filters.add(filter1);
            dataImport.UpdateAirline(testAirline2.getAirlineID(), "VirginBeta", null, null, null, null,
                    null, null);
            Filter filter2 = new Filter(format("id_airline = %d", testAirline2.getAirlineID()), "OR");
            filters.add(filter2);
            dataImport.UpdateAirline(testAirline3.getAirlineID(), "VirginCharlie", null, null, null, null,
                    null, null);
            Filter filter3 = new Filter(format("id_airline = %d", testAirline3.getAirlineID()), "OR");
            filters.add(filter3);
            dataImport.UpdateAirline(testAirline4.getAirlineID(), "VirginDelta", null, null, null, null,
                    null, null);
            Filter filter4 = new Filter(format("id_airline = %d", testAirline4.getAirlineID()), "OR");
            filters.add(filter4);
            dataImport.UpdateAirline(testAirline5.getAirlineID(), "VirginEcho", null, null, null, null,
                    null, null);
            Filter filter5 = new Filter(format("id_airline = %d", testAirline5.getAirlineID()), "");
            filters.add(filter5);
            actualAirlines = dataExport.FetchAirlines(filters);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        testAirlines.get(0).SetName("VirginAlpha");
        testAirlines.get(1).SetName("VirginBeta");
        testAirlines.get(2).SetName("VirginCharlie");
        testAirlines.get(3).SetName("VirginDelta");
        testAirlines.get(4).SetName("VirginEcho");
        Collections.sort(testAirlines);
        Collections.sort(actualAirlines);
        for (int i = 0; i < 5; i++) {
            assertEquals(testAirlines.get(i), actualAirlines.get(i));
        }
        cleanUpListArl(testAirlines);
        fullClear();
    }

    /**
     * Test updating an airline with empty parameters (should throw exception) within the database
     */
    @Test @Ignore
    public void TestUpdateAirlineEmpty() {
        try {
            dataImport.InsertAirline(testAirline1);} catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.UpdateAirline(testAirline1.getAirlineID(), null, null, null, null, null,
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
            dataImport.InsertAirline(testAirline1);} catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.UpdateAirline(testAirline1.getAirlineID(), null, null, "W", null, null,
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
            dataImport.InsertAirline(testAirline1);} catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.UpdateAirline(testAirline1.getAirlineID(), null, null, "WAP", null, null,
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
            dataImport.InsertAirline(testAirline1);} catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.UpdateAirline(testAirline1.getAirlineID(), null, null, null, "WA", null,
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
            dataImport.InsertAirline(testAirline1);} catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.UpdateAirline(testAirline1.getAirlineID(), null, null, null, "WAPP", null,
                    null, null);
            Assert.fail("Should have failed by now.");
        } catch (Exception e) {
            String message = "The provided ICAO was not three characters long!";
            assertEquals(message, e.getMessage());
        }
        cleanUp(testAirline1);
        fullClear();
    }

    //TODO For some reason some airport tests are failing, find out why!
    /**
     * Test updating one airport within the database
     */
    //TODO URGENT!!! Fix airport update and delete tests with auto-increment
    @Test @Ignore
    public void TestUpdateOneAirport() {
        try {
            dataImport.InsertAirport(testAirport1);} catch (Exception e) {Assert.fail(e.getMessage());}
            //testAirport1.SetAirportID(actualAirports.get(0).getAirportID());
        try {
            dataImport.UpdateAirport(testAirport1.getAirportID(), "Alpha", null, null, null, null,
                    null, null, null, null, null);
            Filter filter = new Filter(format("icao = '%s'", testAirport1.getICAO()), "");
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
    @Test @Ignore
    public void TestUpdateTwoAirports() {
        testAirports.add(testAirport1);
        testAirports.add(testAirport2);
        try {
            for (Airport airport: testAirports) {
                dataImport.InsertAirport(airport);
            }
        } catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.UpdateAirport(testAirport1.getAirportID(), "Bravo", null, null, null, null,
                    null, null, null, null, null);
            Filter filter1 = new Filter(format("id_airport = %d", testAirport1.getAirportID()), "OR");
            filters.add(filter1);
            dataImport.UpdateAirport(testAirport2.getAirportID(), "Charlie", null, null, null, null,
                    null, null, null, null, null);
            Filter filter2 = new Filter(format("id_airport = %d", testAirport2.getAirportID()), "");
            filters.add(filter2);
            actualAirports = dataExport.FetchAirports(filters);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        testAirports.get(0).SetName("Bravo");
        testAirports.get(1).SetName("Charlie");
        Collections.sort(testAirports);
        Collections.sort(actualAirports);
        for (int i = 0; i < 2; i++) {
            assertEquals(testAirports.get(i), actualAirports.get(i));
        }
        cleanUpListApt(testAirports);
        fullClear();
    }

    /**
     * Test updating five airports within the database
     */
    @Test @Ignore
    public void TestUpdateFiveAirports() {
        testAirports.add(testAirport1);
        testAirports.add(testAirport2);
        testAirports.add(testAirport3);
        testAirports.add(testAirport4);
        testAirports.add(testAirport5);
        try {
            for (Airport airport: testAirports) {
                dataImport.InsertAirport(airport);
            }
        } catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.UpdateAirport(testAirport1.getAirportID(), "Delta", null, null, null, null,
                    null, null, null, null, null);
            Filter filter1 = new Filter(format("id_airport = %d", testAirport1.getAirportID()), "OR");
            filters.add(filter1);
            dataImport.UpdateAirport(testAirport2.getAirportID(), "Echo", null, null, null, null,
                    null, null, null, null, null);
            Filter filter2 = new Filter(format("id_airport = %d", testAirport2.getAirportID()), "OR");
            filters.add(filter2);
            dataImport.UpdateAirport(testAirport3.getAirportID(), "Foxtrot", null, null, null, null,
                    null, null, null, null, null);
            Filter filter3 = new Filter(format("id_airport = %d", testAirport3.getAirportID()), "OR");
            filters.add(filter3);
            dataImport.UpdateAirport(testAirport4.getAirportID(), "Golf", null, null, null, null,
                    null, null, null, null, null);
            Filter filter4 = new Filter(format("id_airport = %d", testAirport4.getAirportID()), "OR");
            filters.add(filter4);
            dataImport.UpdateAirport(testAirport5.getAirportID(), "Hotel", null, null, null, null,
                    null, null, null, null, null);
            Filter filter5 = new Filter(format("id_airport = %d", testAirport5.getAirportID()), "");
            filters.add(filter5);
            actualAirports = dataExport.FetchAirports(filters);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        testAirports.get(0).SetName("Delta");
        testAirports.get(1).SetName("Echo");
        testAirports.get(2).SetName("Foxtrot");
        testAirports.get(3).SetName("Golf");
        testAirports.get(4).SetName("Hotel");
        Collections.sort(testAirports);
        Collections.sort(actualAirports);
        for (int i = 0; i < 5; i++) {
            assertEquals(testAirports.get(i), actualAirports.get(i));
        }
        cleanUpListApt(testAirports);
        fullClear();
    }

    /**
     * Test updating an airport with empty parameters within the database
     */
    @Test @Ignore
    public void TestUpdateAirportEmpty() {
        try {
            dataImport.InsertAirport(testAirport1);} catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.UpdateAirport(testAirport1.getAirportID(), null, null, null, null, null,
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
    @Test @Ignore
    public void TestUpdateAirportTwoCharIATA() {
        try {
            dataImport.InsertAirport(testAirport1);} catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.UpdateAirport(testAirport1.getAirportID(), null, null, null, "BO", null,
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
    @Test @Ignore
    public void TestUpdateAirportFourCharIATA() {
        try {
            dataImport.InsertAirport(testAirport1);} catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.UpdateAirport(testAirport1.getAirportID(), null, null, null, "BOII", null,
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
    @Test @Ignore
    public void TestUpdateAirportThreeCharICAO() {
        try {
            dataImport.InsertAirport(testAirport1);} catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.UpdateAirport(testAirport1.getAirportID(), null, null, null, null, "SUG",
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
    @Test @Ignore
    public void TestUpdateAirportFiveCharICAO() {
        try {
            dataImport.InsertAirport(testAirport1);} catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.UpdateAirport(testAirport1.getAirportID(), null, null, null, null, "SUGAR",
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
            dataImport.UpdateRoute(testRoute1.getAirlineID(), testRoute1.getSourceAirportID(), testRoute1.getDestinationAirportID(),
                    null, null, null, null, 5, null);
            Filter filter = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                    testRoute1.getAirlineID(), testRoute1.getSourceAirportID(), testRoute1.getDestinationAirportID()), "");
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
            dataImport.UpdateRoute(testRoute1.getAirlineID(), testRoute1.getSourceAirportID(), testRoute1.getDestinationAirportID(),
                    null, null, null, null, 6, null);
            Filter filter1 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                    testRoute1.getAirlineID(), testRoute1.getSourceAirportID(), testRoute1.getDestinationAirportID()), "OR");
            filters.add(filter1);
            dataImport.UpdateRoute(testRoute2.getAirlineID(), testRoute2.getSourceAirportID(), testRoute2.getDestinationAirportID(),
                    null, null, null, null, 7, null);
            Filter filter2 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                    testRoute2.getAirlineID(), testRoute2.getSourceAirportID(), testRoute2.getDestinationAirportID()), "");
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
            dataImport.UpdateRoute(testRoute1.getAirlineID(), testRoute1.getSourceAirportID(), testRoute1.getDestinationAirportID(),
                    null, null, null, null, 8, null);
            Filter filter1 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                    testRoute1.getAirlineID(), testRoute1.getSourceAirportID(), testRoute1.getDestinationAirportID()), "OR");
            filters.add(filter1);
            dataImport.UpdateRoute(testRoute2.getAirlineID(), testRoute2.getSourceAirportID(), testRoute2.getDestinationAirportID(),
                    null, null, null, null, 9, null);
            Filter filter2 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                    testRoute2.getAirlineID(), testRoute2.getSourceAirportID(), testRoute2.getDestinationAirportID()), "OR");
            filters.add(filter2);
            dataImport.UpdateRoute(testRoute3.getAirlineID(), testRoute3.getSourceAirportID(), testRoute3.getDestinationAirportID(),
                    null, null, null, null, 10, null);
            Filter filter3 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                    testRoute3.getAirlineID(), testRoute3.getSourceAirportID(), testRoute3.getDestinationAirportID()), "OR");
            filters.add(filter3);
            dataImport.UpdateRoute(testRoute4.getAirlineID(), testRoute4.getSourceAirportID(), testRoute4.getDestinationAirportID(),
                    null, null, null, null, 11, null);
            Filter filter4 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                    testRoute4.getAirlineID(), testRoute4.getSourceAirportID(), testRoute4.getDestinationAirportID()), "OR");
            filters.add(filter4);
            dataImport.UpdateRoute(testRoute5.getAirlineID(), testRoute5.getSourceAirportID(), testRoute5.getDestinationAirportID(),
                    null, null, null, null, 12, null);
            Filter filter5 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                    testRoute5.getAirlineID(), testRoute5.getSourceAirportID(), testRoute5.getDestinationAirportID()), "");
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
            dataImport.UpdateRoute(testRoute1.getAirlineID(), testRoute1.getSourceAirportID(), testRoute1.getDestinationAirportID(),
                    null, null, null, null, null, null);
            Assert.fail("Should have failed by now.");
        } catch (Exception e) {
            String message = "No parameters to update were provided!";
            assertEquals(message, e.getMessage());
        }
        cleanUp(testRoute1);
        fullClear();
    }
    //TODO Should add more tests for UpdateRoute, as newAirline and NewAirports are not tested

    /**
     * Test deleting an airline in the database
     */
    @Test
    public void TestDeleteOneAirline() {
        try {
            dataImport.InsertAirline(testAirline1);} catch (Exception e) {Assert.fail(e.getMessage());}
        Filter filter = new Filter(format("id_airline = %d", testAirline1.getAirlineID()), "");
        filters.add(filter);
        try {
            dataImport.DeleteAirline(testAirline1.getAirlineID());
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
            }
        } catch (Exception e) {Assert.fail(e.getMessage());}
        Filter filter1 = new Filter(format("id_airline = %d", testAirline1.getAirlineID()), "OR");
        filters.add(filter1);
        Filter filter2 = new Filter(format("id_airline = %d", testAirline2.getAirlineID()), "");
        filters.add(filter2);
        try {
            dataImport.DeleteAirline(testAirline1.getAirlineID());
            dataImport.DeleteAirline(testAirline2.getAirlineID());
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
            }
        } catch (Exception e) {Assert.fail(e.getMessage());}
        Filter filter1 = new Filter(format("id_airline = %d", testAirline1.getAirlineID()), "OR");
        filters.add(filter1);
        Filter filter2 = new Filter(format("id_airline = %d", testAirline2.getAirlineID()), "OR");
        filters.add(filter2);
        Filter filter3 = new Filter(format("id_airline = %d", testAirline3.getAirlineID()), "OR");
        filters.add(filter3);
        Filter filter4 = new Filter(format("id_airline = %d", testAirline4.getAirlineID()), "OR");
        filters.add(filter4);
        Filter filter5 = new Filter(format("id_airline = %d", testAirline5.getAirlineID()), "");
        filters.add(filter5);
        try {
            dataImport.DeleteAirline(testAirline1.getAirlineID());
            dataImport.DeleteAirline(testAirline2.getAirlineID());
            dataImport.DeleteAirline(testAirline3.getAirlineID());
            dataImport.DeleteAirline(testAirline4.getAirlineID());
            dataImport.DeleteAirline(testAirline5.getAirlineID());
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
    @Test @Ignore
    public void TestDeleteOneAirport() {
        try {
            dataImport.InsertAirport(testAirport1);} catch (Exception e) {Assert.fail(e.getMessage());}
        Filter filter = new Filter(format("id_airport = %d", testAirport1.getAirportID()), "");
        filters.add(filter);
        try {
            dataImport.DeleteAirport(testAirport1.getAirportID());
        } catch (Exception e) {Assert.fail(e.getMessage());}
        actualAirports = dataExport.FetchAirports(filters);
        assertTrue(actualAirports.isEmpty());
        cleanUp(testAirport1);
        fullClear();
    }

    /**
     * Test deleting two airports in the database
     */
    @Test @Ignore
    public void TestDeleteTwoAirports() {
        testAirports.add(testAirport1);
        testAirports.add(testAirport2);
        try {
            for (Airport airport: testAirports) {
                dataImport.InsertAirport(airport);
            }
        } catch (Exception e) {Assert.fail(e.getMessage());}
        Filter filter1 = new Filter(format("id_airport = %d", testAirport1.getAirportID()), "OR");
        filters.add(filter1);
        Filter filter2 = new Filter(format("id_airport = %d", testAirport2.getAirportID()), "");
        filters.add(filter2);
        try {
            dataImport.DeleteAirport(testAirport1.getAirportID());
            dataImport.DeleteAirport(testAirport2.getAirportID());
        } catch (Exception e) {Assert.fail(e.getMessage());}
        actualAirports = dataExport.FetchAirports(filters);
        assertTrue(actualAirports.isEmpty());
        cleanUpListApt(testAirports);
        fullClear();
    }

    /**
     * Test deleting five airports in the database
     */
    @Test @Ignore
    public void TestDeleteFiveAirports() {
        testAirports.add(testAirport1);
        testAirports.add(testAirport2);
        testAirports.add(testAirport3);
        testAirports.add(testAirport4);
        testAirports.add(testAirport5);
        try {
            for (Airport airport: testAirports) {
                dataImport.InsertAirport(airport);
            }
        } catch (Exception e) {Assert.fail(e.getMessage());}
        Filter filter1 = new Filter(format("id_airport = %d", testAirport1.getAirportID()), "OR");
        filters.add(filter1);
        Filter filter2 = new Filter(format("id_airport = %d", testAirport2.getAirportID()), "OR");
        filters.add(filter2);
        Filter filter3 = new Filter(format("id_airport = %d", testAirport3.getAirportID()), "OR");
        filters.add(filter3);
        Filter filter4 = new Filter(format("id_airport = %d", testAirport4.getAirportID()), "OR");
        filters.add(filter4);
        Filter filter5 = new Filter(format("id_airport = %d", testAirport5.getAirportID()), "");
        filters.add(filter5);
        try {
            dataImport.DeleteAirport(testAirport1.getAirportID());
            dataImport.DeleteAirport(testAirport2.getAirportID());
            dataImport.DeleteAirport(testAirport3.getAirportID());
            dataImport.DeleteAirport(testAirport4.getAirportID());
            dataImport.DeleteAirport(testAirport5.getAirportID());
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
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        fullClear();
    }

    /**
     * Test deleting one route in the database
     */
    @Test
    public void TestDeleteOneRoute() {
        try {
            dataImport.InsertRoute(testRoute1);} catch (Exception e) {Assert.fail(e.getMessage());}
        Filter filter = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                testRoute1.getAirlineID(), testRoute1.getSourceAirportID(), testRoute1.getDestinationAirportID()), "");
        filters.add(filter);
        try {
            dataImport.DeleteRoute(testRoute1.getAirlineID(), testRoute1.getSourceAirportID(), testRoute1.getDestinationAirportID());
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
                testRoute1.getAirlineID(), testRoute1.getSourceAirportID(), testRoute1.getDestinationAirportID()), "OR");
        filters.add(filter1);
        Filter filter2 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                testRoute2.getAirlineID(), testRoute2.getSourceAirportID(), testRoute2.getDestinationAirportID()), "OR");
        filters.add(filter2);
        try {
            dataImport.DeleteRoute(testRoute1.getAirlineID(), testRoute1.getSourceAirportID(), testRoute1.getDestinationAirportID());
            dataImport.DeleteRoute(testRoute2.getAirlineID(), testRoute2.getSourceAirportID(), testRoute2.getDestinationAirportID());
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
                testRoute1.getAirlineID(), testRoute1.getSourceAirportID(), testRoute1.getDestinationAirportID()), "OR");
        filters.add(filter1);
        Filter filter2 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                testRoute2.getAirlineID(), testRoute2.getSourceAirportID(), testRoute2.getDestinationAirportID()), "OR");
        filters.add(filter2);
        Filter filter3 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                testRoute3.getAirlineID(), testRoute3.getSourceAirportID(), testRoute3.getDestinationAirportID()), "OR");
        filters.add(filter3);
        Filter filter4 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                testRoute4.getAirlineID(), testRoute4.getSourceAirportID(), testRoute4.getDestinationAirportID()), "OR");
        filters.add(filter4);
        Filter filter5 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                testRoute5.getAirlineID(), testRoute5.getSourceAirportID(), testRoute5.getDestinationAirportID()), "");
        filters.add(filter5);
        try {
            dataImport.DeleteRoute(testRoute1.getAirlineID(), testRoute1.getSourceAirportID(), testRoute1.getDestinationAirportID());
            dataImport.DeleteRoute(testRoute2.getAirlineID(), testRoute2.getSourceAirportID(), testRoute2.getDestinationAirportID());
            dataImport.DeleteRoute(testRoute3.getAirlineID(), testRoute3.getSourceAirportID(), testRoute3.getDestinationAirportID());
            dataImport.DeleteRoute(testRoute4.getAirlineID(), testRoute4.getSourceAirportID(), testRoute4.getDestinationAirportID());
            dataImport.DeleteRoute(testRoute5.getAirlineID(), testRoute5.getSourceAirportID(), testRoute5.getDestinationAirportID());
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
                testRoute1.getAirlineID(), testRoute1.getSourceAirportID(), testRoute1.getDestinationAirportID()), "");
        filters.add(filter);
        try {
            dataImport.DeleteRoute(8008, testRoute1.getSourceAirportID(), testRoute1.getDestinationAirportID());
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
                testRoute1.getAirlineID(), testRoute1.getSourceAirportID(), testRoute1.getDestinationAirportID()), "");
        filters.add(filter);
        try {
            dataImport.DeleteRoute(testRoute1.getAirlineID(), 8008, testRoute1.getDestinationAirportID());
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
                testRoute1.getAirlineID(), testRoute1.getSourceAirportID(), testRoute1.getDestinationAirportID()), "");
        filters.add(filter);
        try {
            dataImport.DeleteRoute(testRoute1.getAirlineID(), testRoute1.getSourceAirportID(), 8008);
            Assert.fail("SQLException was supposed to be thrown.");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        cleanUp(testRoute1);
        fullClear();
    }


    /**
     * Test inserting a flight path into the database
     */
    @Test @Ignore
    public void TestInsertOneFlightPath() {
        try {
            dataImport.InsertFlightPath(testRoutePath1, "/RoutePath/Objects/");
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test inserting a two flight paths into the database
     */
    @Test @Ignore
    public void TestInsertTwoFlightPaths() {

    }

    /**
     * Test inserting a five flight paths into the database
     */
    @Test @Ignore
    public void TestInsertFiveFlightPaths() {

    }

    /**
     * Test inserting two of the same flights, should error
     */
    @Test @Ignore
    public void TestInsertTwoSameFlightPaths() {

    }

    /**
     * Test inserting a flight with no directory, should error
     */
    @Test @Ignore
    public void TestInsertFlightPathWithNoDirectory() {

    }

    /**
     * Test updating one flight path
     */
    @Test @Ignore
    public void TestUpdateOneFlightPath() {

    }

    /**
     * Test updating two flight paths
     */
    @Test @Ignore
    public void TestUpdateTwoFlightPaths() {

    }

    /**
     * Test updating five flight paths
     */
    @Test @Ignore
    public void TestUpdateFiveFlightPaths() {

    }

    /**
     * Test updating a flight path without a directory, should error
     */
    @Test @Ignore
    public void TestUpdateFlightPathWithNoDirectory() {

    }

    /**
     * Test updating a flight with an invalid source airport ID, should error
     */
    @Test @Ignore
    public void TestUpdateFlightPathWithInvalidSourceAirportID() {

    }

    /**
     * Test updating a flight with an invalid destination airport ID, should error
     */
    @Test @Ignore
    public void TestUpdateFlightPathWithInvalidDestinationAirportID() {

    }

    /**
     * Test deleting one flight path
     */
    @Test @Ignore
    public void TestDeleteOneFlightPath() {

    }

    /**
     * Test deleting two flight paths
     */
    @Test @Ignore
    public void TestDeleteTwoFlightPaths() {

    }

    /**
     * Test deleting five flight paths
     */
    @Test @Ignore
    public void TestDeleteFiveFlightPaths() {

    }

    /**
     * Test deleting a non-existant flight path, should error
     */
    @Test @Ignore
    public void TestDeleteNonExistantFlightPath() {

    }

    /**
     * Test deleting a flight path with an invalid source airport ID, should error
     */
    @Test @Ignore
    public void TestDeleteFlightPathWithInvalidSourceAirportID() {

    }

    /**
     * Test deleting a flight path with an invalid destination airport ID, should error
     */
    @Test @Ignore
    public void TestDeleteFlightPathWithInvalidDestinationAirportID() {

    }

    /**
     * Test inserting one holiday plan
     */
    @Test @Ignore
    public void TestInsertOneHolidayPlan() {

    }

    /**
     * Test inserting two holiday plans
     */
    @Test @Ignore
    public void TestInsertTwoHolidayPlans() {

    }

    /**
     * Test inserting five holiday plans
     */
    @Test @Ignore
    public void TestInsertFiveHolidayPlans() {

    }

    /**
     * Test inserting two of the same holiday plans, should error
     */
    @Test @Ignore
    public void TestInsertTwoSameHolidayPlans() {

    }

    /**
     * Testing inserting a holiday plan with not parameters provided, should error
     */
    @Test @Ignore
    public void TestInsertHolidayPlanWithNoParameters() {

    }

    /**
     * Test inserting a holiday plan without providing a dictionary, should error
     */
    @Test @Ignore
    public void TestInsertHolidayPlanWithoutDirectory() {

    }

    /**
     * Test inserting a holiday plan without providing a name, should error
     */
    @Test @Ignore
    public void TestInsertHolidayPlanWithoutName() {

    }

    /**
     * Test updating one holiday plan
     */
    @Test @Ignore
    public void TestUpdateOneHolidayPlan() {

    }

    /**
     * Test updating two holiday plans
     */
    @Test @Ignore
    public void TestUpdateTwoHolidayPlans() {

    }

    /**
     * Test updating five holiday plans
     */
    @Test @Ignore
    public void TestUpdateFiveHolidayPlans() {

    }

    /**
     * Test updating a holiday plan without providing a new directory
     */
    @Test @Ignore
    public void TestUpdateHolidayPlanWithoutNewDirectory() {

    }

    /**
     * Test updating a holiday plan without providing a new name
     */
    @Test @Ignore
    public void TestUpdateHolidayPlanWithoutNewName() {

    }

    /**
     * Test updating a holiday plan with an invalid index, should error
     */
    @Test @Ignore
    public void TestUpdateHolidayPlanWithInvalidIndex() {

    }

    /**
     * Test deleting one holiday plan
     */
    @Test @Ignore
    public void TestDeleteOneHolidayPlan() {

    }

    /**
     * Test deleting two holiday plans
     */
    @Test @Ignore
    public void TestDeleteTwoHolidayPlans() {

    }

    /**
     * Test deleting five holiday plans
     */
    @Test @Ignore
    public void TestDeleteFiveHolidayPlans() {

    }

    /**
     * Test deleting a non-existant holiday plan, should error
     */
    @Test @Ignore
    public void TestDeleteNonExistantHolidayPlan() {

    }
}
