package seng202.team6.model.events;
import java.time.*;

/**
 * The Abstract class event has three subclasses; General, Flight, and CarTrip
 */
public abstract class Event {
    LocalDate date;
    int Hour;
    int Minute;
    String Title;
    String Notes;

    /**
     * Constructor of the abstract class Event
     * @param D Any integer from 1 to 31
     * @param M Any integer from 1 to 12
     * @param Y Any integer from 2000 to 2099
     * @param T Any String with descriptive title
     * @param N Any String with additional information about the event
     */
    Event(int D, int M, int Y, int newHour, int newMinute, String T, String N) {
        date = LocalDate.of(Y, M, D);
        Title = T;
        Notes = N;
    }

    /**
     *
     * @return The int value of the day of the month
     */
    public int getDay() {
        return date.getDayOfMonth();
    }

    /**
     *
     * @return The int value of the month (Anything from 1 to 12 inclusive)
     */
    public int getMonth() {
        return date.getMonthValue();
    }

    /**
     *
     * @return The int value of the year
     */
    public int getYear() {
        return date.getYear();
    }
}
