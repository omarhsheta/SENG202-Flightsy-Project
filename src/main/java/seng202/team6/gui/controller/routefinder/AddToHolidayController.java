package seng202.team6.gui.controller.routefinder;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;
import seng202.team6.gui.controller.HolidayAgendaController;
import seng202.team6.gui.controller.holidayview.HolidayFlightController;
import seng202.team6.gui.helper.NodeHelper;
import seng202.team6.model.entities.Route;
import seng202.team6.model.events.Flight;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;


public class AddToHolidayController {

    @FXML
    DatePicker departureDatePicker;
    @FXML
    DatePicker arrivalDatePicker;

    @FXML
    TextField deptDay;
    @FXML
    TextField deptMonth;
    @FXML
    TextField deptYear;

    @FXML
    TextField arrivalDay;
    @FXML
    TextField arrivalMonth;
    @FXML
    TextField arrivalYear;

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


    private Route route;

    private Stage stage;

    private final String subFolder = "holidayview";
    private final String holidayFlightComponent = "holidayflight";


    /**
     * Sets the variable route to the Route object supplied
     * @param route Route object supplied
     */
    public void setRoute(Route route) {
        this.route = route;
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
        String title = String.format("%s to %s", route.getSourceAirport(), route.getDestinationAirport());
        String notes = "";

        Flight newFlight = new Flight(Integer.parseInt(deptDay.getText()), Integer.parseInt(deptMonth.getText()),
                Integer.parseInt(deptYear.getText()), Integer.parseInt(deptHour.getText()), Integer.parseInt(deptMinute.getText()),
                Integer.parseInt(arrivalDay.getText()), Integer.parseInt(arrivalMonth.getText()), Integer.parseInt(arrivalYear.getText()),
                Integer.parseInt(arrivalHour.getText()), Integer.parseInt(arrivalMinute.getText()), title, notes, route);


        HolidayAgendaController.GetInstance().addFlightToHoliday(newFlight);


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
