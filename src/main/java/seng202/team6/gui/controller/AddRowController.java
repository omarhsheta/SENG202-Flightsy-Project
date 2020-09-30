package seng202.team6.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import seng202.team6.model.data.AddRowHandler;
import seng202.team6.model.data.DataImportHandler;
import seng202.team6.model.entities.Airline;
import seng202.team6.model.entities.Airport;
import seng202.team6.model.entities.Route;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

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

    private DataImportHandler dataImport;

    private AddRowHandler addRowHandler;

    private int airlinesAdded, airportsAdded, routesAdded;

    /**
     * Initialise the pop up window, fetch datahandler instance and set counters.
     * @param url Windows URL
     * @param resourceBundle Windows resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        dataImport = DataImportHandler.GetInstance();
        addRowHandler = new AddRowHandler();
        airlinesAdded = airportsAdded = routesAdded = 0;
    }

    /**
     * Helper function to show/modify the existing message label.
     * Can show red error text or green success text.
     * @param error When set to true message appears in red fill, green when false.
     * @param message Passed in message to display to the user.
     */
    public void ShowMessage(boolean error, String message) {
        if (error) {
            InfoText.setFill(Paint.valueOf("Red"));
        } else {
            InfoText.setFill(Paint.valueOf("Green"));
        }
        InfoText.setText(message);
        InfoText.setVisible(true);
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
     * Check the validity of the input and add a valid airline object to the database from the user input.
     */
    @FXML
    public void AddAirline() {
//        ArrayList<String> fields = addRowHandler.CheckAirline(this, airIdField.getText(),
//                airNameField.getText(), airAliasField.getText(), AirIataField.getText(), AirIcaoField.getText(),
//                AirCallsignField.getText(), AirCountryField.getText(), (String) AirActiveField.getValue());

        ArrayList<String> fields = new ArrayList<>(Arrays.asList(airIdField.getText(), airNameField.getText(),
                airAliasField.getText(), AirIataField.getText(), AirIcaoField.getText(), AirCallsignField.getText(),
                AirCountryField.getText(), (String) AirActiveField.getValue()));

        String errorMessage = addRowHandler.CheckAirline(fields);

        if (errorMessage != null) {
            ShowMessage(true, errorMessage);
        } else {
            Airline airline = addRowHandler.CreateAirline(fields);
            try {
                dataImport.InsertAirline(airline);
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
     * Check the validity of the input and add a valid airport object to the database from the user input.
     */
    @FXML
    public void AddAirport() {
        ArrayList<String> fields = new ArrayList<>(Arrays.asList(airpId.getText(), airpName.getText(),
                airpCity.getText(), airpCountry.getText(), airpIata.getText(), airpIcao.getText(), airpLat.getText(),

                airpLon.getText(), airpAlt.getText(), airpTim.getText(), airpDst.getText()));
        String errorMessage = addRowHandler.CheckAirport(fields);

        if (errorMessage != null) {
            ShowMessage(true, errorMessage);
        } else {
            Airport airport = addRowHandler.CreateAirport(fields);
            try {
                dataImport.InsertAirport(airport);
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
     * Check the validity of the input and add a valid route object to the database from the user input.
     */
    @FXML
    public void AddRoute() {
//        ArrayList<String> fields = addRowHandler.CheckRoute(this, rouAir.getText(), rouAirId.getText(),
//                rouSouAir.getText(), rouSouAirId.getText(), rouDesAir.getText(), rouDesAirId.getText(),
//                (String) rouCod.getValue(), (int) rouStp.getValue(), rouEqp.getText());

        ArrayList<String> fields = new ArrayList<>(Arrays.asList(rouAir.getText(), rouAirId.getText(),
                rouSouAir.getText(), rouSouAirId.getText(), rouDesAir.getText(), rouDesAirId.getText(),
                (String) rouCod.getValue(), String.valueOf((int)(rouStp.getValue())), rouEqp.getText()));

        String errorMessage = addRowHandler.CheckRoute(fields);

        if (errorMessage != null) {
            ShowMessage(true, errorMessage);
        } else {
            Route route = addRowHandler.CreateRoute(fields);
            try {
                dataImport.InsertRoute(route);
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
