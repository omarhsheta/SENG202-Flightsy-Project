package seng202.team6.model.entities;

public class Airport {

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

    public void SetLatitude(float latitude) {
        Latitude = latitude;
    }

    public float GetLongitude() {
        return Longitude;
    }

    public void SetLongitude(float longitude) {
        Longitude = longitude;
    }

    public int GetAltitude() {
        return Altitude;
    }

    public void SetAltitude(int altitude) {
        Altitude = altitude;
    }

    public float GetTimezone() {
        return Timezone;
    }

    public void SetTimezone(int timezone) {
        Timezone = timezone;
    }

    public char GetDST() {
        return DST;
    }

    public void SetDST(char DST) {
        this.DST = DST;
    }
}
