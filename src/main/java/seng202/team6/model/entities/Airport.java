package seng202.team6.model.entities;
import seng202.team6.model.interfaces.IMapDrawable;

import java.lang.*;
import java.util.Objects;

public class Airport implements IMapDrawable {

    int AirportID;
    String Name;
    String City;
    String Country;
    String IATA;
    String ICAO;
    float Latitude;
    float Longitude;
    int Altitude;
    float Timezone;
    char DST;

    public Airport(int newAirportID, String newName, String newCity, String newCountry, String newIATA,
                   String newICAO, float newLatitude, float newLongitude, int newAltitude, float newTimezone,
                   char newDST) {
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


    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Airport airportObject = (Airport)obj;

        return this.AirportID == airportObject.getAirportID() && Objects.equals(this.Name, airportObject.getName())
                && Objects.equals(this.City, airportObject.getCity()) && Objects.equals(this.Country, airportObject.getCountry())
                && Objects.equals(this.IATA, airportObject.getIATA()) && Objects.equals(this.ICAO, airportObject.getICAO())
                && this.Latitude == airportObject.getLatitude() && this.Longitude == airportObject.getLongitude()
                && this.Altitude == airportObject.getAltitude() && this.Timezone == airportObject.getTimezone()
                && this.DST == airportObject.getDST();
    }

    /**
     * This method compares the distance between this instance of Airport with 'other' Airport
     * @param other a different airport
     * @return Distance between airports
     */
    public double GetDistance(Airport other) {
        float destLat = other.Latitude;
        float destLong = other.Longitude;
//        float x_pos = Math.abs(this.Latitude - destLat);
//        float y_pos = Math.abs(this.Longitude - destLong);
//        return Math.sqrt(Math.pow(x_pos, 2) + Math.pow(y_pos, 2));

        double conv = Math.PI/180;
        double dlong = Math.abs(destLong*conv - this.Longitude*conv);
        double dlat = Math.abs(destLat*conv - this.Latitude*conv);

        double a = (Math.pow(Math.sin(dlat/2), 2) + (Math.cos(this.Latitude*conv) * Math.cos(destLat*conv) * (Math.pow(Math.sin(dlong/2), 2))));
        return 6371 * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
    }

    @Override
    public String ConvertToJavascriptString() {
        return String.format("lat: %f, lng: %f, " +
                        "name: \"%s\", country: \"%s\", city: \"%s\", " +
                        "iata: \"%s\", icao: \"%s\", alt: %d, tz: %f",

                this.getLatitude(), this.getLongitude(), this.getName(),
                this.getCountry(), this.getCity(), this.getIATA(), this.getICAO(),
                this.getAltitude(), this.getTimezone());
    }

    public int getAirportID() {
        return AirportID;
    }

    public void SetAirportID(int airportID) {
        AirportID = airportID;
    }

    public String getName() {
        return Name;
    }

    public void SetName(String name) {
        Name = name;
    }

    public String getCity() {
        return City;
    }

    public void SetCity(String city) {
        City = city;
    }

    public String getCountry() {
        return Country;
    }

    public void SetCountry(String country) {
        Country = country;
    }

    public String getIATA() {
        return IATA;
    }

    public void SetIATA(String IATA) {
        this.IATA = IATA;
    }

    public String getICAO() {
        return ICAO;
    }

    public void SetICAO(String ICAO) {
        this.ICAO = ICAO;
    }

    public float getLatitude() {
        return Latitude;
    }

    public void SetLatitude(float latitude) {
        Latitude = latitude;
    }

    public float getLongitude() {
        return Longitude;
    }

    public void SetLongitude(float longitude) {
        Longitude = longitude;
    }

    public int getAltitude() {
        return Altitude;
    }

    public void SetAltitude(int altitude) {
        Altitude = altitude;
    }

    public float getTimezone() {
        return Timezone;
    }

    public void SetTimezone(int timezone) {
        Timezone = timezone;
    }

    public char getDST() {
        return DST;
    }

    public void SetDST(char DST) {
        this.DST = DST;
    }
}
