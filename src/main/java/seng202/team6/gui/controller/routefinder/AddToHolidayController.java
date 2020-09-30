package seng202.team6.gui.controller.routefinder;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.ChoiceBox;
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
import seng202.team6.model.user.HolidayHelper;

import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;


public class AddToHolidayController implements Initializable {

    @FXML
    DatePicker departureDatePicker;
    @FXML
    DatePicker arrivalDatePicker;

    @FXML
    private ChoiceBox<String> holidayChoiceBox = new ChoiceBox<>();

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

    /**
     * Sets the Stage variable stage to the supplied Stage.
     * @param newStage Stage object supplied
     */
    public void setStage(Stage newStage) {
        stage = newStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HolidayAgendaController controller = HolidayAgendaController.GetInstance();
        holidayChoiceBox.itemsProperty().bindBidirectional(controller.GetHolidaySelectChoiceBox().itemsProperty());
        holidayChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) ->
                        controller.ChangeHoliday(holidayChoiceBox.getSelectionModel().getSelectedIndex()));
    }

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
     * Called when the user wants to add the route to their holiday.
     */
    @FXML
    private void OnAddToHolidayButtonClicked() {
        String title = String.format("%s to %s", route.getSourceAirport(), route.getDestinationAirport());
        String notes = "";

        int dDay, dMonth, dYear, dHour, dMinute;
        int aDay, aMonth, aYear, aHour, aMinute;

        try {
            dDay = Integer.parseInt(deptDay.getText());
            dMonth = Integer.parseInt(deptMonth.getText());
            dYear = Integer.parseInt(deptYear.getText());
            dHour = Integer.parseInt(deptHour.getText());
            dMinute = Integer.parseInt(deptMinute.getText());

            aDay = Integer.parseInt(arrivalDay.getText());
            aMonth = Integer.parseInt(arrivalMonth.getText());
            aYear = Integer.parseInt(arrivalYear.getText());
            aHour = Integer.parseInt(arrivalHour.getText());
            aMinute = Integer.parseInt(arrivalMinute.getText());
        } catch (Exception e) {
            ShowError("Invalid input in one or more textboxes.");
            return;
        }

        if (!(HolidayHelper.IsValidDate(dDay, dMonth, dYear, dHour, dMinute)
                && HolidayHelper.IsValidDate(aDay, aMonth, aYear, aHour, aMinute))) {
            ShowError("Please check the dates, One or both of the dates are invalid.");
            return;
        }

        LocalDateTime arrivalDateTime = LocalDateTime.of(aYear, aMonth, aDay, aHour, aMinute);
        LocalDateTime deptDateTime = LocalDateTime.of(dYear, dMonth, dDay, dHour, dMinute);

        if (arrivalDateTime.isBefore(deptDateTime)) {
            ShowError("Please input an arrival date after the departure date");
            return;
        }

        Flight newFlight = new Flight(dDay, dMonth, dYear, dHour,
                                        dMinute, aDay, aMonth, aYear, aHour,
                                        aMinute, title, notes, route);

        HolidayAgendaController.GetInstance().AddToHoliday(newFlight);
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
