package seng202.team6.model.entities;

import java.util.Objects;

public class Route {

    int AirlineID;
    String Airline;
    String SourceAirport;
    int SourceAirportID;
    String DestinationAirport;
    int DestinationAirportID;
    char Codeshare;
    int Stops;
    String Equipment;

    public Route(int newAirlineID, String newAirline, String newSourceAirport, int newSourceAirportID,
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Route routeObject = (Route)obj;

        return this.AirlineID == routeObject.getAirlineID()
                && Objects.equals(this.Airline, routeObject.getAirline())
                && Objects.equals(this.SourceAirport, routeObject.getSourceAirport())
                && this.SourceAirportID == routeObject.getSourceAirportID()
                && Objects.equals(this.DestinationAirport, routeObject.getDestinationAirport())
                && this.DestinationAirportID == routeObject.getDestinationAirportID()
                && this.Codeshare == routeObject.getCodeshare()
                && this.Stops == routeObject.getStops()
                && Objects.equals(this.Equipment, routeObject.getEquipment());
    }

    public int getAirlineID() {
        return AirlineID;
    }

    public void SetAirlineID(int airlineID) {
        AirlineID = airlineID;
    }

    public String getAirline() {
        return Airline;
    }

    public void SetAirline(String airline) {
        Airline = airline;
    }

    public String getSourceAirport() {
        return SourceAirport;
    }

    public void SetSourceAirport(String sourceAirport) {
        SourceAirport = sourceAirport;
    }

    public int getSourceAirportID() {
        return SourceAirportID;
    }

    public void SetSourceAirportID(int sourceAirportID) {
        SourceAirportID = sourceAirportID;
    }

    public String getDestinationAirport() {
        return DestinationAirport;
    }

    public void SetDestinationAirport(String destinationAirport) {
        DestinationAirport = destinationAirport;
    }

    public int getDestinationAirportID() {
        return DestinationAirportID;
    }

    public void SetDestinationAirportID(int destinationAirportID) {
        DestinationAirportID = destinationAirportID;
    }

    public char getCodeshare() {
        return Codeshare;
    }

    public void SetCodeshare(char codeshare) {
        Codeshare = codeshare;
    }

    public int getStops() {
        return Stops;
    }

    public void SetStops(int stops) {
        Stops = stops;
    }

    public String getEquipment() {
        return Equipment;
    }

    public void SetEquipment(String equipment) {
        Equipment = equipment;
    }
}
