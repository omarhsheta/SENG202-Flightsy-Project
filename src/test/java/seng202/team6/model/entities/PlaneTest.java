package seng202.team6.model.entities;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Test class for Planes
 */
public class PlaneTest {
    /**
     * Test if two plane objects the same
     */
    @Test
    public void TestEqualityDirect() {
        Plane plane1 = new Plane("Hello", ",", "World!");
        Plane plane2 = new Plane("Hello", ",", "World!");
        Plane plane3 = new Plane("Hi", ",", "developer!");

        assertEquals(plane1, plane2);
        assertNotEquals(plane1, plane3);
    }

    /**
     * Test if plane objects are the same in multiple directions
     */
    @Test
    public void TestEqualityChain() {
        Plane plane1 = new Plane("Hello", ",", "World!");
        Plane plane2 = new Plane("Hello", ",", "World!");
        Plane plane3 = new Plane("Hello", ",", "World!");

        assertEquals(plane1, plane2);
        assertEquals(plane2, plane3);
        assertEquals(plane3, plane1);
    }
}
