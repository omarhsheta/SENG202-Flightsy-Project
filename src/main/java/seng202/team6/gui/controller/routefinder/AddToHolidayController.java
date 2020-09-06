package seng202.team6.gui.controller.routefinder;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seng202.team6.model.entities.Route;


public class AddToHolidayController {

    @FXML
    DatePicker departureDatePicker;
    @FXML
    DatePicker arrivalDatePicker;
    @FXML
    TextField deptHour;
    @FXML
    TextField deptMinute;
    @FXML
    TextField arrivalHour;
    @FXML
    TextField arrivalMinute;


    @FXML
    Button addToHolidayButton;


    private Route routeToAdd;

    private Stage stage;


    /**
     * Sets the variable route to the Route object supplied
     * @param route Route object supplied
     */
    public void setRoute(Route route) {
        routeToAdd = route;
    }

    /**
     * Sets the Stage variable stage to the supplied Stage.
     * @param newStage Stage object supplied
     */
    public void setStage(Stage newStage) {
        stage = newStage;
    }

    /**
     * Called when the user wants to add the route to their holiday.
     */
    @FXML
    private void OnAddToHolidayButtonClicked() {
        //System.out.println(String.format("Departure time %s: %s\nArrival time %s: %s", deptHour.getText(), deptMinute.getText(), arrivalHour.getText(), arrivalMinute.getText()));


        // Add to holiday here


        stage.close();
    }

    /**
     * Called when the user wants to close the window, calls stage.close().
     */
    @FXML
    private void closeWindow() {
        stage.close();
    }
}
