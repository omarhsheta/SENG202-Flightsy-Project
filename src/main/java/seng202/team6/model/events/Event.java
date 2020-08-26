package seng202.team6.model.events;

public class Event {
    Integer Day;
    Integer Month;
    Integer Year;
    String Title;
    String Notes;

    Event(Integer D, Integer M, Integer Y, String T, String N) {
        Day = D;
        Month = M;
        Year = Y;
        Title = T;
        Notes = N;
    }
}
