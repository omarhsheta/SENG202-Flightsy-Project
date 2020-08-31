package seng202.team6.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import seng202.team6.gui.WindowHandler;
import seng202.team6.model.data.DataHandler;
import seng202.team6.model.entities.Airline;
import seng202.team6.model.entities.Airport;
import seng202.team6.model.entities.Route;

import java.io.Console;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;

public class AddRowController implements Initializable
{
    // Airline input fields
    @FXML
    private TextField airIdField, airNameField, airAliasField, AirIataField, AirIcaoField, AirCallsignField,
            AirCountryField;

    @FXML
    private ComboBox AirActiveField;

    // Airport input fields
    @FXML
    private TextField airpId, airpName, airpCity, airpCountry, airpIata, airpIcao, airpLat, airpLon, airpAlt, airpTim,
            airpDst;

    // Route input fields
    @FXML
    private TextField rouAir, rouAirId, rouSouAir, rouSouAirId, rouDesAir, rouDesAirId, rouEqp;

    @FXML
    private ComboBox rouCod;

    @FXML
    private Slider rouStp;


    @FXML
    private Text InfoText;

    private DataHandler dataHandler;

    private int airlinesAdded, airportsAdded, routesAdded;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        dataHandler = DataHandler.GetInstance();
        airlinesAdded = airportsAdded = routesAdded = 0;
    }

    private void ShowMessage(boolean error, String message) {
        if (error) {
            InfoText.setFill(Paint.valueOf("Red"));
        } else {
            InfoText.setFill(Paint.valueOf("Green"));
        }
        InfoText.setText(message);
        InfoText.setVisible(true);
    }

    private Airport CheckAirport(String airpId, String airpName, String airpCity, String airpCountry, String airpIata,
                                 String airpIcao, String airpLat, String airpLon, String airpAlt, String airpTim,
                                 String airpDst) {
        Airport airport = null;

        int airportId;
        try {  // Check if airport id input is valid
            airportId = Integer.parseInt(airpId);
        } catch (Exception e) {
            ShowMessage(true, "Check the Airport ID field and try again");
            return airport;
        }

        Float latitude = null;
        if (airpLat != "") {
            try {  // Check if latitude input is valid
                latitude = Float.parseFloat(airpLat);
            } catch (Exception e) {
                ShowMessage(true, "Check the latitude field and try again");
                return airport;
            }
        }

        Float longitude = null;
        if (airpLon != "") {
            try {  // Check if latitude and longitude inputs are valid
                longitude = Float.parseFloat(airpLon);
            } catch (Exception e) {
                ShowMessage(true, "Check the longitude field and try again");
                return airport;
            }
        }

        Integer altitude = null;
        if (airpAlt != "") {
            try {  // Check if altitude input is valid
                altitude = Integer.parseInt(airpAlt);
            } catch (Exception e) {
                ShowMessage(true, "Check the Altitude field and try again");
                return airport;
            }
        }

        Integer timeZone = null;
        if (airpTim != "") {
            try {  // Check if the time zone input is valid
                timeZone = Integer.parseInt(airpTim);
            } catch (Exception e) {
                ShowMessage(true, "Check the Time Zone field and try again");
                return airport;
            }
        }

        char dst;
        Set<String> validValues = Set.of("e", "a", "s", "o", "z", "n");
        if (validValues.contains(airpDst.toLowerCase())) {
            dst = airpDst.toUpperCase().charAt(0);
        } else {
            dst = 'U';  // Unknown Daylight savings value
        }

        airport = new Airport(airportId, airpName, airpCity, airpCountry, airpIata, airpIcao, latitude, longitude,
                altitude, timeZone, dst);
        return airport;
    }

    private Airline CheckAirline(String airlineID, String name, String alias, String iata, String icao,
                                 String callsign, String country, String active) {
        Airline airline = null;

        int airId;
        try {  // Check if airline id input is valid
            airId = Integer.parseInt(airlineID);
        } catch (Exception e) {
            ShowMessage(true, "Check the Airline ID field and try again");
            return airline;
        }

        Character airActive = null;  // Parse combo box result
        if (active != null) {
            if (active.equals("Yes")) {
                airActive = 'Y';
            } else if (active.equals("No")) {
                airActive = 'N';
            }
        }

        airline = new Airline(airId, name, alias, iata, icao, callsign, country, airActive);
        return airline;
    }

    private Route CheckRoute(String rouAir, String rouAirId, String rouSouAir, String rouSouAirId, String rouDesAir,
                             String rouDesAirId, String rouCod, int rouStp, String rouEqp) {
        Route route = null;

        int airId;
        try {  // Check if airline id input is valid
            airId = Integer.parseInt(rouAirId);
        } catch (Exception e) {
            ShowMessage(true, "Check the Airline ID field and try again");
            return route;
        }

        int srcAirId;
        try {  // Check if source airline id input is valid
            srcAirId = Integer.parseInt(rouSouAirId);
        } catch (Exception e) {
            ShowMessage(true, "Check the Source Airline ID field and try again");
            return route;
        }

        int desAirId;
        try {  // Check if destination airline id input is valid
            desAirId = Integer.parseInt(rouDesAirId);
        } catch (Exception e) {
            ShowMessage(true, "Check the Destination Airline ID field and try again");
            return route;
        }

        Character codeShare = null;  // Parse combo box result
        if (rouCod != null) {
            if (rouCod.equals("Yes")) {
                codeShare = 'Y';
            } else if (rouCod.equals("No")) {
                codeShare = 'N';
            }
        }

        route = new Route(airId, rouAir, rouSouAir, srcAirId, rouDesAir, desAirId, codeShare, rouStp, rouEqp);
        return route;
    }

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

    @FXML
    public void AddAirport() {
        Airport airport = CheckAirport(airpId.getText(), airpName.getText(), airpCity.getText(), airpCountry.getText(),
                airpIata.getText(), airpIcao.getText(), airpLat.getText(), airpLon.getText(), airpAlt.getText(),
                airpTim.getText(), airpDst.getText());
        if (airport != null) {
            ArrayList<Airport> airportArrayList = new ArrayList<>() {{
                add(airport);
            }};

            try {
                dataHandler.InsertAirports(airportArrayList);
                String message = String.format("Successfully added %d airline", ++airportsAdded);
                if (airportsAdded > 1) {
                    message += "s";
                }
                ShowMessage(false, message);
            } catch (SQLException e) {
                ShowMessage(true, "There was a problem when saving the airport");
            }
        }
    }

    @FXML
    public void AddAirline() {
        Airline airline = CheckAirline(airIdField.getText(), airNameField.getText(), airAliasField.getText(),
                AirIataField.getText(), AirIcaoField.getText(), AirCallsignField.getText(), AirCountryField.getText(),
                (String) AirActiveField.getValue());
        if (airline != null) {
            ArrayList<Airline> airlineArrayList = new ArrayList<>() {{
                add(airline);
            }};

            try {
                dataHandler.InsertAirlines(airlineArrayList);
                String message = String.format("Successfully added %d airline", ++airlinesAdded);
                if (airlinesAdded > 1) {
                    message += "s";
                }
                ShowMessage(false, message);
            } catch (SQLException e) {
                ShowMessage(true, "There was a problem when saving the airline");
            }
        }
    }

    @FXML
    public void AddRoute() {
        Route route = CheckRoute(rouAir.getText(), rouAirId.getText(), rouSouAir.getText(), rouSouAirId.getText(),
                rouDesAir.getText(), rouDesAirId.getText(), (String) rouCod.getValue(), (int) rouStp.getValue(),
                rouEqp.getText());
        if (route != null) {
            ArrayList<Route> routeArrayList = new ArrayList<>() {{
                add(route);
            }};

            try {
                dataHandler.InsertRoutes(routeArrayList);
                String message = String.format("Successfully added %d route", ++routesAdded);
                if (routesAdded > 1) {
                    message += "s";
                }
                ShowMessage(false, message);
            } catch (SQLException e) {
                ShowMessage(true, "There was a problem when saving the route");
            }
        }
    }
}
