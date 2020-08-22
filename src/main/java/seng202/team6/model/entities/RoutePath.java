package seng202.team6.model.entities;

import javafx.util.Pair;

import java.util.ArrayList;

public class RoutePath {
    String Source;
    String Destination;
    ArrayList<Pair<Double, Double>> Coordinates = new ArrayList<>();

    public RoutePath(String newSource, String newDestination, ArrayList<Pair<Double, Double>> newCoordinates) {
        Source = newSource;
        Destination = newDestination;
        Coordinates = newCoordinates;
    }


}
