package seng202.team6.gui.controller.routefinder;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AnalysisDistanceController {
    @FXML
    private Label airportInfoLabel;
    @FXML
    private Label distanceLabel;

    /**
     * Sets the labels in the connected FXML file to display airport IATAs and Distance between the airports.
     * @param title Title text
     * @param distance Distance text
     */
    public void SetInfo(String title, String distance) {
        airportInfoLabel.setText(title);
        distanceLabel.setText(distance);
    }
}
