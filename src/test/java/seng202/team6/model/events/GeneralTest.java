package seng202.team6.model.events;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class GeneralTest {

    @Test
    public void DateTest() {
        General test = new General(25, 1, 2020, 11, 23, "Testing event", "Just some more extra info",
                "Christchurch", "New Zealand");
        LocalDateTime expectedDateTime = LocalDateTime.of(2020, 1, 25, 11, 23);
        assertEquals(expectedDateTime, test.getDateTime());
    }
}
