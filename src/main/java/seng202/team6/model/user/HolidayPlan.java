package seng202.team6.model.user;

import com.google.gson.Gson;
import seng202.team6.model.data.DataHandler;
import seng202.team6.model.data.DataImportHandler;
import seng202.team6.model.events.CarTrip;
import seng202.team6.model.events.Flight;
import seng202.team6.model.events.General;
import seng202.team6.model.interfaces.JSONSerializable;

import java.util.ArrayList;

public class HolidayPlan implements JSONSerializable {
    String name; //No more than 25 chars
    private ArrayList<General> itineraries = new ArrayList<>();
    private ArrayList<Flight> flights = new ArrayList<>(); //Should not exceed 30
    private ArrayList<CarTrip> carTrips = new ArrayList<>();

    private boolean isInDatabase = false;

    /**
     * Constructor for the holiday plan
     * @param newName the name of the new HolidayPlan
     */
    public HolidayPlan(String newName) {
        name = newName.replaceAll("[^a-zA-Z0-9 ]", "");
    }

    /**
     * Save holiday into database
     */
    public void SaveHoliday() {
        String filename = name + ".json";
        try {
            if (!isInDatabase) {
                DataImportHandler.GetInstance().InsertHolidayPlan(name, filename);
                isInDatabase = true;
            }
            DataHandler.GetInstance().WriteDataFile(filename, this.ToJson());
        } catch (Exception e) {
            System.out.println("Failed to save new holiday. \n" + e.toString());
        }
    }

    /**
     * Constructor for turning object from JSON
     * @param json JSON String
     * @return HolidayPlan object
     */
    public static HolidayPlan FromJSON(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, HolidayPlan.class);
    }

    /**
     * Turn object into JSON Representation
     * @return String JSON String
     */
    @Override
    public String ToJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
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
     * @param newHour Hour
     * @param newMinute Minute
     */
    public void ItineraryAppend(General General, int D, int M, int Y, int newHour, int newMinute, String T, String N, String nCity, String nCountry) {
        General = new General(D, M, Y, newHour, newMinute, T, N, nCity, nCountry);
        itineraries.add(General);
    }

    /**
     *
     * @return the holiday name
     */
    public String GetName() {
        return name;
    }

    /**
     *
     * @return the itineraries for the holiday
     */
    public ArrayList<General> GetItineraries() {
        return itineraries;
    }

    /**
     *
     * @return the flights for the holiday
     */
    public ArrayList<Flight> GetFlights() {
        return flights;
    }

    /**
     *
     * @return the car trips for the holiday
     */
    public ArrayList<CarTrip> GetCarTrips() {
        return carTrips;
    }

    /**
     * Adds a General event to the itineraries ArrayList
     * @param generalEvent general Event to add to the itinerary
     */
    public void AddItinerary(General generalEvent) {
        itineraries.add(generalEvent);
    }

    /**
     * Adds a Flight to the flights ArrayList
     * @param flight Flight to add to the flights
     */
    public void AddFlight(Flight flight) {
        flights.add(flight);
    }

    /**
     * Adds a CarTrip to the carTrips ArrayList
     * @param carTrip CarTrip to add to the carTrips
     */
    public void AddCarTrip(CarTrip carTrip) {
        carTrips.add(carTrip);
    }
}
