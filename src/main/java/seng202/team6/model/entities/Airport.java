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
    int Timezone;
    char DST;

    public Airport(int newAirportID, String newName, String newCity, String newCountry, String newIATA,
                   String newICAO, float newLatitude, float newLongitude, int newAltitude, int newTimezone,
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

    public int getAirportID() {
        return AirportID;
    }

    public void setAirportID(int airportID) {
        AirportID = airportID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getIATA() {
        return IATA;
    }

    public void setIATA(String IATA) {
        this.IATA = IATA;
    }

    public String getICAO() {
        return ICAO;
    }

    public void setICAO(String ICAO) {
        this.ICAO = ICAO;
    }

    public float getLatitude() {
        return Latitude;
    }

    public void setLatitude(float latitude) {
        Latitude = latitude;
    }

    public float getLongitude() {
        return Longitude;
    }

    public void setLongitude(float longitude) {
        Longitude = longitude;
    }

    public int getAltitude() {
        return Altitude;
    }

    public void setAltitude(int altitude) {
        Altitude = altitude;
    }

    public int getTimezone() {
        return Timezone;
    }

    public void setTimezone(int timezone) {
        Timezone = timezone;
    }

    public char getDST() {
        return DST;
    }

    public void setDST(char DST) {
        this.DST = DST;
    }
}
