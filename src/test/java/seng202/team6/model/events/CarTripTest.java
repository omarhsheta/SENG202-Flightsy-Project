package seng202.team6.model.events;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CarTripTest {
    @Test
    public void DateTest() {
        CarTrip test = new CarTrip(25, 1, 2020, "Testing Trip",
                "Nothing like testing a class :)", "Christchurch", "Mew Zealand",
                "Dunedin", "New Zealand");
        int expectedDay = 25;
        int expectedMonth = 1;
        int expectedYear = 2020;
        assertEquals(25, test.getDay());
        assertEquals(1, test.getMonth());
        assertEquals(2020, test.getYear());
    }

    @Test
    public void OriginDestinationTest() {
        CarTrip test = new CarTrip(25, 1, 2020, "Testing Trip",
                "Nothing like testing a class :)", "Christchurch", "Mew Zealand",
                "Dunedin", "New Zealand");
        assertNotEquals(test.getOCity(), test.getDCity());
    }
}
