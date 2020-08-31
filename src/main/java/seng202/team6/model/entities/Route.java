package seng202.team6.model.entities;

public class Route {

    int AirlineID;
    String Airline;
    String SourceAirport;
    int SourceAirportID;
    String DestinationAirport;
    int DestinationAirportID;
    Character Codeshare;
    int Stops;
    String Equipment;

    public Route(int newAirlineID, String newAirline, String newSourceAirport, int newSourceAirportID,
                 String newDestinationAirport, int newDestinationAirportID, Character newCodeshare, int newStops,
                 String newEquipment) {
        AirlineID = newAirlineID;
        Airline = newAirline;
        SourceAirport = newSourceAirport;
        SourceAirportID = newSourceAirportID;
        DestinationAirport = newDestinationAirport;
        DestinationAirportID = newDestinationAirportID;
        Codeshare = newCodeshare;
        Stops = newStops;
        Equipment = newEquipment;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Route routeObject = (Route)obj;

        return this.AirlineID == routeObject.GetAirlineID() &&
                this.Airline.equals(routeObject.GetAirline()) &&
                this.SourceAirport.equals(routeObject.GetSourceAirport()) &&
                this.SourceAirportID == routeObject.GetSourceAirportID() &&
                this.DestinationAirport.equals(routeObject.GetDestinationAirport()) &&
                this.DestinationAirportID == routeObject.GetDestinationAirportID() &&
                this.Codeshare == routeObject.GetCodeshare() &&
                this.Stops == routeObject.GetStops() &&
                this.Equipment.equals(routeObject.GetEquipment());
    }

    public int GetAirlineID() {
        return AirlineID;
    }

    public void SetAirlineID(int airlineID) {
        AirlineID = airlineID;
    }

    public String GetAirline() {
        return Airline;
    }

    public void SetAirline(String airline) {
        Airline = airline;
    }

    public String GetSourceAirport() {
        return SourceAirport;
    }

    public void SetSourceAirport(String sourceAirport) {
        SourceAirport = sourceAirport;
    }

    public int GetSourceAirportID() {
        return SourceAirportID;
    }

    public void SetSourceAirportID(int sourceAirportID) {
        SourceAirportID = sourceAirportID;
    }

    public String GetDestinationAirport() {
        return DestinationAirport;
    }

    public void SetDestinationAirport(String destinationAirport) {
        DestinationAirport = destinationAirport;
    }

    public int GetDestinationAirportID() {
        return DestinationAirportID;
    }

    public void SetDestinationAirportID(int destinationAirportID) {
        DestinationAirportID = destinationAirportID;
    }

    public Character GetCodeshare() { return Codeshare; }

    public void SetCodeshare(Character codeshare) {
        Codeshare = codeshare;
    }

    public int GetStops() {
        return Stops;
    }

    public void SetStops(int stops) {
        Stops = stops;
    }

    public String GetEquipment() {
        return Equipment;
    }

    public void SetEquipment(String equipment) {
        Equipment = equipment;
    }
}
