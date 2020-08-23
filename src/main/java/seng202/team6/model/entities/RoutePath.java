package seng202.team6.model.entities;

import javafx.util.Pair;

import java.util.ArrayList;

public class RoutePath {
    String Source;
    String Destination;
    ArrayList<Pair<Double, Double>> Coordinates;

    public RoutePath(String newSource, String newDestination, ArrayList<Pair<Double, Double>> newCoordinates) {
        Source = newSource;
        Destination = newDestination;
        Coordinates = newCoordinates;
    }

    public String getSource() {
        return Source;
    }

    public String getDestination() {
        return Destination;
    }

    public ArrayList<Pair<Double, Double>> getCoordinates() {
        return Coordinates;
    }
}
