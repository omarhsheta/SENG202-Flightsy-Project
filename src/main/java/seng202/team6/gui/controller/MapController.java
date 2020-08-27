package seng202.team6.gui.controller;

import javafx.scene.web.WebEngine;
import javafx.util.Pair;
import seng202.team6.model.entities.Airport;
import seng202.team6.model.entities.Route;
import seng202.team6.model.entities.RoutePath;
import seng202.team6.model.interfaces.IMapDrawable;

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
        String airportString = BuildJavascriptArrayString(airports);
        this.mapEngine.executeScript(String.format(JSFunction, airportString));
    }

    /**
     * Draw routes on the map
     * @param route Route object to draw
     */
    public void DrawRoutePath(RoutePath route) {
        String JSFunction = "PlaceRouteLines(%s);";
        String routeString = BuildJavascriptArrayString(route);
        this.mapEngine.executeScript(String.format(JSFunction, routeString));
    }

    /**
     * Clear all markers from the map
     */
    public void ClearAll() {
        this.mapEngine.executeScript("ClearMapMarkers();");
    }

    /**
     * Turn IMapDrawable objects into javascript array
     * @param drawableList Objects which implement IMapDrawable interface
     * @return String Javascript string array representation
     */
    public String BuildJavascriptArrayString(ArrayList<? extends IMapDrawable> drawableList) {
        StringBuilder returnString = new StringBuilder();
        returnString.append('[');
        for (IMapDrawable drawable : drawableList) {
            returnString.append(String.format("{%s},", drawable.ConvertToJavascriptString()));
        }
        returnString.append(']');
        return returnString.toString();
    }

    /**
     * Turn IMapDrawable objects into javascript array
     * @param drawable Object which implement IMapDrawable interface
     * @return String Javascript string array representation
     */
    public String BuildJavascriptArrayString(IMapDrawable drawable) {
        return String.format("[{%s}]", drawable.ConvertToJavascriptString());
    }
}
