package seng202.team6.gui.controller;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seng202.team6.model.MapHelper;
import seng202.team6.model.events.CarTrip;
import seng202.team6.model.events.Event;
import seng202.team6.model.events.Flight;
import seng202.team6.model.events.General;
import seng202.team6.model.user.HolidayPlan;

import javax.swing.event.ChangeListener;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class HolidayAgendaController implements Initializable {

    ArrayList<HolidayPlan> holidays = new ArrayList<>();
    private String selectedHoliday;

    @FXML
    private ChoiceBox<String> holidaySelectChoiceBox = new ChoiceBox<>();
    private String currSelectedHoliday;
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
        Instance = this;
        holidaySelectChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> changeHoliday());
        holidaySelectChoiceBox.getItems().add("New Holiday");
        holidays.add(new HolidayPlan("Holiday 1"));
        holidays.add(new HolidayPlan("Holiday 2"));
        holidays.add(new HolidayPlan("Holiday 3"));
        holidays.add(new HolidayPlan("Holiday 4"));
        holidays.add(new HolidayPlan("Holiday 5"));
        for (HolidayPlan holiday: holidays) {
            holidaySelectChoiceBox.getItems().add(holiday.getName());
        }
        webEngine = webView2.getEngine();
        webEngine.load(getClass().getResource(mapHTML).toExternalForm());

        controller = new MapHelper(webEngine);

        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                OnLoad();
            }
        });
    }

    /**
     * Called when WebEngine is finished loading
     */
    private void OnLoad() {
        // to get index of tab selected
    }

    public ArrayList<HolidayPlan> getHolidays() {
        return holidays;
    }

    /**
     * A method that is called every time the choicebox value changes. This method changes the holiday visible to the recently selected holiday
     */
    private void changeHoliday() {
        selectedHoliday = holidaySelectChoiceBox.getValue();
        int currHolidayIndex = getSelectedHolidayIndex();
        if (currHolidayIndex == holidays.size()) { // New Holiday was selected
            createNewHoliday();
        }
        selectedHoliday = holidaySelectChoiceBox.getValue();

        int i = 0;
        for(HolidayPlan holiday: holidays) {
            System.out.println(String.format("%s, index: %d", holiday.getName(), i++));
        }
        System.out.println("\n");

        System.out.println(String.format("Current index: %d", currHolidayIndex));
        showHoliday(holidays.get(currHolidayIndex));
    }

    /**
     *
     * @return selected holiday index from the holidaySelectChoiceBox
     */
    public int getSelectedHolidayIndex() {
        int count = 0;
        if (selectedHoliday == "New Holiday") {
            return holidays.size();// Index of new holiday
        } else {
            for (HolidayPlan holiday : holidays) {
                if (selectedHoliday == holiday.getName()) {
                    return count;
                }
                count++;
            }
        }
        return -1;// Shouldn't get to this point
    }

    /**
     * A method to create a new holiday
     */
    private void createNewHoliday() {
        //Create new holiday here
    }

    /**
     * Called when the user wants to add a general event to their holiday. This method calls the addItinerary method in the selected holiday
     * @param generalEvent General event to be added to the itineraries
     */
    public void addItineraryToHoliday(General generalEvent) {
        int currHolidayIndex = getSelectedHolidayIndex();
        //int currHolidayIndex = holidaysTabPane.getSelectionModel().getSelectedIndex();
        holidays.get(currHolidayIndex).addItinerary(generalEvent);
        showHoliday(holidays.get(currHolidayIndex));
    }

    /**
     * Called when the user wants to add a flight to their holiday. This method calls the addFlight method in the selected holiday
     * @param flight Flight to be added to the flights
     */
    public void addFlightToHoliday(Flight flight) {
        int currHolidayIndex = getSelectedHolidayIndex();
        holidays.get(currHolidayIndex).addFlight(flight);
        showHoliday(holidays.get(currHolidayIndex));
    }

    /**
     * Called when the user wants to add a car trip to their holiday. This method calls the addCarTrip method in the selected holiday
     * @param carTrip CarTrip to be added to the carTrips
     */
    public void addCarTripToHoliday(CarTrip carTrip) {
        int currHolidayIndex = getSelectedHolidayIndex();
        holidays.get(currHolidayIndex).addCarTrip(carTrip);
        showHoliday(holidays.get(currHolidayIndex));
    }

    /**
     * A method for displaying the holidays events in the GUI. This method appends Panes of the events to a VBox is
     * sorted order by date and time.
     */
    private void showHoliday(HolidayPlan holiday) {
        eventsVBox.getChildren().clear();

        ArrayList<Event> allEvents = new ArrayList<>();
        allEvents.addAll(holiday.getItineraries());
        allEvents.addAll(holiday.getFlights());
        allEvents.addAll(holiday.getCarTrips());

        Event earliestEvent = null;
        int numEvents = allEvents.size();
        //Sorting the events for displaying
        for(int i = 0; i < numEvents;i++) {
            for(Event event: allEvents) {
                if (earliestEvent == null || event.getDateTime().isBefore(earliestEvent.getDateTime())) {
                    earliestEvent = event;
                }
            }
            eventsVBox.getChildren().add(earliestEvent.toPane());
            allEvents.remove(earliestEvent);
            earliestEvent = null;
        }
    }
}
