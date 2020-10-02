package seng202.team6.model.events;
import javafx.scene.layout.Pane;

import java.time.*;

/**
 * The Abstract class event has three subclasses; General, Flight, and CarTrip
 */
public abstract class Event {
    protected LocalDateTime dateTime;
    protected String Title;
    protected String Notes;

    /**
     * Constructor of the abstract class Event
     * @param D Any integer from 1 to 31
     * @param M Any integer from 1 to 12
     * @param Y Any integer from 2000 to 2099
     * @param T Any String with descriptive title
     * @param N Any String with additional information about the event
     */
    Event(int D, int M, int Y, int newHour, int newMinute, String T, String N) {
        dateTime = LocalDateTime.of(Y, M, D, newHour, newMinute);
        Title = T;
        Notes = N;
    }

    /**
     *
     * @return The DateTime of the event
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * A method that makes a Pane for the event
     * This method is only a filler as the children classes should all have @Override'ed this method
     * @return Pane object to be added to the holiday GUI
     */
    public Pane toPane() {
        //Implimented in children classes
        Pane newPane = null;
        return newPane;
    }
}
