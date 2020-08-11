package seng202.team6.model.entities;

public class Plane {

    String Name;
    String IATA;
    String ICAO;

    public Plane(String newName, String newIATA, String newICAO) {
        Name = newName;
        IATA = newIATA;
        ICAO = newICAO;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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
}
