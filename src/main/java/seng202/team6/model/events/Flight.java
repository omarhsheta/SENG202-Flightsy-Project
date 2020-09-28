package seng202.team6.model.events;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import seng202.team6.gui.controller.holidayview.HolidayFlightController;
import seng202.team6.gui.helper.NodeHelper;
import seng202.team6.model.entities.Route;

import javax.lang.model.util.Elements;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * This class is for flight plans
 */
public class Flight extends Event {
    Route route;
    LocalDateTime arrivalDateTime;

    private final String subFolder = "holidayview";
    private final String holidayFlightComponent = "holidayflight";

    /**
     * Constructor for the Flight class
     * @param deptDay Any integer from 1 to 31
     * @param deptMonth Any integer from 1 to 12
     * @param deptYear Any integer from 2000 to 2099
     * @param destDay Any integer from 1 to 31
     * @param destMonth Any integer from 1 to 12
     * @param destYear Any integer from 2000 to 2099
     * @param T Any String with descriptive title
     * @param N Any String with additional information about the event
     * @param route Route of the flight
     */
    public Flight(int deptDay, int deptMonth, int deptYear, int deptHour, int deptMinute, int destDay, int destMonth, int destYear, int destHour, int destMinute, String T, String N, Route route) {
        super(deptDay, deptMonth, deptYear, deptHour, deptMinute, T, N);
        arrivalDateTime = LocalDateTime.of(destYear, destMonth, destDay, destHour, destMinute);
        this.route = route;
    }

    /**
     *
     * @return The route
     */
    public Route getRoute() {
        return route;
    }

    /**
     *
     * @return the arrival LocalDateTime
     */
    public LocalDateTime getArrivalDateTime() {
        return arrivalDateTime;
    }

    /**
     * A method that makes a Pane for the flight event
     * @return Pane object to be added to the holiday GUI
     */
    @Override
    public Pane toPane() {
        //String deptTime = String.format("%d: %d", super.getDateTime().getHour(), super.getDateTime().getMinute());
        //String destTime = String.format("%d: %d", arrivalDateTime.getHour(), arrivalDateTime.getMinute());


//        String deptDayOfWeek = super.getDateTime().getDayOfWeek().toString();
//        deptDayOfWeek = deptDayOfWeek.substring(0, 1) + deptDayOfWeek.substring(1).toLowerCase();
//        String deptMonth = super.getDateTime().getMonth().toString();
//        deptMonth = deptMonth.substring(0, 1) + deptMonth.substring(1).toLowerCase();
//        String deptDate = String.format("%s %d %s, %d", deptDayOfWeek, super.getDateTime().getDayOfMonth(), deptMonth, super.getDateTime().getYear());
        String deptTime = super.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        String deptDate = super.getDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyy"));

//        String destDayOfWeek = arrivalDateTime.getDayOfWeek().toString();
//        destDayOfWeek = destDayOfWeek.substring(0, 1) + destDayOfWeek.substring(1).toLowerCase();
//        String destMonth = arrivalDateTime.getMonth().toString();
//        destMonth = destMonth.substring(0, 1) + destMonth.substring(1).toLowerCase();
//        String destDate = String.format("%s %d %s, %d", destDayOfWeek, arrivalDateTime.getDayOfMonth(), destMonth, arrivalDateTime.getYear());
        String destTime = arrivalDateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        String destDate = arrivalDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyy"));


        Pane newFlightPane = null;
        try {
            Pair<Pane, HolidayFlightController> pair = NodeHelper.LoadNode(subFolder, holidayFlightComponent);
            newFlightPane = pair.getKey();
            HolidayFlightController flightController = pair.getValue();
            flightController.setData(deptTime, destTime, deptDate, destDate, this, route);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return newFlightPane;
    }
}
