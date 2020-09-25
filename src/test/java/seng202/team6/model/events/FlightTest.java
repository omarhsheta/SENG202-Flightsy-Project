package seng202.team6.model.events;

import org.junit.Test;
import seng202.team6.model.entities.Route;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class FlightTest {
    @Test
    public void DateTest() {
        Route testRoute = new Route(1, "Test Airline", "TestSource", 23,
                "TestDest", 24, ' ', 0, "");
        Flight test = new Flight(25, 1, 2020, 11, 23, 26, 1,
                2020, 14, 16, "Testing Trip", "Nothing like testing a class :)", testRoute);
        int expectedDay = 25;
        int expectedMonth = 1;
        int expectedYear = 2020;
        int expectedHour = 11;
        int expectedMinute = 23;
        assertEquals(25, test.getDay());
        assertEquals(1, test.getMonth());
        assertEquals(2020, test.getYear());
    }

    @Test
    public void RouteTest() {
        Route testRoute = new Route(1, "Test Airline", "TestSource", 23,
                "TestDest", 24, ' ', 0, "");
        Flight test = new Flight(25, 1, 2020, 11, 23, 26, 1,
                2020, 14, 16, "Testing Trip", "Nothing like testing a class :)", testRoute);
        assertEquals(testRoute, test.getRoute());
    }
}
