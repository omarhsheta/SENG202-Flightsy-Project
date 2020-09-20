package seng202.team6.model.entities;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Test class for Airports
 */
public class AirportTest {

    /**
     * Test distance between two airports
     */
    @Test
    public void GetDistanceTest() {
        Airport airport1 = new Airport(1, "CHC", "VOID", "VOID", "VOID", "VOID",
                (float)35, (float)-35,  100, 3, '3');
        Airport airport2 = new Airport(1, "CHC", "VOID", "VOID", "VOID", "VOID",
                (float)-35, (float)35,  100, 3, '3');
        double result = airport1.GetDistance(airport2);

        assertEquals(10642, Math.floor(result), 1e-15);
    }

    /**
     * Test if two airport objects the same
     */
    @Test
    public void TestEqualityDirect() {
        Airport airport1 = new Airport(1, "CHC", "VOID", "VOID", "VOID", "VOID",
                (float)0, (float)0,  100, 3, '3');
        Airport airport2 = new Airport(1, "CHC", "VOID", "VOID", "VOID", "VOID",
                (float)0, (float)0,  100, 3, '3');
        Airport airport3 = new Airport(1, "WRONG", "VOID", "VOID", "VOID", "VOID",
                (float)0, (float)0,  100, 3, '3');

        assertEquals(airport1, airport2);
        assertNotEquals(airport1, airport3);
    }

    /**
     * Test if airport objects are the same in multiple directions
     */
    @Test
    public void TestEqualityChain() {
        Airport airport1 = new Airport(1, "CHC", "VOID", "VOID", "VOID", "VOID",
                (float)0, (float)0,  100, 3, '3');
        Airport airport2 = new Airport(1, "CHC", "VOID", "VOID", "VOID", "VOID",
                (float)0, (float)0,  100, 3, '3');

        Airport airport3 = new Airport(1, "CHC", "VOID", "VOID", "VOID", "VOID",
                (float)0, (float)0,  100, 3, '3');

        assertEquals(airport1, airport2);
        assertEquals(airport2, airport3);
        assertEquals(airport3, airport1);
    }
}
