package seng202.team6.model.data;

import seng202.team6.gui.controller.AddRowController;
import seng202.team6.model.entities.Airline;
import seng202.team6.model.entities.Airport;
import seng202.team6.model.entities.Route;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class AddRowHandler {

    /**
     * Function to check the validity of the airport data inputted by the user.
     * Also checks that data is suitable for inserting into the database.
     * @param airpId Airport Id
     * @param airpName Airport Name
     * @param airpCity City
     * @param airpCountry Country
     * @param airpIata IATA
     * @param airpIcao ICAO
     * @param airpLat Latitude
     * @param airpLon Longitude
     * @param airpAlt Altitude
     * @param airpTim Timezone
     * @param airpDst Daylight savings
     * @return ArrayList of fields to be entered into database, or null if invalid input occurs
     */
    public ArrayList<String> CheckAirport(AddRowController addRowController, String airpId, String airpName, String airpCity, String airpCountry, String airpIata,
                                          String airpIcao, String airpLat, String airpLon, String airpAlt, String airpTim,
                                          String airpDst) {

        try {  // Check if airport id input is valid
            Integer.parseInt(airpId);
        } catch (Exception e) {
            addRowController.ShowMessage(true, "Check the Airport ID field and try again");
            return null;
        }

        try {  // Check if latitude input is valid
            Float.parseFloat(airpLat);
        } catch (Exception e) {
            addRowController.ShowMessage(true, "Check the latitude field and try again");
            return null;
        }

        try {  // Check if latitude and longitude inputs are valid
            Float.parseFloat(airpLon);
        } catch (Exception e) {
            addRowController.ShowMessage(true, "Check the longitude field and try again");
            return null;
        }

        try {  // Check if altitude input is valid
            Integer.parseInt(airpAlt);
        } catch (Exception e) {
            addRowController.ShowMessage(true, "Check the Altitude field and try again");
            return null;
        }

        try {  // Check if the time zone input is valid
            Integer.parseInt(airpTim);
        } catch (Exception e) {
            addRowController.ShowMessage(true, "Check the Time Zone field and try again");
            return null;
        }

        String dst;
        Set<String> validValues = Set.of("e", "a", "s", "o", "z", "n");
        if (validValues.contains(airpDst.toLowerCase())) {
            dst = airpDst.toUpperCase();
        } else {
            dst = "U";  // Unknown Daylight savings value
        }

        return new ArrayList<>(Arrays.asList(airpId, airpName, airpCity, airpCountry, airpIata, airpIcao, airpLat, airpLon,
                airpAlt, airpTim, dst));
    }


    /**
     * Function to check the validity of the airline data inputted by the user.
     * Also checks that data is suitable for inserting into the database.
     * @param airlineID Airline ID
     * @param name Airline name
     * @param alias Alias
     * @param iata IATA
     * @param icao ICAO
     * @param callsign Callsign
     * @param country Country
     * @param active Is the airline active
     * @return ArrayList of fields to be entered into database, or null if invalid input occurs
     */
    public ArrayList<String> CheckAirline(AddRowController addRowController, String airlineID, String name, String alias, String iata, String icao,
                                           String callsign, String country, String active) {

        try {  // Check if airline id input is valid
            Integer.parseInt(airlineID);
        } catch (Exception e) {
            addRowController.ShowMessage(true, "Check the Airline ID field and try again");
            return null;
        }

        String airActive;
        if ((active == null) || (active.equals("No"))) {
            airActive = "N";
        } else {
            airActive = "Y";
        }


        return new ArrayList<>(Arrays.asList(airlineID, name, alias, iata, icao, callsign, country, airActive));
    }


    /**
     * Function to check the validity of the route data inputted by the user.
     * Also checks that data is suitable for inserting into the database.
     * @param rouAir Airline
     * @param rouAirId Airline ID
     * @param rouSouAir Source airline
     * @param rouSouAirId Source airline ID
     * @param rouDesAir Destination airline
     * @param rouDesAirId Destination airline ID
     * @param rouCod Codeshare
     * @param rouStp Amount of stops
     * @param rouEqp Equipment
     * @return ArrayList of fields to be entered into database, or null if invalid input occurs
     */
    public ArrayList<String> CheckRoute(AddRowController addRowController, String rouAir, String rouAirId, String rouSouAir, String rouSouAirId, String rouDesAir,
                                         String rouDesAirId, String rouCod, double rouStp, String rouEqp) {

        try {  // Check if airline id input is valid
            Integer.parseInt(rouAirId);
        } catch (Exception e) {
            addRowController.ShowMessage(true, "Check the Airline ID field and try again");
            return null;
        }

        try {  // Check if source airline id input is valid
            Integer.parseInt(rouSouAirId);
        } catch (Exception e) {
            addRowController.ShowMessage(true, "Check the Source Airline ID field and try again");
            return null;
        }

        try {  // Check if destination airline id input is valid
            Integer.parseInt(rouDesAirId);
        } catch (Exception e) {
            addRowController.ShowMessage(true, "Check the Destination Airline ID field and try again");
            return null;
        }

        String codeShare;  // Parse combo box result
        if ((rouCod == null) || (rouCod.equals("No"))) {
            codeShare = "N";
        } else {
            codeShare = "Y";
        }

        String stops = String.valueOf((int) rouStp);

        return new ArrayList<>(Arrays.asList(rouAir, rouAirId, rouSouAir, rouSouAirId, rouDesAir, rouDesAirId, codeShare, stops, rouEqp));
    }


    /**
     * Creates an Airport object from an ArrayList of the required fields from a data entry
     * @param fields ArrayList if data fields
     * @return Airport object for insertion into database
     */
    public Airport CreateAirport(ArrayList<String> fields) {
        Airport airport = new Airport(Integer.parseInt(fields.get(0)), fields.get(1), fields.get(2), fields.get(3),
                fields.get(4), fields.get(5), Float.parseFloat(fields.get(6)), Float.parseFloat(fields.get(7)),
                Integer.parseInt(fields.get(8)), Integer.parseInt(fields.get(9)), fields.get(10).charAt(0));
        return airport;
    }


    /**
     * Creates an Airline object from an ArrayList of the required fields from a data entry
     * @param fields ArrayList if data fields
     * @return Airline object for insertion into database
     */
    public Airline CreateAirline(ArrayList<String> fields) {
        Airline airline = new Airline(Integer.parseInt(fields.get(0)), fields.get(1), fields.get(2), fields.get(3),
                fields.get(4), fields.get(5), fields.get(6), fields.get(7).charAt(0));
        return airline;
    }


    /**
     * Creates an Route object from an ArrayList of the required fields from a data entry
     * @param fields ArrayList if data fields
     * @return Route object for insertion into database
     */
    public Route CreateRoute(ArrayList<String> fields) {
        Route route = new Route(Integer.parseInt(fields.get(1)), fields.get(0), fields.get(2),
                Integer.parseInt(fields.get(3)), fields.get(4), Integer.parseInt(fields.get(5)),
                fields.get(6).charAt(0), Integer.parseInt(fields.get(7)), fields.get(8));
        return route;
    }
}
