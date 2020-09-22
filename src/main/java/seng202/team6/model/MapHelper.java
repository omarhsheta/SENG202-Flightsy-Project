package seng202.team6.model;

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
public class MapHelper
{
    private WebEngine mapEngine;

    /**
     * Constructor for MapController takes in map params
     * @param engine Map engine to use
     */
    public MapHelper(WebEngine engine) {
        this.mapEngine = engine;
    }

    /**
     * Draw airports on the map
     * @param airports List of airports to draw
     */
    public void DrawAirportMarks(ArrayList<Airport> airports) {
        String JSFunction = "PlaceAirportMarkers(%s);";
        String airportString = BuildJavascriptArrayString(airports);
        if (!airportString.equals("")) {
            this.mapEngine.executeScript(String.format(JSFunction, airportString));
        }
    }

    /**
     * Draw line between airports
     * @param airports List of airport objects
     */
    public void DrawLineBetween(ArrayList<Airport> airports) {
        String JSFunction = "PlaceRouteLines(%s);";
        ArrayList<Pair<Float, Float>> positions = new ArrayList<>();
        for (Airport airport : airports) {
            positions.add(new Pair<>(airport.getLatitude(), airport.getLongitude()));
        }
        String line = PositionsToArray(positions);
        if (!line.equals("")) {
            this.mapEngine.executeScript(String.format(JSFunction, line));
        }
    }

    /**
     * Draw routes on the map
     * @param route Route object to draw
     */
    public void DrawRoutePath(RoutePath route) {
        String JSFunction = "PlaceRouteLines(%s);";
        String routeString = BuildJavascriptArrayString(route);
        if (!routeString.equals("")) {
            this.mapEngine.executeScript(String.format(JSFunction, routeString));
        }
    }

    /**
     * Clear all markers from the map
     */
    public void ClearAll() {
        this.mapEngine.executeScript("ClearMapMarkers();");
    }

    /**
     * Go to position
     * @param lat Latitude
     * @param lng Longitude
     */
    public void GoTo(double lat, double lng) {
        this.mapEngine.executeScript(String.format("GoToPosition(%f, %f);", lat, lng));
    }

    /**
     * Turn IMapDrawable objects into javascript array
     * @param drawableList Objects which implement IMapDrawable interface
     * @return String Javascript string array representation
     */
    public String BuildJavascriptArrayString(ArrayList<? extends IMapDrawable> drawableList) {
        if (drawableList == null) {
            return "";
        }

        StringBuilder returnString = new StringBuilder();
        returnString.append('[');
        for (IMapDrawable drawable : drawableList) {
            if (drawable == null) {
                continue;
            }
            returnString.append(String.format("{%s},", drawable.ConvertToJavascriptString()));
        }
        returnString.append(']');
        return returnString.toString();
    }

    /**
     * Turn list of pairs of numbers into a javascript array
     * @param positions List of pairs of floats
     * @return Javascript array string format
     */
    public String PositionsToArray(ArrayList<Pair<Float, Float>> positions) {
        if (positions == null) {
            return "";
        }
        StringBuilder string = new StringBuilder();
        string.append('[');

        string.append(String.format("{lat: %f, lng: %f}", positions.get(0).getKey(), positions.get(0).getValue()));

        if (positions.size() > 1) {
            string.append(","); //Else size > 1 then append "}," then add all the other points
            for (int i = 1; i < positions.size() - 1; i++) {
                string.append(String.format("{lat: %f, lng: %f},", positions.get(i).getKey(), positions.get(i).getValue()));
            }

            string.append(String.format("{lat: %f, lng: %f}", positions.get(positions.size() - 1).getKey(), positions.get(positions.size() - 1).getValue()));
        }
        string.append("]");
        return string.toString();
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
