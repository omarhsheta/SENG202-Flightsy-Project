package seng202.team6.model.events;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import seng202.team6.gui.controller.holidayview.HolidayFlightController;
import seng202.team6.gui.helper.NodeHelper;
import seng202.team6.model.entities.Route;

import javax.lang.model.util.Elements;
import java.time.LocalDate;

/**
 * This class is for flight plans
 */
public class Flight extends Event {
    Route route;
    LocalDate arrivalDate;
    int arrivalHour;
    int arrivalMinute;

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
        arrivalDate = LocalDate.of(destYear, destMonth, destDay);
        arrivalHour = destHour;
        arrivalMinute = destMinute;
        this.route = route;
        System.out.println(String.format("%s", super.Title));
    }

    /**
     *
     * @return The route
     */
    public Route getRoute() {
        return route;
    }

    public Pane toPane() {
        String deptTime = String.format("%d: %d", super.Hour, super.Minute);
        String destTime = String.format("%d: %d", arrivalHour, arrivalMinute);
        Pane newFlightPane = null;
        try {
            Pair<Pane, HolidayFlightController> pair = NodeHelper.LoadNode(subFolder, holidayFlightComponent);
            newFlightPane = pair.getKey();
            HolidayFlightController flightController = pair.getValue();
            flightController.setRoute(route);
            flightController.setData(deptTime, destTime, super.date, arrivalDate);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return newFlightPane;
    }
}
