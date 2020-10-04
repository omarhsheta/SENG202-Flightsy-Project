package seng202.team6.model.events;

import javafx.scene.layout.Pane;
import javafx.util.Pair;
import seng202.team6.gui.controller.holidayview.eventbuttons.FlightBtnController;
import seng202.team6.gui.helper.NodeHelper;
import seng202.team6.model.entities.Route;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class is for flight plans
 */
public class Flight extends Event {
    Route route;
    LocalDateTime arrivalDateTime;

    private final String subFolder = "holidayview/eventbuttons";
    private final String holidayFlightComponent = "FlightBtn";

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
     * @param deptHour Dept Hour
     * @param deptMinute Dept Minute
     * @param destHour Destination Hour
     * @param destMinute Dest minute
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
        String deptTime = super.getDateTime().format(DateTimeFormatter.ofPattern("hh:mm a"));
        String deptDate = super.getDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyy"));

        String destTime = arrivalDateTime.format(DateTimeFormatter.ofPattern("hh:mm a"));

        Pane newFlightPane = null;
        try {
            Pair<Pane, FlightBtnController> pair = NodeHelper.LoadNode(subFolder, holidayFlightComponent);
            newFlightPane = pair.getKey();
            FlightBtnController flightController = pair.getValue();
            flightController.setData(deptTime, deptDate, destTime, this, route);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return newFlightPane;
    }
}
