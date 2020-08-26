package seng202.team6.model.events;

public class Event {
    int Day;
    int Month;
    int Year;
    String Title;
    String Notes;

    Event(int D, int M, int Y, String T, String N) {
        Day = D;
        Month = M;
        Year = Y;
        Title = T;
        Notes = N;
    }
}
