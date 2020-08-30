package seng202.team6.model.entities;

import org.junit.Before;
import org.junit.Test;
import seng202.team6.model.entities.Airport;

import static org.junit.Assert.*;

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
                35, -35,  100, 3, '3');
        Airport airport2 = new Airport(1, "CHC", "VOID", "VOID", "VOID", "VOID",
                -35, 35,  100, 3, '3');
        double result = airport1.GetDistance(airport2);

        assertEquals(98, Math.floor(result), 1e-15);
    }

    /**
     * Test if two airport objects the same
     */
    @Test
    public void TestEqualityDirect() {
        Airport airport1 = new Airport(1, "CHC", "VOID", "VOID", "VOID", "VOID",
                0, 0,  100, 3, '3');
        Airport airport2 = new Airport(1, "CHC", "VOID", "VOID", "VOID", "VOID",
                0, 0,  100, 3, '3');
        Airport airport3 = new Airport(1, "WRONG", "VOID", "VOID", "VOID", "VOID",
                0, 0,  100, 3, '3');

        assertEquals(airport1, airport2);
        assertNotEquals(airport1, airport3);
    }

    /**
     * Test if airport objects are the same in multiple directions
     */
    @Test
    public void TestEqualityChain() {
        Airport airport1 = new Airport(1, "CHC", "VOID", "VOID", "VOID", "VOID",
                0, 0,  100, 3, '3');
        Airport airport2 = new Airport(1, "CHC", "VOID", "VOID", "VOID", "VOID",
                0, 0,  100, 3, '3');

        Airport airport3 = new Airport(1, "CHC", "VOID", "VOID", "VOID", "VOID",
                0, 0,  100, 3, '3');

        assertEquals(airport1, airport2);
        assertEquals(airport2, airport3);
        assertEquals(airport3, airport1);
    }
}
