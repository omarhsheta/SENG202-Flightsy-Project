package seng202.team6.model.entities;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Test class for Airline
 */
public class AirlineTest {
    /**
     * Test if two airline objects the same
     */
    @Test
    public void TestEqualityDirect() {
        Airline airline1 = new Airline(0, "null", "no", "\n", "\t", "No",
                "No Country", 'N');

        Airline airline2 = new Airline(0, "null", "no", "\n", "\t", "No",
                "No Country", 'N');

        Airline airline3 = new Airline(0, "null", "", "\n", "\t", "No",
                "Yes Country", 'N');

        assertEquals(airline1, airline2);
        assertNotEquals(airline1, airline3);
    }

    /**
     * Test if airline objects are the same in multiple directions
     */
    @Test
    public void TestEqualityChain() {
        Airline airline1 = new Airline(0, "null", "no", "\n", "\t", "No",
                "No Country", 'N');

        Airline airline2 = new Airline(0, "null", "no", "\n", "\t", "No",
                "No Country", 'N');

        Airline airline3 = new Airline(0, "null", "no", "\n", "\t", "No",
                "No Country", 'N');

        assertEquals(airline1, airline2);
        assertEquals(airline2, airline3);
        assertEquals(airline3, airline1);
    }
}
