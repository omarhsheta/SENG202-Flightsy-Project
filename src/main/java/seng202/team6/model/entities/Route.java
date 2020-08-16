package seng202.team6.model.entities;

public class Route {

    String AirlineID;
    String Airline;
    String SourceAirport;
    int SourceAirportID;
    String DestinationAirport;
    int DestinationAirportID;
    char Codeshare;
    int Stops;
    String Equipment;

    public Route(String newAirlineID, String newAirline, String newSourceAirport, int newSourceAirportID,
                 String newDestinationAirport, int newDestinationAirportID, char newCodeshare, int newStops,
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

    public String getAirlineID() {
        return AirlineID;
    }

    public void setAirlineID(String airlineID) {
        AirlineID = airlineID;
    }

    public String getAirline() {
        return Airline;
    }

    public void setAirline(String airline) {
        Airline = airline;
    }

    public String getSourceAirport() {
        return SourceAirport;
    }

    public void setSourceAirport(String sourceAirport) {
        SourceAirport = sourceAirport;
    }

    public int getSourceAirportID() {
        return SourceAirportID;
    }

    public void setSourceAirportID(int sourceAirportID) {
        SourceAirportID = sourceAirportID;
    }

    public String getDestinationAirport() {
        return DestinationAirport;
    }

    public void setDestinationAirport(String destinationAirport) {
        DestinationAirport = destinationAirport;
    }

    public int getDestinationAirportID() {
        return DestinationAirportID;
    }

    public void setDestinationAirportID(int destinationAirportID) {
        DestinationAirportID = destinationAirportID;
    }

    public char getCodeshare() {
        return Codeshare;
    }

    public void setCodeshare(char codeshare) {
        Codeshare = codeshare;
    }

    public int getStops() {
        return Stops;
    }

    public void setStops(int stops) {
        Stops = stops;
    }

    public String getEquipment() {
        return Equipment;
    }

    public void setEquipment(String equipment) {
        Equipment = equipment;
    }
}
