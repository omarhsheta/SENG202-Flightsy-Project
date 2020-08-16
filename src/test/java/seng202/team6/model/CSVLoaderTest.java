package seng202.team6.model;

import org.junit.Test;
import java.util.ArrayList;
import seng202.team6.model.CSVLoader;
import seng202.team6.model.entities.Airline;
import seng202.team6.model.entities.Airport;
import seng202.team6.model.entities.Plane;
import seng202.team6.model.entities.Route;

public class CSVLoaderTest {

    @Test
    public void testAirplaneList() {
        CSVLoader test = new CSVLoader();
        ArrayList<Plane> testList = test.PlaneList("src\\test\\resources\\CSVLoader\\AirplaneTest.csv");
        Plane testPlane = new Plane("Flightsy", "CHC", "NZ");
        testPlane.equals(testList.get(0));
    }

    @Test
    public void testAirlineList() {
        CSVLoader test = new CSVLoader();
        ArrayList<Airline> testList = test.AirlineList("src\\test\\resources\\CSVLoader\\AirlineTest.csv");
        Airline testAirline = new Airline(69, "Flightsy Airways", "FA", "CHC", "NZ", "KIA KAHA", "New Zealand", 'Y');
        testAirline.equals(testList.get(0));
    }

    @Test
    public void testAirportList() {
        CSVLoader test = new CSVLoader();
        ArrayList<Airport> testList = test.AirportList("src\\test\\resources\\CSVLoader\\AirportTest.csv");
        Airport testAirport = new Airport(69, "The Dystopian Airport", "Dystopia", "North Korea", "DYS", "DYNK", 42069, 42069, 9001, 3, 'Y');
        testAirport.equals(testList.get(0));
    }

    @Test
    public void testRouteList() {
        CSVLoader test = new CSVLoader();
        ArrayList<Route> testList = test.RouteList("src\\test\\resources\\CSVLoader\\RouteTest.csv");
        Route testRoute = new Route("69", "Flightsy Airways", "CHC", 420, "TLV", 666, '\u0000', 5, "CR2");
        testRoute.equals(testList.get(0));

    }

}
