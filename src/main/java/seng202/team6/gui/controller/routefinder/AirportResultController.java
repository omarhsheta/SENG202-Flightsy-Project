package seng202.team6.gui.controller.routefinder;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seng202.team6.model.entities.Airport;

/**
 * Airport Result controller for airport results in trip view
 */
public class AirportResultController extends ResultController {

    @FXML
    private Label airportLabel;

    @FXML
    private Label city;

    @FXML
    private Label country;

    @FXML
    private Label airportInfo;

    private Airport airport;

    /**
     * Set airport info
     * @param airportResult Airport object
     */
    public void SetAirport(Airport airportResult) {
        this.airport = airportResult;
        airportLabel.setText(airportResult.getName());
        city.setText(String.format("City: %s", airportResult.getCity()));
        country.setText(String.format("Country: %s", airportResult.getCountry()));
        airportInfo.setText(String.format("IATA: %s | ICAO: %s", airportResult.getIATA(), airportResult.getICAO()));
    }

    /**
     * Called when the user wants to view the airport in question
     * This method moves the map viewpoint to the airport in question.
     */
    @FXML
    private void OnViewButtonClicked()
    {
        if (mapController == null) {
            return;
        }

        mapController.GoTo(airport.getLatitude(), airport.getLongitude());
    }
}
