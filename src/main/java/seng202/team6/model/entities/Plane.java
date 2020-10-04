package seng202.team6.model.entities;

import java.util.Objects;

public class Plane {

    String Name;
    String IATA;
    String ICAO;

    /**
     * Constructor for class Plane
     * @param newName String value for Plane's name
     * @param newIATA String value for Plane's International Air Transport Association (IATA) code
     * @param newICAO String value for Plane's International Civil Aviation Organization (ICAO) code
     */
    public Plane(String newName, String newIATA, String newICAO) {
        Name = newName;
        IATA = newIATA;
        ICAO = newICAO;
    }

    /**
     * Checks for equality between two instances of Plane
     * @param obj the other instance of Object (will return false if it is not Plane.java.
     *            Otherwise, it will check if all the parameters and values are identical
     * @return returns either true or false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Plane planeObj = (Plane)obj;

        return Objects.equals(this.Name, planeObj.GetName())
                && Objects.equals(this.IATA, planeObj.GetIATA())
                && Objects.equals(this.ICAO, planeObj.GetICAO());
    }

    public String GetName() {
        return Name;
    }

    public void SetName(String name) {
        Name = name;
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
}
