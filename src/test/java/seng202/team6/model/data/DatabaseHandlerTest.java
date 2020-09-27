package seng202.team6.model.data;

import org.junit.*;
import seng202.team6.model.entities.*;

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

    public void fullClear() {
        filters.clear();
        testAirlines.clear();
        actualAirlines.clear();
        testAirports.clear();
        actualAirports.clear();
        testRoutes.clear();
        actualRoutes.clear();
    }

    @Before
    public void InitializeTest() {
        random = new Random();
        dataImport = DataImportHandler.GetInstance();
        dataExport = DataExportHandler.GetInstance();
        filters = new ArrayList<Filter>();

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

        testAirport1 = new Airport(random.nextInt(randomBound), "London Heathrow Airport", "London", "England",
                "LHR", "LOND", (float)51.470020, (float)-0.454295, 25, 1, 'U');
        testAirport2 = new Airport(random.nextInt(randomBound), "Los Angeles Airport", "Los Angeles", "United States of America",
                "LAX", "LOSX", (float)33.94279, (float)-118.410042, 38, -7, 'U');
        testAirport3 = new Airport(random.nextInt(randomBound), "Tokyo Haneda Airport", "Tokyo", "Japan",
                "HND", "HNDA", (float)35.5494, (float)139.7798, 11, 9, 'A');
        testAirport4 = new Airport(random.nextInt(randomBound), "Amsterdam Airport Schipol", "Amsterdam", "Netherlands",
                "AMS", "AMSD", (float)52.3105, (float)4.7683, -3, 1, 'A');
        testAirport5 = new Airport(random.nextInt(randomBound), "Hong Kong Airport", "Hong Kong", "Hong Kong",
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
        fullClear();
    }

    /**
     * Test inserting two airlines into the database
     */
    @Test
    public void testInsertTwoAirlines() {
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
        fullClear();
    }

    /**
     * Test inserting five airlines into the database
     */
    @Test
    public void testInsertFiveAirlines() {
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
        fullClear();
    }

    /**
     * Test inserting an two of the same airlines into the database
     */
    @Test
    public void testInsertTwoSameAirlines() {
        try {
            dataImport.InsertAirline(testAirline1);
            dataImport.InsertAirline(testAirline1);
            Assert.fail("SQLException was supposed to be thrown.");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        fullClear();
    }

    /**
     * Test inserting one airport into the database
     */
    @Test
    public void testInsertOneAirport() {
        try {
            dataImport.InsertAirport(testAirport1);
            Filter filter = new Filter(format("id_airport = %d", testAirport1.getAirportID()), "");
            filters.add(filter);
            actualAirports = dataExport.FetchAirports(filters);
            Assert.assertEquals(testAirport1, actualAirports.get(0));
        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
        fullClear();
    }

    /**
     * Test inserting two airports into the database
     */
    @Test
    public void testInsertTwoAirports() {
        testAirports.add(testAirport1);
        testAirports.add(testAirport2);
        try {
            for (Airport airport: testAirports) {
                dataImport.InsertAirport(airport);
            }
            Filter filter1 = new Filter(format("id_airport = %d", testAirport1.getAirportID()), "OR");
            filters.add(filter1);
            Filter filter2 = new Filter(format("id_airport = %d", testAirport2.getAirportID()), "");
            filters.add(filter2);
            actualAirports = dataExport.FetchAirports(filters);
        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
        Collections.sort(testAirports);
        Collections.sort(actualAirports);
        for (int i = 0; i < 2; i++) {
            Assert.assertEquals(testAirports.get(i), actualAirports.get(i));
        }
        fullClear();
    }

    /**
     * Test inserting five airports in the database
     */
    @Test
    public void testInsertFiveAirports() {
        testAirports.add(testAirport1);
        testAirports.add(testAirport2);
        testAirports.add(testAirport3);
        testAirports.add(testAirport4);
        testAirports.add(testAirport5);
        try {
            for (Airport airport: testAirports) {
                dataImport.InsertAirport(airport);
            }
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
            actualAirports = dataExport.FetchAirports(filters);
        } catch(Exception e) {
            Assert.fail(e.getMessage());
        }
        Collections.sort(testAirports);
        Collections.sort(actualAirports);
        for (int i = 0; i < 5; i++) {
            Assert.assertEquals(testAirports.get(i), actualAirports.get(i));
        }
        fullClear();
    }

    /**
     * Test inserting an two of the same airports into the database
     */
    @Test
    public void testInsertTwoSameAirports() {
        try {
            dataImport.InsertAirport(testAirport1);
            dataImport.InsertAirport(testAirport1);
            Assert.fail("SQLException was supposed to be thrown.");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        fullClear();
    }

    /**
     * Test inserting one route into the database
     */
    @Test
    public void testInsertOneRoute() {
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
        fullClear();
    }

    /**
     * Test inserting two routes into the database
     */
    @Test
    public void testInsertTwoRoutes() {
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
        fullClear();
    }

    /**
     * Test inserting five routes into the database
     */
    @Test
    public void testInsertFiveRoutes() {
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
        fullClear();
    }

    /**
     * Test updating one airline within the database
     */
    @Test
    public void testUpdateOneAirline() {
        try {
            dataImport.InsertAirline(testAirline1);} catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.updateAirline(testAirline1.getAirlineID(), "VirginBlue", null, null, null, null,
                    null, null);
            Filter filter = new Filter(format("id_airline = %d", testAirline1.getAirlineID()), "");
            filters.add(filter);
            actualAirlines = dataExport.FetchAirlines(filters);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        testAirline1.SetName("VirginBlue");
        assertEquals(testAirline1, actualAirlines.get(0));
        fullClear();
    }

    /**
     * Test updating two airlines within the database
     */
    @Test
    public void testUpdateTwoAirlines() {
        testAirlines.add(testAirline1);
        testAirlines.add(testAirline2);
        try {
            for (Airline airline: testAirlines) {
                dataImport.InsertAirline(airline);
            }
        } catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.updateAirline(testAirline1.getAirlineID(), "VirginGreen", null, null, null, null,
                    null, null);
            Filter filter1 = new Filter(format("id_airline = %d", testAirline1.getAirlineID()), "OR");
            filters.add(filter1);
            dataImport.updateAirline(testAirline2.getAirlineID(), "VirginPurple", null, null, null, null,
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
        fullClear();
    }

    /**
     * Test updating five airlines within the database
     */
    @Test
    public void testUpdateFiveAirlines() {
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
            dataImport.updateAirline(testAirline1.getAirlineID(), "VirginAlpha", null, null, null, null,
                    null, null);
            Filter filter1 = new Filter(format("id_airline = %d", testAirline1.getAirlineID()), "OR");
            filters.add(filter1);
            dataImport.updateAirline(testAirline2.getAirlineID(), "VirginBeta", null, null, null, null,
                    null, null);
            Filter filter2 = new Filter(format("id_airline = %d", testAirline2.getAirlineID()), "OR");
            filters.add(filter2);
            dataImport.updateAirline(testAirline3.getAirlineID(), "VirginCharlie", null, null, null, null,
                    null, null);
            Filter filter3 = new Filter(format("id_airline = %d", testAirline3.getAirlineID()), "OR");
            filters.add(filter3);
            dataImport.updateAirline(testAirline4.getAirlineID(), "VirginDelta", null, null, null, null,
                    null, null);
            Filter filter4 = new Filter(format("id_airline = %d", testAirline4.getAirlineID()), "OR");
            filters.add(filter4);
            dataImport.updateAirline(testAirline5.getAirlineID(), "VirginEcho", null, null, null, null,
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
        fullClear();
    }

    /**
     * Test updating an airline with empty parameters (should throw exception) within the database
     */
    @Test
    public void testUpdateAirlineEmpty() {
        try {
            dataImport.InsertAirline(testAirline1);} catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.updateAirline(testAirline1.getAirlineID(), null, null, null, null, null,
                    null, null);
            Assert.fail("Should have failed by now.");
        } catch (Exception e) {
            String message = "No parameters to update were provided!";
            assertEquals(message, e.getMessage());
        }
        fullClear();
    }

    /**
     * Test updating invalid IATA with one character within the database
     */
    @Test
    public void testUpdateAirlineOneCharIATA() {
        try {
            dataImport.InsertAirline(testAirline1);} catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.updateAirline(testAirline1.getAirlineID(), null, null, "W", null, null,
                    null, null);
            Assert.fail("Should have failed by now.");
        } catch (Exception e) {
            String message = "The provided IATA was not two characters long!";
            assertEquals(message, e.getMessage());
        }
        fullClear();
    }

    /**
     * Test updating invalid IATA with three characters within the database
     */
    @Test
    public void testUpdateAirlineThreeCharIATA() {
        try {
            dataImport.InsertAirline(testAirline1);} catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.updateAirline(testAirline1.getAirlineID(), null, null, "WAP", null, null,
                    null, null);
            Assert.fail("Should have failed by now.");
        } catch (Exception e) {
            String message = "The provided IATA was not two characters long!";
            assertEquals(message, e.getMessage());
        }
        fullClear();
    }

    /**
     * Test updating invalid ICAO with two characters within the database
     */
    @Test
    public void testUpdateAirlineTwoCharICAO() {
        try {
            dataImport.InsertAirline(testAirline1);} catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.updateAirline(testAirline1.getAirlineID(), null, null, null, "WA", null,
                    null, null);
            Assert.fail("Should have failed by now.");
        } catch (Exception e) {
            String message = "The provided ICAO was not three characters long!";
            assertEquals(message, e.getMessage());
        }
        fullClear();
    }

    /**
     * Test updating invalid ICAO with four characters within the database
     */
    @Test
    public void testUpdateAirlineFourCharICAO() {
        try {
            dataImport.InsertAirline(testAirline1);} catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.updateAirline(testAirline1.getAirlineID(), null, null, null, "WAPP", null,
                    null, null);
            Assert.fail("Should have failed by now.");
        } catch (Exception e) {
            String message = "The provided ICAO was not three characters long!";
            assertEquals(message, e.getMessage());
        }
        fullClear();
    }

    /**
     * Test updating one airport within the database
     */
    @Test
    public void testUpdateOneAirport() {
        try {
            dataImport.InsertAirport(testAirport1);} catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.updateAirport(testAirport1.getAirportID(), "Alpha", null, null, null, null,
                    null, null, null, null, null);
            Filter filter = new Filter(format("id_airport = %d", testAirport1.getAirportID()), "");
            filters.add(filter);
            actualAirports = dataExport.FetchAirports(filters);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        testAirport1.SetName("Alpha");
        assertEquals(testAirport1, actualAirports.get(0));
        fullClear();
    }

    /**
     * Test updating two airports within the database
     */
    @Test
    public void testUpdateTwoAirports() {
        testAirports.add(testAirport1);
        testAirports.add(testAirport2);
        try {
            for (Airport airport: testAirports) {
                dataImport.InsertAirport(airport);
            }
        } catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.updateAirport(testAirport1.getAirportID(), "Bravo", null, null, null, null,
                    null, null, null, null, null);
            Filter filter1 = new Filter(format("id_airport = %d", testAirport1.getAirportID()), "OR");
            filters.add(filter1);
            dataImport.updateAirport(testAirport2.getAirportID(), "Charlie", null, null, null, null,
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
        fullClear();
    }

    /**
     * Test updating five airports within the database
     */
    @Test
    public void testUpdateFiveAirports() {
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
            dataImport.updateAirport(testAirport1.getAirportID(), "Delta", null, null, null, null,
                    null, null, null, null, null);
            Filter filter1 = new Filter(format("id_airport = %d", testAirport1.getAirportID()), "OR");
            filters.add(filter1);
            dataImport.updateAirport(testAirport2.getAirportID(), "Echo", null, null, null, null,
                    null, null, null, null, null);
            Filter filter2 = new Filter(format("id_airport = %d", testAirport2.getAirportID()), "OR");
            filters.add(filter2);
            dataImport.updateAirport(testAirport3.getAirportID(), "Foxtrot", null, null, null, null,
                    null, null, null, null, null);
            Filter filter3 = new Filter(format("id_airport = %d", testAirport3.getAirportID()), "OR");
            filters.add(filter3);
            dataImport.updateAirport(testAirport4.getAirportID(), "Golf", null, null, null, null,
                    null, null, null, null, null);
            Filter filter4 = new Filter(format("id_airport = %d", testAirport4.getAirportID()), "OR");
            filters.add(filter4);
            dataImport.updateAirport(testAirport5.getAirportID(), "Hotel", null, null, null, null,
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
        fullClear();
    }

    /**
     * Test updating an airport with empty parameters within the database
     */
    @Test
    public void testUpdateAirportEmpty() {
        try {
            dataImport.InsertAirport(testAirport1);} catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.updateAirport(testAirport1.getAirportID(), null, null, null, null, null,
                    null, null, null, null, null);
            Assert.fail("Should have failed by now.");
        } catch (Exception e) {
            String message = "No parameters to update were provided!";
            assertEquals(message, e.getMessage());
        }
        fullClear();
    }

    /**
     * Test updating invalid IATA with two characters within the database
     */
    @Test
    public void testUpdateAirportTwoCharIATA() {
        try {
            dataImport.InsertAirport(testAirport1);} catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.updateAirport(testAirport1.getAirportID(), null, null, null, "BO", null,
                    null, null, null, null, null);
            Assert.fail("Should have failed by now.");
        } catch (Exception e) {
            String message = "The provided IATA was not three characters long!";
            assertEquals(message, e.getMessage());
        }
        fullClear();
    }

    /**
     * Test updating invalid IATA with four characters within the database
     */
    @Test
    public void testUpdateAirportFourCharIATA() {
        try {
            dataImport.InsertAirport(testAirport1);} catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.updateAirport(testAirport1.getAirportID(), null, null, null, "BOII", null,
                    null, null, null, null, null);
            Assert.fail("Should have failed by now.");
        } catch (Exception e) {
            String message = "The provided IATA was not three characters long!";
            assertEquals(message, e.getMessage());
        }
        fullClear();
    }

    /**
     * Test updating invalid ICAO with three characters within the database
     */
    @Test
    public void testUpdateAirportThreeCharICAO() {
        try {
            dataImport.InsertAirport(testAirport1);} catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.updateAirport(testAirport1.getAirportID(), null, null, null, null, "SUG",
                    null, null, null, null, null);
            Assert.fail("Should have failed by now.");
        } catch (Exception e) {
            String message = "The provided ICAO was not four characters long!";
            assertEquals(message, e.getMessage());
        }
        fullClear();
    }

    /**
     * Test updating invalid ICAO with five characters within the database
     */
    @Test
    public void testUpdateAirportFiveCharICAO() {
        try {
            dataImport.InsertAirport(testAirport1);} catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.updateAirport(testAirport1.getAirportID(), null, null, null, null, "SUGAR",
                    null, null, null, null, null);
            Assert.fail("Should have failed by now.");
        } catch (Exception e) {
            String message = "The provided ICAO was not four characters long!";
            assertEquals(message, e.getMessage());
        }
        fullClear();
    }

    /**
     * Test updating a route within the database
     */
    @Test
    public void testUpdateOneRoute() {
        try {
            dataImport.InsertRoute(testRoute1);} catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.updateRoute(testRoute1.getAirlineID(), testRoute1.getSourceAirportID(), testRoute1.getDestinationAirportID(),
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
        fullClear();
    }

    /**
     * Test updating two routes within the database
     */
    @Test
    public void testUpdateTwoRoutes() {
        testRoutes.add(testRoute1);
        testRoutes.add(testRoute2);
        try {
            for (Route route: testRoutes) {
                dataImport.InsertRoute(route);
            }
        } catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.updateRoute(testRoute1.getAirlineID(), testRoute1.getSourceAirportID(), testRoute1.getDestinationAirportID(),
                    null, null, null, null, 6, null);
            Filter filter1 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                    testRoute1.getAirlineID(), testRoute1.getSourceAirportID(), testRoute1.getDestinationAirportID()), "OR");
            filters.add(filter1);
            dataImport.updateRoute(testRoute2.getAirlineID(), testRoute2.getSourceAirportID(), testRoute2.getDestinationAirportID(),
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
        fullClear();
    }

    /**
     * Test updating five routes within the database
     */
    @Test
    public void testUpdateFiveRoutes() {
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
            dataImport.updateRoute(testRoute1.getAirlineID(), testRoute1.getSourceAirportID(), testRoute1.getDestinationAirportID(),
                    null, null, null, null, 8, null);
            Filter filter1 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                    testRoute1.getAirlineID(), testRoute1.getSourceAirportID(), testRoute1.getDestinationAirportID()), "OR");
            filters.add(filter1);
            dataImport.updateRoute(testRoute2.getAirlineID(), testRoute2.getSourceAirportID(), testRoute2.getDestinationAirportID(),
                    null, null, null, null, 9, null);
            Filter filter2 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                    testRoute2.getAirlineID(), testRoute2.getSourceAirportID(), testRoute2.getDestinationAirportID()), "OR");
            filters.add(filter2);
            dataImport.updateRoute(testRoute3.getAirlineID(), testRoute3.getSourceAirportID(), testRoute3.getDestinationAirportID(),
                    null, null, null, null, 10, null);
            Filter filter3 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                    testRoute3.getAirlineID(), testRoute3.getSourceAirportID(), testRoute3.getDestinationAirportID()), "OR");
            filters.add(filter3);
            dataImport.updateRoute(testRoute4.getAirlineID(), testRoute4.getSourceAirportID(), testRoute4.getDestinationAirportID(),
                    null, null, null, null, 11, null);
            Filter filter4 = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                    testRoute4.getAirlineID(), testRoute4.getSourceAirportID(), testRoute4.getDestinationAirportID()), "OR");
            filters.add(filter4);
            dataImport.updateRoute(testRoute5.getAirlineID(), testRoute5.getSourceAirportID(), testRoute5.getDestinationAirportID(),
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
        fullClear();
    }

    /**
     * Test updating a route with empty parameters (should throw exception) within the database
     */
    @Test
    public void testUpdateRouteEmpty() {
        try {
            dataImport.InsertRoute(testRoute1);} catch (Exception e) {Assert.fail(e.getMessage());}
        try {
            dataImport.updateRoute(testRoute1.getAirlineID(), testRoute1.getSourceAirportID(), testRoute1.getDestinationAirportID(),
                    null, null, null, null, null, null);
            Assert.fail("Should have failed by now.");
        } catch (Exception e) {
            String message = "No parameters to update were provided!";
            assertEquals(message, e.getMessage());
        }
        fullClear();
    }
    //TODO Should add more tests for UpdateRoute, as newAirline and NewAirports are not tested

    /**
     * Test deleting an airline in the database
     */
    @Test
    public void testDeleteOneAirline() {
        try {
            dataImport.InsertAirline(testAirline1);} catch (Exception e) {Assert.fail(e.getMessage());}
        Filter filter = new Filter(format("id_airline = %d", testAirline1.getAirlineID()), "");
        filters.add(filter);
        try {
            dataImport.deleteAirline(testAirline1.getAirlineID());
        } catch (Exception e) {Assert.fail(e.getMessage());}
        actualAirlines = dataExport.FetchAirlines(filters);
        assertTrue(actualAirlines.isEmpty());
        fullClear();
    }

    /**
     * Test deleting two airlines in the database
     */
    @Test
    public void testDeleteTwoAirlines() {
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
            dataImport.deleteAirline(testAirline1.getAirlineID());
            dataImport.deleteAirline(testAirline2.getAirlineID());
        } catch (Exception e) {Assert.fail(e.getMessage());}
        actualAirlines = dataExport.FetchAirlines(filters);
        assertTrue(actualAirlines.isEmpty());
        fullClear();
    }

    /**
     * Test deleting five airlines in the database
     */
    @Test
    public void testDeleteFiveAirlines() {
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
            dataImport.deleteAirline(testAirline1.getAirlineID());
            dataImport.deleteAirline(testAirline2.getAirlineID());
            dataImport.deleteAirline(testAirline3.getAirlineID());
            dataImport.deleteAirline(testAirline4.getAirlineID());
            dataImport.deleteAirline(testAirline5.getAirlineID());
        } catch (Exception e) {Assert.fail(e.getMessage());}
        actualAirlines = dataExport.FetchAirlines(filters);
        assertTrue(actualAirlines.isEmpty());
        fullClear();
    }

    /**
     * Test deleting an airline with an invalid ID in the database
     */
    @Test
    public void testDeleteAirlineInvalidID() {
        try {
            dataImport.deleteAirline(8008);
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
    public void testDeleteOneAirport() {
        try {
            dataImport.InsertAirport(testAirport1);} catch (Exception e) {Assert.fail(e.getMessage());}
        Filter filter = new Filter(format("id_airport = %d", testAirport1.getAirportID()), "");
        filters.add(filter);
        try {
            dataImport.deleteAirport(testAirport1.getAirportID());
        } catch (Exception e) {Assert.fail(e.getMessage());}
        actualAirports = dataExport.FetchAirports(filters);
        assertTrue(actualAirports.isEmpty());
        fullClear();
    }

    /**
     * Test deleting two airports in the database
     */
    @Test
    public void testDeleteTwoAirports() {
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
            dataImport.deleteAirport(testAirport1.getAirportID());
            dataImport.deleteAirport(testAirport2.getAirportID());
        } catch (Exception e) {Assert.fail(e.getMessage());}
        actualAirports = dataExport.FetchAirports(filters);
        assertTrue(actualAirports.isEmpty());
        fullClear();
    }

    /**
     * Test deleting five airports in the database
     */
    @Test
    public void testDeleteFiveAirports() {
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
            dataImport.deleteAirport(testAirport1.getAirportID());
            dataImport.deleteAirport(testAirport2.getAirportID());
            dataImport.deleteAirport(testAirport3.getAirportID());
            dataImport.deleteAirport(testAirport4.getAirportID());
            dataImport.deleteAirport(testAirport5.getAirportID());
        } catch (Exception e) {Assert.fail(e.getMessage());}
        actualAirports = dataExport.FetchAirports(filters);
        assertTrue(actualAirports.isEmpty());
        fullClear();
    }

    /**
     * Test deleting an airport with an invalid ID in the database
     */
    @Test
    public void testDeleteAirportInvalidID() {
        try {
            dataImport.deleteAirport(9009);
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
    public void testDeleteOneRoute() {
        try {
            dataImport.InsertRoute(testRoute1);} catch (Exception e) {Assert.fail(e.getMessage());}
        Filter filter = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                testRoute1.getAirlineID(), testRoute1.getSourceAirportID(), testRoute1.getDestinationAirportID()), "");
        filters.add(filter);
        try {
            dataImport.deleteRoute(testRoute1.getAirlineID(), testRoute1.getSourceAirportID(), testRoute1.getDestinationAirportID());
        } catch (Exception e) {Assert.fail(e.getMessage());}
        actualRoutes = dataExport.FetchRoutes(filters);
        assertTrue(actualRoutes.isEmpty());
        fullClear();
    }

    /**
     * Test deleting two routes in the database
     */
    @Test
    public void testDeleteTwoRoutes() {
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
            dataImport.deleteRoute(testRoute1.getAirlineID(), testRoute1.getSourceAirportID(), testRoute1.getDestinationAirportID());
            dataImport.deleteRoute(testRoute2.getAirlineID(), testRoute2.getSourceAirportID(), testRoute2.getDestinationAirportID());
        } catch (Exception e) {Assert.fail(e.getMessage());}
        actualRoutes = dataExport.FetchRoutes(filters);
        assertTrue(actualRoutes.isEmpty());
        fullClear();
    }

    /**
     * Test deleting five routes in the database
     */
    @Test
    public void testDeleteFiveRoutes() {
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
            dataImport.deleteRoute(testRoute1.getAirlineID(), testRoute1.getSourceAirportID(), testRoute1.getDestinationAirportID());
            dataImport.deleteRoute(testRoute2.getAirlineID(), testRoute2.getSourceAirportID(), testRoute2.getDestinationAirportID());
            dataImport.deleteRoute(testRoute3.getAirlineID(), testRoute3.getSourceAirportID(), testRoute3.getDestinationAirportID());
            dataImport.deleteRoute(testRoute4.getAirlineID(), testRoute4.getSourceAirportID(), testRoute4.getDestinationAirportID());
            dataImport.deleteRoute(testRoute5.getAirlineID(), testRoute5.getSourceAirportID(), testRoute5.getDestinationAirportID());
        } catch (Exception e) {Assert.fail(e.getMessage());}
        actualRoutes = dataExport.FetchRoutes(filters);
        assertTrue(actualRoutes.isEmpty());
        fullClear();
    }

    /**
     * Test deleting a route with an invalid ID in the database
     */
    @Test
    public void testDeleteRouteInvalidAirlineID() {
        try {
            dataImport.InsertRoute(testRoute1);} catch (Exception e) {Assert.fail(e.getMessage());}
        Filter filter = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                testRoute1.getAirlineID(), testRoute1.getSourceAirportID(), testRoute1.getDestinationAirportID()), "");
        filters.add(filter);
        try {
            dataImport.deleteRoute(8008, testRoute1.getSourceAirportID(), testRoute1.getDestinationAirportID());
            Assert.fail("SQLException was supposed to be thrown.");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        fullClear();
    }

    /**
     * Test deleting a route with an invalid source airport ID in the database
     */
    @Test
    public void testDeleteRouteInvalidSourceID() {
        try {
            dataImport.InsertRoute(testRoute1);} catch (Exception e) {Assert.fail(e.getMessage());}
        Filter filter = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                testRoute1.getAirlineID(), testRoute1.getSourceAirportID(), testRoute1.getDestinationAirportID()), "");
        filters.add(filter);
        try {
            dataImport.deleteRoute(testRoute1.getAirlineID(), 8008, testRoute1.getDestinationAirportID());
            Assert.fail("SQLException was supposed to be thrown.");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        fullClear();
    }

    /**
     * Test deleting a route with an invalid destination airport ID in the database
     */
    @Test
    public void testDeleteRouteInvalidDestinationID() {
        try {
            dataImport.InsertRoute(testRoute1);} catch (Exception e) {Assert.fail(e.getMessage());}
        Filter filter = new Filter(format("id_airline = %d AND source_airport_id = %d AND destination_airport_id = %d",
                testRoute1.getAirlineID(), testRoute1.getSourceAirportID(), testRoute1.getDestinationAirportID()), "");
        filters.add(filter);
        try {
            dataImport.deleteRoute(testRoute1.getAirlineID(), testRoute1.getSourceAirportID(), 8008);
            Assert.fail("SQLException was supposed to be thrown.");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof Exception);
        }
        fullClear();
    }

}