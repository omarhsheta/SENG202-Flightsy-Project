package seng202.team6.model.data;

import org.junit.Before;
import org.junit.Test;
import seng202.team6.model.entities.*;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class CSVLoaderTest {
    private static final String resourceFolder = "src/test/resources/CSVLoader/";
    private CSVLoader csvLoader;

    @Before
    public void InitializeTest()
    {
        csvLoader = new CSVLoader();
    }

    /**
     * Test getting Airplanes List from CSV file
     */
    @Test
    public void TestAirplaneList() {
        ArrayList<Plane> testList = csvLoader.GetCSVPlanes(resourceFolder + "AirplaneTest.csv");
        Plane testPlane = new Plane("Flightsy", "CHC", "NZ");
        assertEquals(testPlane, testList.get(0));
        assertEquals(1, testList.size());// Test for invalid data
    }

    /**
     * Test getting Airline list from CSV file
     */
    @Test
    public void TestAirlineList() {
        ArrayList<Airline> testList = csvLoader.GetCSVAirlineList(resourceFolder + "AirlineTest.csv");
        Airline testAirline = new Airline(69, "Flightsy Airways", "FA", "CHC",
                "NZ", "KIA KAHA", "New Zealand", 'Y');
        assertEquals(testAirline, testList.get(0));
        assertEquals(1, testList.size());// Test for invalid data
    }

    /**
     * Test getting Airport list from CSV file
     */
    @Test
    public void TestAirportList() {
        ArrayList<Airport> testList = csvLoader.GetCSVAirportList(resourceFolder + "AirportTest.csv");
        Airport testAirport = new Airport(69, "The Dystopian Airport", "Dystopia", "North Korea",
                "DYS", "DYNK", (float)42069, (float)42069, 9001, 3, 'Y');
        assertEquals(testAirport, testList.get(0));
        assertEquals(1, testList.size());// Test for invalid data
    }

    /**
     * Test getting route list from CSV file
     */
    @Test
    public void TestRouteList() {
        ArrayList<Route> testList = csvLoader.GetCSVRouteList(resourceFolder + "RouteTest.csv");
        Route testRoute = new Route(69, "Flightsy Airways", "CHC", 420, "TLV", 666, '\u0000', 5, "CR2");
        assertEquals(testRoute, testList.get(0));
        assertEquals(1, testList.size());// Test for invalid data
    }

    /**
     * Test parsing null characters from CSV file
     */
    @Test
    public void TestNullParse() {
        ArrayList<ArrayList<String>> lines = csvLoader.ProcessCSVFile(resourceFolder + "NullTest.csv");
        ArrayList<String> expected = new ArrayList<>() {
            {
                add(null);
                add(null);
                add("Words");
            }
        };

        assertEquals(expected, lines.get(0));
    }

    /**
     * Test getting RoutePath from CSV file
     */
    @Test
    public void TestRoutePath() {
        RoutePath path = csvLoader.GetCSVRoutePath(resourceFolder + "NZCH-WSSS.csv");
        assertEquals("NZCH", path.GetSource());
        assertEquals("WSSS", path.GetDestination());
        assertEquals(31, path.GetCoordinates().size());
    }

}
