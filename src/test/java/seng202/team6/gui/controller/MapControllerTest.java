package seng202.team6.gui.controller;

import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import seng202.team6.gui.controller.MapController;
import seng202.team6.model.entities.Airport;
import seng202.team6.model.entities.RoutePath;
import seng202.team6.model.interfaces.IMapDrawable;

import java.util.ArrayList;

/**
 * Test file for MapController.java
 */
public class MapControllerTest {
    private MapController controller;

    @Before
    public void InitializeTest() {
        controller = new MapController(null);
    }

    /**
     * Test building airport string from airport list
     * Used with placing points on a map using javascript
     */
    @Test
    public void TestAirportString() {
        String format = "{lat: %f, lng: %f, " +
                "name: \"%s\", country: \"%s\", city: \"%s\"," +
                "iata: \"%s\", icao: \"%s\", alt: %d, tz: %d},";

        ArrayList<IMapDrawable> airports = new ArrayList<>();
        airports.add(new Airport(1, "Christchurch International", "Christchurch", "New Zealand",
                "CHC", "NZCH", -43.487598f, 172.537292f, 37, 12, 'Y'));

        airports.add(new Airport(2, "No", "Null", "Null land",
                "NUL", "No", 0f, 0f, 0, 12, 'N'));

        assertEquals("[{lat: -43.487598, lng: 172.537292, name: \"Christchurch International\", country: \"New Zealand\", " +
                                "city: \"Christchurch\", iata: \"CHC\", icao: \"NZCH\", alt: 37, tz: 12}," +
                                "{lat: 0.000000, lng: 0.000000, name: \"No\", country: \"Null land\", " +
                                "city: \"Null\", iata: \"NUL\", icao: \"No\", alt: 0, tz: 12},]",
                controller.BuildJavascriptArrayString(airports));
    }

    /**
     * Test building RoutePath string
     * Used with placing points on a map using javascript
     * Single coord object used in this test
     */
    @Test
    public void TestRoutePathOne() {
        String expected = "[{lat: -43.487600, lng: 172.537400}]";

        ArrayList<Pair<Double, Double>> coords = new ArrayList<>();
        coords.add(new Pair<>(-43.487600d, 172.537400d));
        RoutePath path = new RoutePath(null, null, coords);

        assertEquals(expected, controller.BuildJavascriptArrayString(path));
    }

    /**
     * Test building RoutePath string
     * Used with placing points on a map using javascript
     * Multiple coords used in this test
     */
    @Test
    public void TestRoutePathMultiple() {
        String expected = "[{lat: -43.487600, lng: 172.537400},{lat: -37.669000, lng: 144.841000},{lat: -37.669000, lng: 144.841000}]";

        ArrayList<Pair<Double, Double>> coords = new ArrayList<>();
        coords.add(new Pair<>(-43.487600d, 172.537400d));
        coords.add(new Pair<>(-37.669000d, 144.841000d));
        coords.add(new Pair<>(-37.669000d, 144.841000d));
        RoutePath path = new RoutePath(null, null, coords);

        assertEquals(expected, controller.BuildJavascriptArrayString(path));
    }
}
