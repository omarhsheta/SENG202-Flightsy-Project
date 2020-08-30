package seng202.team6.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import seng202.team6.gui.WindowHandler;
import seng202.team6.model.data.DataHandler;
import seng202.team6.model.entities.Airline;

import java.io.Console;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddRowController implements Initializable
{
    // Airline input fields
    @FXML
    private TextField airIdField;

    @FXML
    private TextField airNameField;

    @FXML
    private TextField airAliasField;

    @FXML
    private TextField AirIataField;

    @FXML
    private TextField AirIcaoField;

    @FXML
    private TextField AirCallsignField;

    @FXML
    private TextField AirCountryField;

    @FXML
    private ComboBox AirActiveField;


    @FXML
    private Text InfoText;

    private DataHandler dataHandler;

    private int successfullyAdded;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        dataHandler = DataHandler.GetInstance();
        successfullyAdded = 0;
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

    @FXML
    public void ClearFields() {
        airIdField.clear();
        airNameField.clear();
        airAliasField.clear();
        AirIataField.clear();
        AirIcaoField.clear();
        AirCallsignField.clear();
        AirCountryField.clear();
        AirActiveField.getSelectionModel().clearSelection();
    }

    @FXML
    public void AddAirline() {
        Airline airline = CheckAirline(airIdField.getText(), airNameField.getText(), airAliasField.getText(),
                AirIataField.getText(), AirIcaoField.getText(), AirCallsignField.getText(), AirCountryField.getText(),
                (String) AirActiveField.getValue());
        if (airline != null) {
            ArrayList<Airline> airlineArrayList = new ArrayList<>();
            airlineArrayList.add(airline);

            try {
                dataHandler.InsertAirlines(airlineArrayList);
                String message = String.format("Successfully added %d airline", ++successfullyAdded);;
                if (successfullyAdded > 1) {
                    message += "s";
                }
                ShowMessage(false, message);
            } catch (SQLException e) {
                ShowMessage(true, "There was a problem when saving the airline");
            }
        }
    }
}
