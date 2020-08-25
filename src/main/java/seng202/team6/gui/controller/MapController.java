package seng202.team6.gui.controller;

import javafx.scene.web.WebEngine;
import seng202.team6.model.entities.Airport;

import java.util.ArrayList;

/**
 * MapController class to interop between the Google maps API and Java
 */
public class MapController
{
    private WebEngine mapEngine;

    /**
     * Constructor for MapController takes in map params
     * @param engine Map engine to use
     */
    public MapController(WebEngine engine) {
        this.mapEngine = engine;
    }

    /**
     * Draw airports on the map
     * @param airports List of airports to draw
     */
    public void DrawAirportMarks(ArrayList<Airport> airports) {
        String JSFunction = "PlaceAirportMarkers(%s);";
        String airportString = BuildJavascriptAirportString(airports);
        this.mapEngine.executeScript(String.format(JSFunction, airportString));
    }

    /**
     * Get a Javascript array of airport objects
     * @param airports Airports
     * @return String
     */
    public String BuildJavascriptAirportString(ArrayList<Airport> airports) {
        StringBuilder airportString = new StringBuilder();
        airportString.append('[');
        for (Airport airport : airports) {
            airportString.append(
                    String.format("{lat: %f, lng: %f, " +
                                    "name: \"%s\", country: \"%s\", city: \"%s\", " +
                                    "iata: \"%s\", icao: \"%s\", alt: %d, tz: %d},",

                            airport.getLatitude(), airport.getLongitude(), airport.getName(),
                            airport.getCountry(), airport.getCity(), airport.getIATA(), airport.getICAO(),
                            airport.getAltitude(), airport.getTimezone()
                    )
            );
        }
        airportString.append(']');

        return airportString.toString();
    }
}
