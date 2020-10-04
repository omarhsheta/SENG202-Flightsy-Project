package seng202.team6.model.data;

import seng202.team6.model.entities.Airline;
import seng202.team6.model.entities.Airport;
import seng202.team6.model.entities.Route;

import java.util.ArrayList;
import java.util.Set;

public class AddRowHandler {

    /**
     * Function to check the validity of the airport data inputted by the user.
     * Also checks that data is suitable for inserting into the database.
     * @param fields ArrayList of fields input by user in Add Row popup
     * @return String either error message if a field is invalid or null if all fields are valid.
     */
    public String CheckAirport(ArrayList<String> fields) {

        try {  // Check if airport id input is valid
            Integer.parseInt(fields.get(0));
        } catch (Exception e) {
            return "Check the Airport ID field and try again";
        }

        try {  // Check if latitude input is valid
            Float.parseFloat(fields.get(6));
        } catch (Exception e) {
            return "Check the latitude field and try again";
        }

        try {  // Check if latitude and longitude inputs are valid
            Float.parseFloat(fields.get(7));
        } catch (Exception e) {
            return "Check the longitude field and try again";
        }

        try {  // Check if altitude input is valid
            Integer.parseInt(fields.get(8));
        } catch (Exception e) {
            return "Check the Altitude field and try again";
        }

        try {  // Check if the time zone input is valid
            Integer.parseInt(fields.get(9));
        } catch (Exception e) {
            return "Check the Time Zone field and try again";
        }


        Set<String> validValues = Set.of("e", "a", "s", "o", "z", "n");
        if (validValues.contains(fields.get(10).toLowerCase())) {
            fields.set(10, fields.get(10).toUpperCase());
        } else {
            fields.set(10, "U");  // Unknown Daylight savings value
        }

        return null;
    }


    /**
     * Function to check the validity of the airline data inputted by the user.
     * Also checks that data is suitable for inserting into the database.
     * @param fields ArrayList of fields input by user in Add Row popup
     * @return String either error message if a field is invalid or null if all fields are valid.
     */
    public String CheckAirline(ArrayList<String> fields) {

        try {  // Check if airline id input is valid
            Integer.parseInt(fields.get(0));
        } catch (Exception e) {
            return "Check the Airline ID field and try again";
        }

        if ((fields.get(7) == null) || (fields.get(7).equals("No"))) {
            fields.set(7, "N");
        } else {
            fields.set(7, "Y");
        }

        return null;
    }


    /**
     * Function to check the validity of the route data inputted by the user.
     * Also checks that data is suitable for inserting into the database.
     * @param fields ArrayList of fields input by user in Add Row popup
     * @return String either error message if a field is invalid or null if all fields are valid.
     */
    public String CheckRoute(ArrayList<String> fields) {

        try {  // Check if airline id input is valid
            Integer.parseInt(fields.get(1));
        } catch (Exception e) {
            return "Check the Airline ID field and try again";
        }

        try {  // Check if source airline id input is valid
            Integer.parseInt(fields.get(3));
        } catch (Exception e) {
            return "Check the Source Airline ID field and try again";
        }

        try {  // Check if destination airline id input is valid
            Integer.parseInt(fields.get(5));
        } catch (Exception e) {
            return "Check the Destination Airline ID field and try again";
        }

        String codeShare;  // Parse combo box result
        if ((fields.get(6) == null) || (fields.get(6).equals("No"))) {
            fields.set(6, "N");
        } else {
            fields.set(6, "Y");
        }

        return null;
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
