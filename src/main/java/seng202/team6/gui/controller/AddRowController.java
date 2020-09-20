package seng202.team6.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import seng202.team6.model.data.DataHandler;
import seng202.team6.model.entities.Airline;
import seng202.team6.model.entities.Airport;
import seng202.team6.model.entities.Route;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Set;

public class AddRowController implements Initializable
{
    @FXML
    private TextField
            // Airline fields
            airIdField, airNameField, airAliasField, AirIataField, AirIcaoField, AirCallsignField,
            AirCountryField,
    // Airport fields
    airpId, airpName, airpCity, airpCountry, airpIata, airpIcao, airpLat, airpLon, airpAlt, airpTim,
            airpDst,
    // Route fields
    rouAir, rouAirId, rouSouAir, rouSouAirId, rouDesAir, rouDesAirId, rouEqp;

    @FXML
    private ComboBox AirActiveField, rouCod;

    @FXML
    private Slider rouStp;

    @FXML
    private Text InfoText;

    private DataHandler dataHandler;

    private int airlinesAdded, airportsAdded, routesAdded;

    /**
     * Initialise the pop up window, fetch datahandler instance and set counters.
     * @param url Windows URL
     * @param resourceBundle Windows resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        dataHandler = DataHandler.GetInstance();
        airlinesAdded = airportsAdded = routesAdded = 0;
    }

    /**
     * Helper function to show/modify the existing message label.
     * Can show red error text or green success text.
     * @param error When set to true message appears in red fill, green when false.
     * @param message Passed in message to display to the user.
     */
    private void ShowMessage(boolean error, String message) {
        if (error) {
            InfoText.setFill(Paint.valueOf("Red"));
        } else {
            InfoText.setFill(Paint.valueOf("Green"));
        }
        InfoText.setText(message);
        InfoText.setVisible(true);
    }

    /**
     * Function to check the validity of the airport data inputted by the user.
     * Also checks that data is suitable for inserting into the database.
     * @param airpId Airport Id
     * @param airpName Airport Name
     * @param airpCity City
     * @param airpCountry Country
     * @param airpIata IATA
     * @param airpIcao ICAO
     * @param airpLat Latitude
     * @param airpLon Longitude
     * @param airpAlt Altitude
     * @param airpTim Timezone
     * @param airpDst Daylight savings
     * @return ArrayList of fields to be entered into database, or null if invalid input occurs
     */
    private ArrayList<String> CheckAirport(String airpId, String airpName, String airpCity, String airpCountry, String airpIata,
                                           String airpIcao, String airpLat, String airpLon, String airpAlt, String airpTim,
                                           String airpDst) {

        try {  // Check if airport id input is valid
            Integer.parseInt(airpId);
        } catch (Exception e) {
            ShowMessage(true, "Check the Airport ID field and try again");
            return null;
        }

        if (!airpLat.equals("")) {
            try {  // Check if latitude input is valid
                Float.parseFloat(airpLat);
            } catch (Exception e) {
                ShowMessage(true, "Check the latitude field and try again");
                return null;
            }
        }

        if (!airpLon.equals("")) {
            try {  // Check if latitude and longitude inputs are valid
                Float.parseFloat(airpLon);
            } catch (Exception e) {
                ShowMessage(true, "Check the longitude field and try again");
                return null;
            }
        }

        if (!airpAlt.equals("")) {
            try {  // Check if altitude input is valid
                Integer.parseInt(airpAlt);
            } catch (Exception e) {
                ShowMessage(true, "Check the Altitude field and try again");
                return null;
            }
        }

        if (!airpTim.equals("")) {
            try {  // Check if the time zone input is valid
                Integer.parseInt(airpTim);
            } catch (Exception e) {
                ShowMessage(true, "Check the Time Zone field and try again");
                return null;
            }
        }

        String dst;
        Set<String> validValues = Set.of("e", "a", "s", "o", "z", "n");
        if (validValues.contains(airpDst.toLowerCase())) {
            dst = airpDst.toUpperCase();
        } else {
            dst = "U";  // Unknown Daylight savings value
        }

        ArrayList<String> airport = new ArrayList<>(Arrays.asList(airpId, airpName, airpCity, airpCountry, airpIata, airpIcao, airpLat, airpLon,
                airpAlt, airpTim, dst));
        return airport;
    }

    /**
     * Function to check the validity of the airline data inputted by the user.
     * Also checks that data is suitable for inserting into the database.
     * @param airlineID Airline ID
     * @param name Airline name
     * @param alias Alias
     * @param iata IATA
     * @param icao ICAO
     * @param callsign Callsign
     * @param country Country
     * @param active Is the airline active
     * @return ArrayList of fields to be entered into database, or null if invalid input occurs
     */
    private ArrayList<String> CheckAirline(String airlineID, String name, String alias, String iata, String icao,
                                           String callsign, String country, String active) {

        try {  // Check if airline id input is valid
            Integer.parseInt(airlineID);
        } catch (Exception e) {
            ShowMessage(true, "Check the Airline ID field and try again");
            return null;
        }

        String airActive = null;
        if (active != null) {
            if (active.equals("Yes")) {
                airActive = "Y";
            } else if (active.equals("No")) {
                airActive = "N";
            }
        }

        ArrayList<String> airline = new ArrayList<>(Arrays.asList(airlineID, name, alias, iata, icao, callsign, country, airActive));

        return airline;
    }

    /**
     * Function to check the validity of the route data inputted by the user.
     * Also checks that data is suitable for inserting into the database.
     * @param rouAir Airline
     * @param rouAirId Airline ID
     * @param rouSouAir Source airline
     * @param rouSouAirId Source airline ID
     * @param rouDesAir Destination airline
     * @param rouDesAirId Destination airline ID
     * @param rouCod Codeshare
     * @param rouStp Amount of stops
     * @param rouEqp Equipment
     * @return ArrayList of fields to be entered into database, or null if invalid input occurs
     */
    private ArrayList<String> CheckRoute(String rouAir, String rouAirId, String rouSouAir, String rouSouAirId, String rouDesAir,
                                         String rouDesAirId, String rouCod, double rouStp, String rouEqp) {

        try {  // Check if airline id input is valid
            Integer.parseInt(rouAirId);
        } catch (Exception e) {
            ShowMessage(true, "Check the Airline ID field and try again");
            return null;
        }

        try {  // Check if source airline id input is valid
            Integer.parseInt(rouSouAirId);
        } catch (Exception e) {
            ShowMessage(true, "Check the Source Airline ID field and try again");
            return null;
        }

        try {  // Check if destination airline id input is valid
            Integer.parseInt(rouDesAirId);
        } catch (Exception e) {
            ShowMessage(true, "Check the Destination Airline ID field and try again");
            return null;
        }

        String codeShare = null;  // Parse combo box result
        if (rouCod != null) {
            if (rouCod.equals("Yes")) {
                codeShare = "Y";
            } else if (rouCod.equals("No")) {
                codeShare = "N";
            }
        }

        String stops = String.valueOf(rouStp);

        ArrayList<String> route = new ArrayList<>(Arrays.asList(rouAir, rouAirId, rouSouAir, rouSouAirId, rouDesAir, rouDesAirId, codeShare, stops, rouEqp));
        return route;
    }

    /**
     * Clear all fields in window and set them to their default values.
     */
    @FXML
    public void ClearFields() {
        TextField[] textFields = {
                airIdField, airNameField, airAliasField, AirIataField, AirIcaoField, AirCallsignField, AirCountryField,
                airpId, airpName, airpCity, airpCountry, airpIata, airpIcao, airpLat, airpLon, airpAlt, airpTim,
                airpDst, rouAir, rouAirId, rouSouAir, rouSouAirId, rouDesAir, rouDesAirId, rouEqp
        };
        for (TextField textField : textFields) {
            textField.clear();
        }
        ComboBox[] comboBoxes = {
                AirActiveField, rouCod
        };
        for (ComboBox comboBox : comboBoxes) {
            comboBox.getSelectionModel().clearSelection();
        }

        rouStp.setValue(rouStp.getMin());
    }

    /**
     * Check the validity of the input and add a valid airport object to the database from the user input.
     */
    @FXML
    public void AddAirport() {
        ArrayList<String> airport = CheckAirport(airpId.getText(), airpName.getText(), airpCity.getText(), airpCountry.getText(),
                airpIata.getText(), airpIcao.getText(), airpLat.getText(), airpLon.getText(), airpAlt.getText(),
                airpTim.getText(), airpDst.getText());
        if (airport != null) {
            try {
                dataHandler.InsertAirport(airport);
                String message = String.format("Successfully added %d airline", ++airportsAdded);
                if (airportsAdded > 1) {
                    message += "s";
                }
                ShowMessage(false, message);
            } catch (SQLException e) {
                System.out.println(e.toString());
                ShowMessage(true, "There was a problem when saving the airport");
            }
        }
    }

    /**
     * Check the validity of the input and add a valid airline object to the database from the user input.
     */
    @FXML
    public void AddAirline() {
        ArrayList<String> airline = CheckAirline(airIdField.getText(), airNameField.getText(), airAliasField.getText(),
                AirIataField.getText(), AirIcaoField.getText(), AirCallsignField.getText(), AirCountryField.getText(),
                (String) AirActiveField.getValue());
        if (airline != null) {
            try {
                dataHandler.InsertAirline(airline);
                String message = String.format("Successfully added %d airline", ++airlinesAdded);
                if (airlinesAdded > 1) {
                    message += "s";
                }
                ShowMessage(false, message);
            } catch (SQLException e) {
                System.out.println(e.toString());

                ShowMessage(true, "There was a problem when saving the airline");
            }
        }
    }

    /**
     * Check the validity of the input and add a valid route object to the database from the user input.
     */
    @FXML
    public void AddRoute() {
        ArrayList<String> route = CheckRoute(rouAir.getText(), rouAirId.getText(), rouSouAir.getText(), rouSouAirId.getText(),
                rouDesAir.getText(), rouDesAirId.getText(), (String) rouCod.getValue(), (int) rouStp.getValue(),
                rouEqp.getText());
        if (route != null) {
            try {
                dataHandler.InsertRoute(route);
                String message = String.format("Successfully added %d route", ++routesAdded);
                if (routesAdded > 1) {
                    message += "s";
                }
                ShowMessage(false, message);
            } catch (SQLException e) {
                System.out.println(e.toString());

                ShowMessage(true, "There was a problem when saving the route");
            }
        }
    }
}
