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
        Airline testAirline1 = new Airline(69, "Flightsy Airways", "FA", "CHC", "NZ", "KIA KAHA", "New Zealand", 'Y');
        Airline testAirline2 = new Airline(0,"0","0","0","0","0","0",'0');
        assertEquals(testAirline1, testList.get(0));
        assertEquals(testAirline2, testList.get(1));
        assertEquals(2, testList.size());// Test for invalid data
    }

    /**
     * Test getting Airport list from CSV file
     */
    @Test
    public void TestAirportList() {
        ArrayList<Airport> testList = csvLoader.GetCSVAirportList(resourceFolder + "AirportTest.csv");
        Airport testAirport1 = new Airport(69, "The Dystopian Airport", "Dystopia", "North Korea", "DYS", "DYNK", (float)42069, (float)42069, 9001, 3, 'Y');
        Airport testAirport2 = new Airport(69,"The Dystopian Airport","Dystopia", "North Korea","DYS","DYNK",(float)42069,(float)42069,9001,3,'U');
        Airport testAirport3 = new Airport(0, "0", "0", "0", "0", "0", 0, 0, 0, 0, '0');
        assertEquals(testAirport1, testList.get(0));
        assertEquals(testAirport2, testList.get(1));
        assertEquals(testAirport3, testList.get(2));
        assertEquals(3, testList.size());// Test for invalid data
    }

    /**
     * Test getting route list from CSV file
     */
    @Test
    public void TestRouteList() {
        ArrayList<Route> testList = csvLoader.GetCSVRouteList(resourceFolder + "RouteTest.csv");
        Route testRoute1 = new Route(69, "Flightsy Airways", "CHC", 420, "TLV", 666, 'N', 5, "CR2");
        Route testRoute2 = new Route(69, "Flightsy Airways", "CHC", 420, "TLV", 666, 'Y', 5, "CR2");
        Route testRoute3 = new Route(69, "Flightsy Airways", "CHC", 420, "TLV", 666, 'N', 5, "CR2");
        Route testRoute4 = new Route(0,"0","0",0,"0",0,'0',0, "0");

        assertEquals(testRoute1, testList.get(0));
        assertEquals(testRoute2, testList.get(1));
        assertEquals(testRoute3, testList.get(2));
        assertEquals(testRoute4, testList.get(3));

        assertEquals(4, testList.size());// Test for invalid data
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

    /**
     * Test parsing an empty CSV file
     */
    @Test
    public void TestEmptyParse() {
        ArrayList<ArrayList<String>> testLines = csvLoader.ProcessCSVFile(resourceFolder + "EmptyTest.csv");
        assertEquals(0, testLines.size());
    }

}
