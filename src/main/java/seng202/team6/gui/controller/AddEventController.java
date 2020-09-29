package seng202.team6.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import seng202.team6.model.events.CarTrip;
import seng202.team6.model.events.General;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class AddEventController implements Initializable {
    @FXML
    private TextField
        // Drive Event fields
        DTit, DCityFrom, DCityTo, DCountFrom, DCountTo, DTimeH, DTimeM,
        // General Event fields
        GTit, GTimeH, GTimeM, GCity, GCount;

    @FXML
    private TextArea DNote, GNote;

    @FXML
    private DatePicker DDate, GDate;

    /**
     * Clear all fields in window and set them to their default values.
     */
    @FXML
    public void ClearFields() {
        TextField[] textFields = {DTit, DCityFrom, DCityTo, DCountFrom, DCountTo, DTimeH, DTimeM, GTit, GTimeH, GTimeM};
        for (TextField textField : textFields) {
            textField.clear();
        }

        TextArea[] textAreas = {DNote, GNote};
        for (TextArea textArea : textAreas) {
            textArea.clear();
        }
    }

    /**
     * Method to check the input values for the new car trip event.
     * Will use the current date and time if no values are passed.
     */
    private CarTrip CheckCarTrip() {
        Date date = new Date(); // Get current date as default
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        LocalDate localDate = DDate.getValue();

        if (localDate != null) {
            day = localDate.getDayOfMonth();
            month = localDate.getMonthValue();
            year = localDate.getYear();
        }

        try {  // Check if hour input is valid
            int newHour = Integer.parseInt(DTimeH.getText());
            if (0 <= newHour && newHour <= 23) {
                hour = newHour;
            }
        } catch (Exception e) {
            // Hour is set by default
        }

        try {  // Check if minute input is valid
            int newMinute = Integer.parseInt(DTimeM.getText());
            if (0 <= newMinute && newMinute <= 59) {
                minute = newMinute;
            }
        } catch (Exception e) {
            // minute is set by default
        }

        return new CarTrip(day, month, year, hour, minute, DTit.getText(), DNote.getText(), DCityFrom.getText(),
                DCountFrom.getText(), DCityTo.getText(), DCountTo.getText());
    }

    /**
     * Method to check the input values for the new general event.
     * Will use the current date and time if no values are passed.
     */
    private General CheckGeneralEvent() {
        Date date = new Date(); // Get current date as default
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        LocalDate localDate = GDate.getValue();

        if (localDate != null) {
            day = localDate.getDayOfMonth();
            month = localDate.getMonthValue();
            year = localDate.getYear();
        }

        try {  // Check if hour input is valid
            int newHour = Integer.parseInt(DTimeH.getText());
            if (0 <= newHour && newHour <= 23) {
                hour = newHour;
            }
        } catch (Exception e) {
            // Hour is set by default
        }

        try {  // Check if minute input is valid
            int newMinute = Integer.parseInt(DTimeM.getText());
            if (0 <= newMinute && newMinute <= 59) {
                minute = newMinute;
            }
        } catch (Exception e) {
            // minute is set by default
        }

        return new General(day, month, year, hour, minute, GTit.getText(), GNote.getText(), GCity.getText(),
                GCount.getText());
    }

    /**
     * Check the validity of the input and add a valid general event object to the holiday object from the user input.
     */
    @FXML
    public void AddGeneral() {
        General generalEvent = CheckGeneralEvent();
        HolidayAgendaController.GetInstance().addItineraryToHoliday(generalEvent);
    }

    /**
     * Check the validity of the input and add a valid drive event object to the holiday object from the user input.
     */
    @FXML
    public void AddCarTrip() {
        CarTrip carTrip = CheckCarTrip();
        HolidayAgendaController.GetInstance().addCarTripToHoliday(carTrip);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}