package seng202.team6.model.events;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GeneralTest {

    @Test
    public void DateTest() {
        General test = new General(25, 1, 2020, 11, 23, "Testing event", "Just some more extra info",
                "Christchurch", "New Zealand");
        int expectedDay = 25;
        int expectedMonth = 1;
        int expectedYear = 2020;
        int expectedHour = 11;
        int expectedMinute = 23;
        assertEquals(25, test.getDay());
        assertEquals(1, test.getMonth());
        assertEquals(2020, test.getYear());
    }
}
