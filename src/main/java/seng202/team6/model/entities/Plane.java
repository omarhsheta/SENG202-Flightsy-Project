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

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Plane planeObj = (Plane)obj;

        return this.Name.equals(planeObj.getName()) && this.IATA.equals(planeObj.getIATA()) && this.ICAO.equals(planeObj.getICAO());
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
