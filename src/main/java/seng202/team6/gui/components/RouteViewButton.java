package seng202.team6.gui.components;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import seng202.team6.model.data.DataHandler;
import seng202.team6.model.data.Filter;
import seng202.team6.model.data.SQLHelper;
import seng202.team6.model.entities.Airport;
import seng202.team6.model.entities.Route;
import seng202.team6.model.events.Flight;
import seng202.team6.model.user.HolidayPlan;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Extension on JavaFX TextField to support filtering
 */
public class RouteViewButton extends Button {
    Airport originAirport;
    Airport destAirport;
    Route route;

    DataHandler dataHandler;

    double distance;

    HolidayPlan holidayPlan;

    /**
     * RouteViewButton component
     */
    public RouteViewButton(int originAirportID, int destAirportID, Route route) {
        this.route = route;

        //TESTING BY OMAR


        //TESTING BY OMAR

        this.prefHeight(60);
        dataHandler = DataHandler.GetInstance();
        holidayPlan = HolidayPlan.GetInstance();
        Filter originFilter = new Filter(String.format("ID_AIRPORT = %d", originAirportID), "AND");
        ArrayList<Filter> originFilters = new ArrayList<>();
        originFilters.add(originFilter);
        ArrayList<Airport> originAirportList = dataHandler.FetchAirports(originFilters);

        Filter destFilter = new Filter(String.format("ID_AIRPORT = %d", destAirportID), "AND");
        ArrayList<Filter> destFilters = new ArrayList<>();
        destFilters.add(destFilter);
        ArrayList<Airport> destAirportList = dataHandler.FetchAirports(destFilters);

        // Only ever one airport as the filter is for the primary key
        this.originAirport = originAirportList.get(0);
        this.destAirport = destAirportList.get(0);

        distance = originAirport.GetDistance(destAirport);

        this.setText(String.format("%s --> %s\nDistance: %.2f", originAirport.getIATA(), destAirport.getIATA(), distance));
        this.setOnMouseClicked(x -> onClick());
    }

    /**
     * A Method that runs when the button is clicked.
     * This method creates an information box for all necessary information about the route.
     */
    private void onClick() {
        ButtonType addToHolidayButtonType = new ButtonType("Add To Holiday");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getButtonTypes().setAll(addToHolidayButtonType, ButtonType.CANCEL);
        alert.setTitle(String.format("%s to %s", originAirport.getIATA(), destAirport.getIATA()));
        alert.setHeaderText(String.format("Information for flight: %s to %s", originAirport.getIATA(), destAirport.getIATA()));
        alert.setContentText(String.format("%s to %s\nDistance: %.2f km\nAircraft: %s", originAirport.getName(), destAirport.getName(), distance, route.getEquipment()));

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == addToHolidayButtonType){
            System.out.println("Added to holiday");
            int day = 1;
            int month = 1;
            int year = 1;
            String title = String.format("Flight from %s to %s", route.getSourceAirport(), route.getDestinationAirport());
            String notes = "";
            Flight flight = new Flight(day, month, year, title, notes, originAirport.getCity(), originAirport.getCountry(),
                    destAirport.getCity(), destAirport.getCountry(), route.getAirline(), route.getSourceAirport(), route.getDestinationAirport());
            holidayPlan.FlightAppend(flight);
        }

        //System.out.println(String.format("%s to %s\nDistance: %.2f km", originAirport.GetName(), destAirport.GetName(), distance));
    }
}
