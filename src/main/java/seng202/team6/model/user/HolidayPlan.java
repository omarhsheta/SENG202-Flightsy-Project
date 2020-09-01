package seng202.team6.model.user;

import java.lang.Object;
import seng202.team6.model.events.Event;
import seng202.team6.model.events.Flight;
import seng202.team6.model.events.General;

import java.util.ArrayList;

public class HolidayPlan {
    String Name; //No more than 25 chars
    private ArrayList<General> Itineraries;
    private ArrayList<Flight> Flights; //Should not exceed 30

    /**
     * This method should be called when the user is adding a new flight to their HolidayPlan.
     * It should also check if it is below 30 before appending the flight to the array, and it should
     * check that the flight that is about to be appended is not in the array.
     * @param Flight The flight that is to be added to the array Flights
     */
    public void FlightAppend(Flight Flight) {
    }

    /**
     * This method should be called when the user wants to append itineraries to their holiday plan
     * @param General The itinerary that is to be added to the array Itineraries
     * @param D Any integer from 1 to 31
     * @param M Any integer from 1 to 12
     * @param Y Any integer from 2000 to 2099
     * @param T Any String with descriptive title
     * @param N Any String with additional information about the event
     * @param nCity The city where the general event is taking place
     * @param nCountry The country where the general event is taking place
     */
    public void ItineraryAppend(General General, int D, int M, int Y, String T, String N, String nCity, String nCountry) {
        General = new General(D, M, Y, T, N, nCity, nCountry);
        Itineraries.add(General);
    }

}
