package seng202.team6.gui.controller.routefinder;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seng202.team6.model.entities.Airport;

import static seng202.team6.gui.controller.routefinder.ResultController.mapHelper;

public class FlightPathsController {

    @FXML
    private Label flightLabel;

    @FXML
    private Label origin;

    @FXML
    private Label originAirportInfo;

    @FXML
    private Label destination;

    @FXML
    private Label destinationAirportInfo;

    private Airport originAirport;

    private Airport destinationAirport;


    /**
     * Set flight info
     * @param originResult Has origin airport info
     * @param destinationResult Has destination airport info
     */
    public void SetAirport(Airport originResult, Airport destinationResult) {
        this.originAirport = originResult;
        this.destinationAirport = destinationResult;
        flightLabel.setText(String.format("%s TO %s", originResult.getIATA(), destinationResult.getIATA()));
        origin.setText(String.format("Origin: %s, %s", originResult.getCity(), originResult.getCountry()));
        destination.setText(String.format("Destination: %s, %s", destinationResult.getCity(), destinationResult.getCountry()));
        originAirportInfo.setText(String.format("Origin ICAO: %s", originResult.getICAO()));
        destinationAirportInfo.setText(String.format("Destination ICAO: %s", destinationResult.getICAO()));
    }

    /**
     * Called when the user wants to view the airport in question
     * This method moves the map viewpoint to the airport in question.
     */
    @FXML
    private void OnViewButtonClicked()
    {
//        if (mapHelper == null) {
//            return;
//        }
//
//        mapHelper.GoTo(originAirport.getLatitude(), originAirport.getLongitude());
//        mapHelper.GoTo(destinationAirport.getLatitude(), destinationAirport.getLongitude());
    }
}
