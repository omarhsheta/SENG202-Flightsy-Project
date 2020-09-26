package seng202.team6.gui.controller;

import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seng202.team6.model.MapHelper;
import seng202.team6.model.events.CarTrip;
import seng202.team6.model.events.Event;
import seng202.team6.model.events.Flight;
import seng202.team6.model.events.General;
import seng202.team6.model.user.HolidayPlan;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class HolidayAgendaController implements Initializable {

    private ArrayList<HolidayPlan> holidays;

    @FXML
    private TabPane holidaysTabPane;
    @FXML
    private VBox eventsVBox;

    @FXML
    private WebView webView2;

    private WebEngine webEngine;
    private final String mapHTML = "/map/main.html";
    private MapHelper controller;

    private static HolidayAgendaController Instance;

    /**
     * Singleton method to get the HolidayAgenda
     * @return Single HolidayAgendaController object
     */
    public static HolidayAgendaController GetInstance() {
        if (Instance == null) {
            Instance = new HolidayAgendaController();
        }
        return Instance;
    }

    /**
     * Called when this FXML page is loaded
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        webEngine = webView2.getEngine();
        webEngine.load(getClass().getResource(mapHTML).toExternalForm());

        controller = new MapHelper(webEngine);

        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                OnLoad();
            }
        });

        holidays = new ArrayList<>();
    }

    /**
     * Called when WebEngine is finished loading
     */
    private void OnLoad() {
        // to get index of tab selected
    }

    /**
     * Called when the user wants to add a general event to their holiday. This method calls the addItinerary method in the selected holiday
     * @param generalEvent General event to be added to the itineraries
     */
    public void addItineraryToHoliday(General generalEvent) {
        int currHoliday = holidaysTabPane.getSelectionModel().getSelectedIndex();
        holidays.get(currHoliday).addItinerary(generalEvent);
    }

    /**
     * Called when the user wants to add a flight to their holiday. This method calls the addFlight method in the selected holiday
     * @param flight Flight to be added to the flights
     */
    public void addFlightToHoliday(Flight flight) {
        int currHoliday = holidaysTabPane.getSelectionModel().getSelectedIndex();
        holidays.get(currHoliday).addFlight(flight);
    }

    /**
     * Called when the user wants to add a car trip to their holiday. This method calls the addCarTrip method in the selected holiday
     * @param carTrip CarTrip to be added to the carTrips
     */
    public void addCarTripToHoliday(CarTrip carTrip) {
        int currHoliday = holidaysTabPane.getSelectionModel().getSelectedIndex();
        holidays.get(currHoliday).addCarTrip(carTrip);
    }

    /**
     * A method for displaying the holidays events in the GUI. This method appends Panes of the events to a VBox is
     * sorted order by date and time.
     */
    private void showHoliday() {
        int currHolidayIndex = holidaysTabPane.getSelectionModel().getSelectedIndex();
        HolidayPlan holiday = holidays.get(currHolidayIndex);
        eventsVBox.getChildren().clear();

        ArrayList<Event> allEvents = new ArrayList<>();
        allEvents.addAll(holiday.getItineraries());
        allEvents.addAll(holiday.getFlights());
        allEvents.addAll(holiday.getCarTrips());

        Event earliestEvent = null;
        //Sorting the events for displaying
        for(Event event: allEvents) {

            if (earliestEvent == null || event.getDateTime().isBefore(earliestEvent.getDateTime())) {
                earliestEvent = event;
            }

            eventsVBox.getChildren().add(event.toPane());
            allEvents.remove(earliestEvent);
            earliestEvent = null;
        }
    }
}
