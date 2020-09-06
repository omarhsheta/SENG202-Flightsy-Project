package seng202.team6.model.entities;

import java.util.Objects;

public class Airline {

    int AirlineID;
    String Name;
    String Alias;
    String IATA;
    String ICAO;
    String Callsign;
    String Country;
    Character Active;

    /**
     * Constructor for the Airline class
     * @param newAirlineID int value for ID of the airline
     * @param newName String value for Airline's name
     * @param newAlias String value for Airline's alias
     * @param newIATA String value for Airline's International Air Transport Association (IATA) code
     * @param newICAO String value for Airline's International Civil Aviation Organization (ICAO) code
     * @param newCallsign String value for Callsign
     * @param newCountry String value for the Airline's home Country
     * @param newActive Character value for the Airline's active status (Either "Y" or "N")
     */
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

    /**
     * Checks for equality between two instances of Airline
     * @param obj the other instance of Object (will return false if it is not Airline.java.
     *            Otherwise, it will check if all the parameters and values are identical
     * @return returns either true or false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Airline airlineObject = (Airline)obj;


        return this.AirlineID == airlineObject.getAirlineID() && Objects.equals(this.Name, airlineObject.getName())
                && Objects.equals(this.Alias, airlineObject.getAlias()) && Objects.equals(this.IATA, airlineObject.getIATA())
                && Objects.equals(this.ICAO, airlineObject.getICAO()) && Objects.equals(this.Callsign, airlineObject.getCallsign())
                && Objects.equals(this.Country, airlineObject.getCountry()) && this.Active == airlineObject.getActive();
    }

    public int getAirlineID() {
        return AirlineID;
    }

    public void SetAirlineID(int airlineID) {
        AirlineID = airlineID;
    }

    public String getName() {
        return Name;
    }

    public void SetName(String name) {
        Name = name;
    }

    public String getAlias() {
        return Alias;
    }

    public void SetAlias(String alias) {
        Alias = alias;
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

    public String getCallsign() {
        return Callsign;
    }

    public void SetCallsign(String callsign) {
        Callsign = callsign;
    }

    public String getCountry() {
        return Country;
    }

    public void SetCountry(String country) {
        Country = country;
    }

    public Character getActive() {
        return Active;
    }

    public void SetActive(char active) {
        Active = active;
    }
}
