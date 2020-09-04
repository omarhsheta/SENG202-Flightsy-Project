package seng202.team6.model.entities;
import seng202.team6.model.interfaces.IMapDrawable;

import java.lang.*;

public class Airport implements IMapDrawable {

    int AirportID;
    String Name;
    String City;
    String Country;
    String IATA;
    String ICAO;
    float Latitude;
    float Longitude;
    Integer Altitude;
    Integer Timezone;
    char DST;

    public Airport(int newAirportID, String newName, String newCity, String newCountry, String newIATA,
                   String newICAO, float newLatitude, float newLongitude, Integer newAltitude, Integer newTimezone,
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

        return this.AirportID == airportObject.GetAirportID() && this.Name.equals(airportObject.GetName()) &&
                this.City.equals(airportObject.GetCity()) && this.Country.equals(airportObject.GetCountry()) &&
                this.IATA.equals(airportObject.GetIATA()) && this.ICAO.equals(airportObject.GetICAO()) &&
                this.Latitude == airportObject.GetLatitude() && this.Longitude == airportObject.GetLongitude() &&
                this.Altitude.equals(airportObject.GetAltitude()) && this.Timezone.equals(airportObject.GetTimezone()) &&
                this.DST == airportObject.GetDST();
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
                        "iata: \"%s\", icao: \"%s\", alt: %d, tz: %d",

                this.GetLatitude(), this.GetLongitude(), this.GetName(),
                this.GetCountry(), this.GetCity(), this.GetIATA(), this.GetICAO(),
                this.GetAltitude(), this.GetTimezone());
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

    public Float GetLatitude() {
        return Latitude;
    }

    public void SetLatitude(Float latitude) {
        Latitude = latitude;
    }

    public Float GetLongitude() {
        return Longitude;
    }

    public void SetLongitude(Float longitude) {
        Longitude = longitude;
    }

    public Integer GetAltitude() {
        return Altitude;
    }

    public void SetAltitude(Integer altitude) {
        Altitude = altitude;
    }

    public Integer GetTimezone() {
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
}
