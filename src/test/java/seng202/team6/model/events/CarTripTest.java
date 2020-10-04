package seng202.team6.model.events;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CarTripTest {
    @Test
    public void DateTest() {
        CarTrip test = new CarTrip(25, 1, 2020, 11, 24, "Testing Trip",
                "Nothing like testing a class :)", "Christchurch", "Mew Zealand",
                "Dunedin", "New Zealand");
        LocalDateTime expectedDateTime = LocalDateTime.of(2020, 1, 25, 11, 24);
        assertEquals(expectedDateTime, test.getDateTime());
    }

    @Test
    public void OriginDestinationTest() {
        CarTrip test = new CarTrip(25, 1, 2020, 11, 24, "Testing Trip",
                "Nothing like testing a class :)", "Christchurch", "Mew Zealand",
                "Dunedin", "New Zealand");
        assertNotEquals(test.getOCity(), test.getDCity());
    }
}
