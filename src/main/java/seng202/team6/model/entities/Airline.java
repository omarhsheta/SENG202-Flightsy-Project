package seng202.team6.model.entities;

public class Airline {

    int AirlineID;
    String Name;
    String Alias;
    String IATA;
    String ICAO;
    String Callsign;
    String Country;
    Character Active;

    public Airline(int newAirlineID, String newName, String newAlias, String newIATA, String newICAO,
                   String newCallsign, String newCountry, Character newActive) {
        AirlineID = newAirlineID;
        Name = newName;
        Alias = newAlias;
        IATA = newIATA;
        ICAO = newICAO;
        Callsign = newCallsign;
        Country = newCountry;
        Active = newActive;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Airline airlineObject = (Airline)obj;

        return this.AirlineID == airlineObject.GetAirlineID() && this.Name.equals(airlineObject.GetName())
                            && this.Alias.equals(airlineObject.GetAlias()) && this.IATA.equals(airlineObject.GetIATA()) &&
                            this.ICAO.equals(airlineObject.GetICAO()) && this.Callsign.equals(airlineObject.GetCallsign()) &&
                            this.Country.equals(airlineObject.GetCountry()) && this.Active == airlineObject.GetActive();
    }

    public int GetAirlineID() {
        return AirlineID;
    }

    public void SetAirlineID(int airlineID) {
        AirlineID = airlineID;
    }

    public String GetName() {
        return Name;
    }

    public void SetName(String name) {
        Name = name;
    }

    public String GetAlias() {
        return Alias;
    }

    public void SetAlias(String alias) {
        Alias = alias;
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

    public String GetCallsign() {
        return Callsign;
    }

    public void SetCallsign(String callsign) {
        Callsign = callsign;
    }

    public String GetCountry() {
        return Country;
    }

    public void SetCountry(String country) {
        Country = country;
    }

    public Character GetActive() {
        return Active;
    }

    public void SetActive(char active) {
        Active = active;
    }
}
