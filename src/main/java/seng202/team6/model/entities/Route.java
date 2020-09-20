package seng202.team6.model.entities;

import java.util.Objects;

import static java.lang.String.format;

public class Route implements Comparable<Route> {

    int AirlineID;
    String Airline;
    String SourceAirport;
    int SourceAirportID;
    String DestinationAirport;
    int DestinationAirportID;
    Character Codeshare;
    Integer Stops;
    String Equipment;

    /**
     * Constructor for class Route
     * @param newAirlineID int value for ID of the Airline
     * @param newAirline String value for the Airline's name
     * @param newSourceAirport String value for the origin's airport's name
     * @param newSourceAirportID int value for the ID of the origin's airport
     * @param newDestinationAirport String value for the destination's airport's name
     * @param newDestinationAirportID int value for the ID of the destination's airport
     * @param newCodeshare char value for Codeshare
     * @param newStops int value for the number of stops
     * @param newEquipment String value for Equipment
     */
    public Route(int newAirlineID, String newAirline, String newSourceAirport, int newSourceAirportID,
                 String newDestinationAirport, int newDestinationAirportID, Character newCodeshare, Integer newStops,
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

    /**
     * Checks for equality between two instances of Route
     * @param obj the other instance of Object (will return false if it is not Route.java.
     *            Otherwise, it will check if all the parameters and values are identical
     * @return returns either true or false
     */
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

    public Character getCodeshare() {
        return Codeshare;
    }

    public void SetCodeshare(Character codeshare) {
        Codeshare = codeshare;
    }

    public int getStops() {
        return Stops;
    }

    public void SetStops(Integer stops) {
        Stops = stops;
    }

    public String getEquipment() {
        return Equipment;
    }

    public void SetEquipment(String equipment) {
        Equipment = equipment;
    }

    public String toString() {
        return format("AirlineID: %d\n" + "Airline: %s\n" + "Source Airport: %s\n" + "Source Airport ID: %d\n" +
                "Destination Airport: %s\n" + "Destination Airport ID: %d\n" + "Codeshare: %c\n" + "Stops: %d\n" +
                "Equipment: %s", AirlineID, Airline, SourceAirport, SourceAirportID, DestinationAirport,
                DestinationAirportID, Codeshare, Stops, Equipment);
    }

    @Override
    public int compareTo(Route route) {
        return Integer.compare(AirlineID, route.AirlineID);
    }
}
