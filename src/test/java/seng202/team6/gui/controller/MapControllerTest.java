package seng202.team6.gui.controller;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import seng202.team6.gui.controller.MapController;
import seng202.team6.model.entities.Airport;

import java.util.ArrayList;

/**
 * Test file for MapController.java
 */
public class MapControllerTest {
    private MapController controller;

    @Before
    public void Init() {
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

        ArrayList<Airport> airports = new ArrayList<>();
        airports.add(new Airport(1, "Christchurch International", "Christchurch", "New Zealand",
                "CHC", "NZCH", -43.487598f, 172.537292f, 37, 12, 'Y'));

        airports.add(new Airport(2, "No", "Null", "Null land",
                "NUL", "No", 0f, 0f, 0, 12, 'N'));

        assertEquals("[{lat: -43.487598, lng: 172.537292, name: \"Christchurch International\", country: \"New Zealand\", " +
                                "city: \"Christchurch\", iata: \"CHC\", icao: \"NZCH\", alt: 37, tz: 12.000000}," +
                                "{lat: 0.000000, lng: 0.000000, name: \"No\", country: \"Null land\", " +
                                "city: \"Null\", iata: \"NUL\", icao: \"No\", alt: 0, tz: 12.000000},]",
                controller.BuildJavascriptAirportString(airports));
    }
}
