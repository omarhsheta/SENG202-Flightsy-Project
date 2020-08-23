package seng202.team6.model.data;

import org.junit.Before;
import org.junit.Test;
import seng202.team6.model.entities.Airline;
import seng202.team6.model.entities.Airport;
import seng202.team6.model.entities.Plane;
import seng202.team6.model.entities.Route;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class CSVLoaderTest {

    private CSVLoader csvLoader;

    @Before
    public void InitializeTest()
    {
        csvLoader = new CSVLoader();
    }

    @Test
    public void TestAirplaneList() {
        ArrayList<Plane> testList = csvLoader.PlaneList("src/test/resources/CSVLoader/AirplaneTest.csv");
        Plane testPlane = new Plane("Flightsy", "CHC", "NZ");
        assertEquals(testPlane, testList.get(0));
        assertEquals(1, testList.size());// Test for invalid data
    }

    @Test
    public void TestAirlineList() {
        ArrayList<Airline> testList = csvLoader.AirlineList("src/test/resources/CSVLoader/AirlineTest.csv");
        Airline testAirline = new Airline(69, "Flightsy Airways", "FA", "CHC", "NZ", "KIA KAHA", "New Zealand", 'Y');
        assertEquals(testAirline, testList.get(0));
        assertEquals(1, testList.size());// Test for invalid data
    }

    @Test
    public void TestAirportList() {
        ArrayList<Airport> testList = csvLoader.AirportList("src/test/resources/CSVLoader/AirportTest.csv");
        Airport testAirport = new Airport(69, "The Dystopian Airport", "Dystopia", "North Korea", "DYS", "DYNK", 42069, 42069, 9001, 3, 'Y');
        assertEquals(testAirport, testList.get(0));
        assertEquals(1, testList.size());// Test for invalid data
    }

    @Test
    public void TestRouteList() {
        ArrayList<Route> testList = csvLoader.RouteList("src/test/resources/CSVLoader/RouteTest.csv");
        Route testRoute = new Route(69, "Flightsy Airways", "CHC", 420, "TLV", 666, '\u0000', 5, "CR2");
        assertEquals(testRoute, testList.get(0));
        assertEquals(1, testList.size());// Test for invalid data
    }

    @Test
    public void TestNullParse() {
        ArrayList<ArrayList<String>> lines = csvLoader.ProcessedFile("src/test/resources/CSVLoader/NullTest.csv");
        ArrayList<String> expected = new ArrayList<>() {
            {
                add(null);
                add(null);
                add("Words");
            }
        };

        assertEquals(expected, lines.get(0));
    }

}
