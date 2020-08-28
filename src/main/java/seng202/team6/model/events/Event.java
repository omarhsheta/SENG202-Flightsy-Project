package seng202.team6.model.events;
import java.time.*;

/**
 * The Abstract class event has three subclasses; General, Flight, and CarTrip
 */
public abstract class Event {
    int Day;
    int Month;
    int Year;
    String Title;
    String Notes;

    /**
     * Initializes the superclass Event
     * @param D Any integer from 1 to 31
     * @param M Any integer from 1 to 12
     * @param Y Any integer from 2000 to 2099
     * @param T Any String with descriptive title
     * @param N Any String with additional information about the event
     */
    Event(int D, int M, int Y, String T, String N) {
        Day = D;
        Month = M;
        Year = Y;
        Title = T;
        Notes = N;
    }
}
