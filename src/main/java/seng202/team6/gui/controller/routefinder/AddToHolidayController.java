package seng202.team6.gui.controller.routefinder;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import seng202.team6.gui.controller.HolidayAgendaController;
import seng202.team6.gui.controller.holidayview.HolidayFlightController;
import seng202.team6.gui.helper.NodeHelper;
import seng202.team6.model.entities.Route;
import seng202.team6.model.events.Flight;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @FXML
    Text infoText;


    private Route route;

    private Stage stage;

    private final String subFolder = "holidayview";
    private final String holidayFlightComponent = "holidayflight";

    private void ShowError(String message) {
        infoText.setFill(Paint.valueOf("Red"));
        infoText.setText(message);
        infoText.setVisible(true);
    }


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

        if (ErrorCheck()) {
            Flight newFlight = new Flight(Integer.parseInt(deptDay.getText()), Integer.parseInt(deptMonth.getText()),
                    Integer.parseInt(deptYear.getText()), Integer.parseInt(deptHour.getText()), Integer.parseInt(deptMinute.getText()),
                    Integer.parseInt(arrivalDay.getText()), Integer.parseInt(arrivalMonth.getText()), Integer.parseInt(arrivalYear.getText()),
                    Integer.parseInt(arrivalHour.getText()), Integer.parseInt(arrivalMinute.getText()), title, notes, route);


            HolidayAgendaController.GetInstance().addFlightToHoliday(newFlight);


            stage.close();
        }
    }

    private boolean ErrorCheck() {
        int day;
        int month;
        int year;
        int hour;
        int minute;

        /* Check if a holiday is selected */
        int numHolidays = HolidayAgendaController.GetInstance().getHolidays().size();
        int currHolidayIndex = HolidayAgendaController.GetInstance().getSelectedHolidayIndex();
        if (currHolidayIndex == numHolidays || currHolidayIndex == -1) { // If "New Holiday" or "" is selected
            ShowError("Please select a holiday and try again");
            return false;
        }

        /* Departure Date */
        try {
            day = Integer.parseInt(deptDay.getText());
            if (day > 31 || day < 1) {
                throw new Exception();
            }
            month = Integer.parseInt(deptMonth.getText());
            if (month > 12 || month < 1) {
                throw new Exception();
            }
            year = Integer.parseInt(deptYear.getText());
        } catch (Exception e) {
            ShowError("Please check the departure date and try again");
            return false;
        }

        /* Departure Time */
        try {
            hour = Integer.parseInt(deptHour.getText());
            if (hour > 24 || hour < 1) {
                throw new Exception();
            }
            minute = Integer.parseInt(deptMinute.getText());
            if (minute > 60 || minute < 1) {
                throw new Exception();
            }
        } catch (Exception e) {
            ShowError("Please check the departure time and try again");
            return false;
        }

        LocalDateTime deptDateTime = LocalDateTime.of(year, month, day, hour, minute);

        /* Arrival Date */
        try {
            day = Integer.parseInt(arrivalDay.getText());
            if (day > 31 || day < 1) {
                throw new Exception();
            }
            month = Integer.parseInt(arrivalMonth.getText());
            if (month > 12 || month < 1) {
                throw new Exception();
            }
            year = Integer.parseInt(arrivalYear.getText());
        } catch (Exception e) {
            ShowError("Please check the arrival date and try again");
            return false;
        }

        /* Arrival Time */
        try {
            hour = Integer.parseInt(arrivalHour.getText());
            if (hour > 24 || hour < 1) {
                throw new Exception();
            }
            minute = Integer.parseInt(arrivalMinute.getText());
            if (minute > 60 || minute < 1) {
                throw new Exception();
            }
        } catch (Exception e) {
            ShowError("Please check the arrival time and try again");
            return false;
        }

        LocalDateTime arrivalDateTime = LocalDateTime.of(year, month, day, hour, minute);

        /* Check if arrival date and time is after departure date and time */
        if (arrivalDateTime.isBefore(deptDateTime)) {
            ShowError("Please input an arrival date after the departure date");
            return false;
        }
        return true;
    }



    /**
     * Called when the user wants to close the window, calls stage.close().
     */
    @FXML
    private void closeWindow() {
        stage.close();
    }
}
