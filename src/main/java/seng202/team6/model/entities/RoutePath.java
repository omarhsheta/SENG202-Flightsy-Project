package seng202.team6.model.entities;

import com.google.gson.Gson;
import javafx.util.Pair;
import seng202.team6.model.interfaces.IMapDrawable;
import seng202.team6.model.interfaces.JSONSerializable;

import java.util.ArrayList;

public class RoutePath implements IMapDrawable, JSONSerializable {
    String source;
    String destination;
    ArrayList<Pair<Double, Double>> coordinates;

    /**
     * Constructor for the class RoutePath
     * @param newSource String value for Source
     * @param newDestination String value for Destination
     * @param newCoordinates An ArrayList of Pairs of Doubles that represent coordinates
     */
    public RoutePath(String newSource, String newDestination, ArrayList<Pair<Double, Double>> newCoordinates) {
        this.source = newSource;
        this.destination = newDestination;
        this.coordinates = newCoordinates;
    }

    /**
     * Constructor for turning object from JSON
     * @param json JSON String
     */
    public static RoutePath FromJSON(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, RoutePath.class);
    }

    /**
     * Turn object into JSON Representation
     * @return String JSON String
     */
    @Override
    public String ToJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String GetSource() {
        return this.source;
    }

    public String GetDestination() {
        return this.destination;
    }

    public ArrayList<Pair<Double, Double>> GetCoordinates() {
        return this.coordinates;
    }

    @Override
    public String ConvertToJavascriptString() {
        StringBuilder string = new StringBuilder();

        //This ones a bit weird, if the list is 1 long then should return without '{' and '}'
        string.append(String.format("lat: %f, lng: %f", coordinates.get(0).getKey(), coordinates.get(0).getValue()));
        if (coordinates.size() > 1) {
            string.append("},"); //Else size > 1 then append "}," then add all the other points
            for (int i = 1; i < this.coordinates.size() - 1; i++) {
                string.append(String.format("{lat: %f, lng: %f},", coordinates.get(i).getKey(), coordinates.get(i).getValue()));
            }
            //Then finish off with opening '{' so when we convert to an array '}' is put on the end
            string.append(String.format("{lat: %f, lng: %f", coordinates.get(coordinates.size() - 1).getKey(), coordinates.get(coordinates.size() - 1).getValue()));
        }

        return string.toString();
    }
}
