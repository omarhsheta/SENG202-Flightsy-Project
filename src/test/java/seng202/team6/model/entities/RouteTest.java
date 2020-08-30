package seng202.team6.model.entities;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Test class for Routes
 */
public class RouteTest {
    /**
     * Test if two route objects the same
     */
    @Test
    public void TestEqualityDirect() {
        Route route1 = new Route(0, "Hello", "World", 0,
                "", 1, 'N', 0, "");

        Route route2 = new Route(0, "Hello", "World", 0,
                "", 1, 'N', 0, "");

        Route route3 = new Route(100, "Hello", "Developer", 69,
                "", 1, 'N', 420, "");

        assertEquals(route1, route2);
        assertNotEquals(route1, route3);
    }

    /**
     * Test if route objects are the same in multiple directions
     */
    @Test
    public void TestEqualityChain() {
        Route route1 = new Route(0, "Hello", "World", 0,
                "", 1, 'N', 0, "");

        Route route2 = new Route(0, "Hello", "World", 0,
                "", 1, 'N', 0, "");

        Route route3 = new Route(0, "Hello", "World", 0,
                "", 1, 'N', 0, "");

        assertEquals(route1, route2);
        assertEquals(route2, route3);
        assertEquals(route3, route1);
    }
}
