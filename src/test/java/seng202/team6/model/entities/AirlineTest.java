package seng202.team6.model.entities;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Test class for Airline
 */
public class AirlineTest {

    private final Airline airline1 = new Airline(0, "null", "no", "\n", "\t", "No",
            "No Country", 'N');

    private final Airline airline2 = new Airline(0, "null", "no", "\n", "\t", "No",
            "No Country", 'N');

    private final Airline airline3 = new Airline(0, "null", "", "\n", "\t", "No",
            "Yes Country", 'N');

    private final Airline airline4 = new Airline(50, "JSON Air", "JA", "JSO", "Hey", "Yes",
            "JSON Country", 'Y');

    /**
     * Test if two airline objects the same
     */
    @Test
    public void TestEqualityDirect() {
        assertEquals(airline1, airline2);
        assertNotEquals(airline1, airline3);
    }

    /**
     * Test if airline objects are the same in multiple directions
     */
    @Test
    public void TestEqualityChain() {
        assertEquals(airline1, airline2);
        assertEquals(airline2, airline1);
        assertNotEquals(airline2, airline3);
        assertNotEquals(airline3, airline2);
    }
}
