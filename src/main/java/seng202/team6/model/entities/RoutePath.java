package seng202.team6.model.entities;

import javafx.util.Pair;
import seng202.team6.model.interfaces.IMapDrawable;

import java.util.ArrayList;

public class RoutePath implements IMapDrawable {
    String source;
    String destination;
    ArrayList<Pair<Double, Double>> coordinates;

    public RoutePath(String newSource, String newDestination, ArrayList<Pair<Double, Double>> newCoordinates) {
        this.source = newSource;
        this.destination = newDestination;
        this.coordinates = newCoordinates;
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
