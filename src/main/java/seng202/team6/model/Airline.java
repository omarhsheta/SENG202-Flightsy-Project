package seng202.team6.model;

public class Airline {

    int AirlineID;
    String Name;
    String Alias;
    String IATA;
    String ICAO;
    String Callsign;
    String Country;
    char Active;

    public Airline(int newAirlineID, String newName, String newAlias, String newIATA, String newICAO,
                   String newCallsign, String newCountry, char newActive) {
        AirlineID = newAirlineID;
        Name = newName;
        Alias = newAlias;
        IATA = newIATA;
        ICAO = newICAO;
        Callsign = newCallsign;
        Country = newCountry;
        Active = newActive;
    }

    public int getAirlineID() {
        return AirlineID;
    }

    public void setAirlineID(int airlineID) {
        AirlineID = airlineID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAlias() {
        return Alias;
    }

    public void setAlias(String alias) {
        Alias = alias;
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

    public String getCallsign() {
        return Callsign;
    }

    public void setCallsign(String callsign) {
        Callsign = callsign;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public char getActive() {
        return Active;
    }

    public void setActive(char active) {
        Active = active;
    }
}
