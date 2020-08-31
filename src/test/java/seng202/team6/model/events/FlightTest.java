package seng202.team6.model.events;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class FlightTest {
    @Test
    public void DateTest() {
        Flight test = new Flight(25, 1, 2020, "Testing Trip",
                "Nothing like testing a class :)", "Christchurch", "Mew Zealand",
                "Dunedin", "New Zealand", "Flightsy Flights",
                "Christchurch International Airport", "Airpurt");
        int expectedDay = 25;
        int expectedMonth = 1;
        int expectedYear = 2020;
        assertEquals(25, test.getDay());
        assertEquals(1, test.getMonth());
        assertEquals(2020, test.getYear());
    }

    @Test
    public void OriginDestinationTest() {
        Flight test = new Flight(25, 1, 2020, "Testing Trip",
                "Nothing like testing a class :)", "Christchurch", "Mew Zealand",
                "Dunedin", "New Zealand", "Flightsy Flights",
                "Christchurch International Airport", "Airpurt");
        assertNotEquals(test.getOAirport(), test.getDAirport());
    }
}
