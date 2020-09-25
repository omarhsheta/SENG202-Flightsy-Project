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

    private HolidayAgendaController holidayController = HolidayAgendaController.GetInstance();

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
        //System.out.println(String.format("Departure time %s: %s\nArrival time %s: %s", deptHour.getText(), deptMinute.getText(), arrivalHour.getText(), arrivalMinute.getText()));

        Pane newFlight;
        Time deptTime;
        //deptTime.setTime();

        //NOT WORKING YET
        /*
        LocalDate deptDate = departureDatePicker.getValue();



        Calendar deptCalendar = Calendar.getInstance();
        deptCalendar.set(Calendar.YEAR, deptDate.getYear());
        deptCalendar.set(Calendar.MONTH, deptDate.getMonthValue());
        deptCalendar.set(Calendar.DATE, deptDate.getDayOfMonth());
        deptCalendar.set(Calendar.HOUR, Integer.parseInt(deptHour.getText()));
        deptCalendar.set(Calendar.MINUTE, Integer.parseInt(deptMinute.getText()));
        deptCalendar.set(Calendar.SECOND, 0);
        deptCalendar.set(Calendar.MILLISECOND, 0);

        Date currTimezoneDeptDate = Date.from(deptDate.atTime(Integer.parseInt(deptHour.getText()), Integer.parseInt(deptMinute.getText())).toInstant());

        //deptDate = deptCalendar.getTime();


        LocalDate arrivalDate = arrivalDatePicker.getValue();

        Calendar c1 = Calendar.getInstance();
        c1.set(Calendar.HOUR, 22);
        */
        try {
            Pair<Pane, HolidayFlightController> pair = NodeHelper.LoadNode(subFolder, holidayFlightComponent);
            newFlight = pair.getKey();
            HolidayFlightController flightController = pair.getValue();
            flightController.setRoute(route);
            //flightController.setData();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Add to holiday here
        //holidayController.addToHoliday();


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
