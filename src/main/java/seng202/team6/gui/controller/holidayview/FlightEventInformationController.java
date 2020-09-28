package seng202.team6.gui.controller.holidayview;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import seng202.team6.model.data.Filter;
import seng202.team6.model.entities.Airport;
import seng202.team6.model.entities.Route;
import seng202.team6.model.events.Flight;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class FlightEventInformationController {

    @FXML
    Text titleInfo;
    @FXML
    Text airportInfo;
    @FXML
    Text locationInfo;
    @FXML
    Text distanceInfo;
    @FXML
    Text aircraftInfo;
    @FXML
    Text departureDateTimeInfo;
    @FXML
    Text arrivalDateTimeInfo;


    @FXML
    Button closeButton;


    private Route route;
    private Flight flight;

    private Stage stage;

    Airport originAirport;
    Airport destAirport;
    double distance;

    /**
     * Called when this FXML page is loaded
     */
    public void initialize() {

    }

    /**
     * Called to populate the flightinformation FXML file with information about the flight.
     */
    public void populateInfo() {
        setAirports();
        distance = originAirport.GetDistance(destAirport);

        titleInfo.setText(String.format("Information about flight: %s to %s", originAirport.getIATA(), destAirport.getIATA()));
        airportInfo.setText(String.format("%s to %s", originAirport.getName(), destAirport.getName()));
        locationInfo.setText(String.format("%s, %s to %s, %s", originAirport.getCity(), originAirport.getCountry(), destAirport.getCity(), destAirport.getCountry()));
        distanceInfo.setText(String.format("Distance: %.2fkm", distance));
        aircraftInfo.setText(String.format("Aircraft: %s", route.getEquipment()));


        String deptDayOfWeek = flight.getDateTime().getDayOfWeek().toString();
        deptDayOfWeek = deptDayOfWeek.substring(0, 1) + deptDayOfWeek.substring(1).toLowerCase();
        String deptMonth = flight.getDateTime().getMonth().toString();
        deptMonth = deptMonth.substring(0, 1) + deptMonth.substring(1).toLowerCase();
        String deptDayEnd;
        if (flight.getDateTime().getDayOfMonth() == 2 || flight.getDateTime().getDayOfMonth() == 3) {
            deptDayEnd = "rd";
        } else if (flight.getDateTime().getDayOfMonth() == 1 || flight.getDateTime().getDayOfMonth() == 21 || flight.getDateTime().getDayOfMonth() == 31 ) {
            deptDayEnd = "st";
        } else {
            deptDayEnd = "th";
        }
        String deptTime = flight.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        departureDateTimeInfo.setText(String.format("Depart %s %d%s %s, %d at %s", deptDayOfWeek, flight.getDateTime().getDayOfMonth(), deptDayEnd, deptMonth, flight.getDateTime().getYear(), deptTime));


        String destDayOfWeek = flight.getArrivalDateTime().getDayOfWeek().toString();
        destDayOfWeek = destDayOfWeek.substring(0, 1) + destDayOfWeek.substring(1).toLowerCase();
        String destMonth = flight.getArrivalDateTime().getMonth().toString();
        destMonth = destMonth.substring(0, 1) + destMonth.substring(1).toLowerCase();
        String arrivalDayEnd;
        if (flight.getArrivalDateTime().getDayOfMonth() == 2 || flight.getArrivalDateTime().getDayOfMonth() == 3) {
            arrivalDayEnd = "rd";
        } else if (flight.getArrivalDateTime().getDayOfMonth() == 1 || flight.getArrivalDateTime().getDayOfMonth() == 21 || flight.getDateTime().getDayOfMonth() == 31 ) {
            arrivalDayEnd = "st";
        } else {
            arrivalDayEnd = "th";
        }
        String arrivalTime = flight.getArrivalDateTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        arrivalDateTimeInfo.setText(String.format("Arrive %s %d%s %s, %d at %s", destDayOfWeek, flight.getArrivalDateTime().getDayOfMonth(), arrivalDayEnd, destMonth, flight.getArrivalDateTime().getYear(), arrivalTime));
    }

    /**
     * Sets the variable route to the Route object supplied
     * @param route Route object supplied
     */
    public void setRoute(Route route) {
        this.route = route;
    }

    /**
     * Sets the variable flight to the Flight object supplied
     * @param flight Flight object supplied
     */
    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    /**
     * Uses the route to get the source and destination airport objects. It does this by using the route objects
     * sourceAirportID and destinationAirportID to query the database and get the airports.
     */
    private void setAirports() {
        ArrayList<Filter> originFilters = new ArrayList<>();
        originFilters.add(new Filter(String.format("ID_AIRPORT = %d", route.getSourceAirportID()), null));

        ArrayList<Filter> destFilters = new ArrayList<>();
        destFilters.add(new Filter(String.format("ID_AIRPORT = %d", route.getDestinationAirportID()), null));

        Pair<ArrayList<Airport>, ArrayList<Airport>> airports = Airport.GetSourceAndDestinations(originFilters, destFilters);
        ArrayList<Airport> originAirportList = airports.getKey();
        ArrayList<Airport> destAirportList = airports.getValue();

        // Only ever one airport as the filter is for the primary key, unless something went wrong...
        if (originAirportList.size() == 1 && destAirportList.size() == 1)
        {
            this.originAirport = originAirportList.get(0);
            this.destAirport = destAirportList.get(0);
        }
    }

    /**
     * sets the Stage variable stage to the supplied Stage.
     * @param newStage Stage object supplied
     */
    public void setStage(Stage newStage) {
        stage = newStage;
    }

    /**
     * Called when the user wants to close the window, calls stage.close().
     */
    @FXML
    private void closeWindow() {
        stage.close();
    }
}
