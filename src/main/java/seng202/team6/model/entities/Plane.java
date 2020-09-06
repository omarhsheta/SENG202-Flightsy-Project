package seng202.team6.model.entities;

import java.util.Objects;

public class Plane {

    String Name;
    String IATA;
    String ICAO;

    public Plane(String newName, String newIATA, String newICAO) {
        Name = newName;
        IATA = newIATA;
        ICAO = newICAO;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Plane planeObj = (Plane)obj;

        return Objects.equals(this.Name, planeObj.getName())
                && Objects.equals(this.IATA, planeObj.getIATA())
                && Objects.equals(this.ICAO, planeObj.getICAO());
    }

    public String getName() {
        return Name;
    }

    public void SetName(String name) {
        Name = name;
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
}
