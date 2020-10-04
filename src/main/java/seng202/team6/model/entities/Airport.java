package seng202.team6.model.entities;

import javafx.util.Pair;
import seng202.team6.model.data.DataExportHandler;
import seng202.team6.model.data.Filter;
import seng202.team6.model.interfaces.IMapDrawable;

import java.util.ArrayList;
import java.util.Objects;

import static java.lang.String.format;

public class Airport implements IMapDrawable, Comparable<Airport> {

    int AirportID;
    String Name;
    String City;
    String Country;
    String IATA;
    String ICAO;
    Float Latitude;
    Float Longitude;
    Integer Altitude;
    Integer Timezone;
    Character DST;

    /**
     * Constructor for Airport class
     * @param newAirportID int value for ID of the Airport
     * @param newName String value for Airport's name
     * @param newCity String value for Airport's city
     * @param newCountry String value for the Airport's country
     * @param newIATA String value for Airport's International Air Transport Association (IATA) code
     * @param newICAO String value for Airport's International Civil Aviation Organization (ICAO) code
     * @param newLatitude float value for Airport's Latitude
     * @param newLongitude float value for Airport's Longitude
     * @param newAltitude int value for Airport's Altitude
     * @param newTimezone float value for the Airport's Timezone
     * @param newDST char value for the Airport's DST
     */
    public Airport(int newAirportID, String newName, String newCity, String newCountry, String newIATA,
                   String newICAO, Float newLatitude, Float newLongitude, Integer newAltitude, Integer newTimezone,
                   Character newDST) {
        AirportID = newAirportID;
        Name = newName;
        City = newCity;
        Country = newCountry;
        IATA = newIATA;
        ICAO = newICAO;
        Latitude = newLatitude;
        Longitude = newLongitude;
        Altitude = newAltitude;
        Timezone = newTimezone;
        DST = newDST;
    }

    /**
     * Checks for equality between two instances of Airport
     * @param obj the other instance of Object (will return false if it is not Airport.java.
     *            Otherwise, it will check if all the parameters and values are identical
     * @return returns either true or false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Airport airportObject = (Airport)obj;

        return this.AirportID == airportObject.GetAirportID() && Objects.equals(this.Name, airportObject.GetName())
                && Objects.equals(this.City, airportObject.GetCity()) && Objects.equals(this.Country, airportObject.GetCountry())
                && Objects.equals(this.IATA, airportObject.GetIATA()) && Objects.equals(this.ICAO, airportObject.GetICAO())
                && this.Latitude == airportObject.GetLatitude() && this.Longitude == airportObject.GetLongitude()
                && this.Altitude == airportObject.GetAltitude() && this.Timezone == airportObject.GetTimezone()
                && this.DST == airportObject.GetDST();
    }

    /**
     * This method compares the distance between this instance of Airport with 'other' Airport
     * @param other a different airport
     * @return Distance between airports
     */
    public double GetDistance(Airport other) {
        float destLat = other.Latitude;
        float destLong = other.Longitude;

        double conv = Math.PI/180;
        double dlong = Math.abs(destLong*conv - this.Longitude*conv);
        double dlat = Math.abs(destLat*conv - this.Latitude*conv);

        double a = (Math.pow(Math.sin(dlat/2), 2) + (Math.cos(this.Latitude*conv) * Math.cos(destLat*conv) * (Math.pow(Math.sin(dlong/2), 2))));
        return 6371 * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
    }

    @Override
    public String ConvertToJavascriptString() {
        return format("lat: %f, lng: %f, " +
                        "name: \"%s\", country: \"%s\", city: \"%s\", " +
                        "iata: \"%s\", icao: \"%s\", alt: %d, tz: %f",

                this.GetLatitude(), this.GetLongitude(), this.GetName(),
                this.GetCountry(), this.GetCity(), this.GetIATA(), this.GetICAO(),
                this.GetAltitude(), this.GetTimezone());
    }

    /**
     * Extract airports from filters
     * @param sourceFilters Source airport filters
     * @param destinationFilters Destination airport filters
     * @return Pair of source, destination airports
     */
    public static Pair<ArrayList<Airport>, ArrayList<Airport>> GetSourceAndDestinations(ArrayList<Filter> sourceFilters, ArrayList<Filter> destinationFilters) {
        ArrayList<Airport> sourceAirports = DataExportHandler.GetInstance().FetchAirports(sourceFilters);
        ArrayList<Airport> destinationAirports = DataExportHandler.GetInstance().FetchAirports(destinationFilters);

        return new Pair<>(sourceAirports, destinationAirports);
    }

    public int GetAirportID() {
        return AirportID;
    }

    public void SetAirportID(int airportID) {
        AirportID = airportID;
    }

    public String GetName() {
        return Name;
    }

    public void SetName(String name) {
        Name = name;
    }

    public String GetCity() {
        return City;
    }

    public void SetCity(String city) {
        City = city;
    }

    public String GetCountry() {
        return Country;
    }

    public void SetCountry(String country) {
        Country = country;
    }

    public String GetIATA() {
        return IATA;
    }

    public void SetIATA(String IATA) {
        this.IATA = IATA;
    }

    public String GetICAO() {
        return ICAO;
    }

    public void SetICAO(String ICAO) {
        this.ICAO = ICAO;
    }

    public float GetLatitude() {
        return Latitude;
    }

    public void SetLatitude(Float latitude) {
        Latitude = latitude;
    }

    public float GetLongitude() {
        return Longitude;
    }

    public void SetLongitude(Float longitude) {
        Longitude = longitude;
    }

    public int GetAltitude() {
        return Altitude;
    }

    public void SetAltitude(Integer altitude) {
        Altitude = altitude;
    }

    public float GetTimezone() {
        return Timezone;
    }

    public void SetTimezone(Integer timezone) {
        Timezone = timezone;
    }

    public char GetDST() {
        return DST;
    }

    public void SetDST(char DST) {
        this.DST = DST;
    }

    public String toString() {
        return format("AirportID: %d\n" + "Name: %s\n" + "City: %s\n" + "Country: %s\n" + "IATA: %s\n" + "ICAO: %s\n" + "Latitude: " +
                "%f\n" + "Longitude: %f\n" + "Altitude: %d\n" + "Timezone: %d\n" + "DST: %c\n", AirportID, Name, City
                , Country, IATA, ICAO, Latitude, Longitude, Altitude, Timezone, DST);
    }

    @Override
    public int compareTo(Airport airport) {
        return Integer.compare(AirportID, airport.AirportID);
    }
}
