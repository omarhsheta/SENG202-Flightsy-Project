package seng202.team6.gui.controller;

import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng202.team6.model.MapHelper;
import seng202.team6.model.entities.Airport;
import seng202.team6.model.events.CarTrip;
import seng202.team6.model.events.Event;
import seng202.team6.model.events.Flight;
import seng202.team6.model.events.General;
import seng202.team6.model.user.HolidayPlan;

import javax.swing.event.ChangeListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

public class HolidayAgendaController implements Initializable {

    ArrayList<HolidayPlan> holidays = new ArrayList<>();

    @FXML
    private ChoiceBox<String> holidaySelectChoiceBox = new ChoiceBox<>();
    private int selectedHolidayIndex;

    @FXML
    private VBox eventsVBox;

    @FXML
    private WebView webView2;

    @FXML
    private Button newHolidayButton;

    private WebEngine webEngine;
    private final String mapHTML = "/map/main.html";
    private MapHelper mapHelper;
    private boolean finishedLoading = false;

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

    public ChoiceBox<String> GetHolidaySelectChoiceBox() {
        return this.holidaySelectChoiceBox;
    }


    /**
     * Called when this FXML page is loaded
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Instance = this;
        holidaySelectChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) ->
                        ChangeHoliday(holidaySelectChoiceBox.getSelectionModel().getSelectedIndex()));

        //Temporary
        holidays.add(new HolidayPlan("Holiday 1"));
        holidays.add(new HolidayPlan("Holiday 2"));
        holidays.add(new HolidayPlan("Holiday 3"));
        holidays.add(new HolidayPlan("Holiday 4"));
        holidays.add(new HolidayPlan("Holiday 5"));
        for (HolidayPlan holiday: holidays) {
            holidaySelectChoiceBox.getItems().add(holiday.getName());
        }
        holidaySelectChoiceBox.getSelectionModel().select(0);
        //End Temporary

        webEngine = webView2.getEngine();
        webEngine.load(getClass().getResource(mapHTML).toExternalForm());

        mapHelper = new MapHelper(webEngine);

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
        finishedLoading = true;
    }

    public ArrayList<HolidayPlan> getHolidays() {
        return holidays;
    }

    /**
     * A method that is called every time the choicebox value changes. This method changes the holiday visible to the recently selected holiday
     * @param index Index to swap to
     */
    public void ChangeHoliday(int index) {
        selectedHolidayIndex = index;
        showHoliday(holidays.get(index));

        //If this is updated from another view
        if (holidaySelectChoiceBox.getSelectionModel().getSelectedIndex() != selectedHolidayIndex) {
            holidaySelectChoiceBox.getSelectionModel().select(selectedHolidayIndex);
        }
    }

    /**
     * Add event to holiday
     * @param <T> Event to add
     */
    public <T extends Event> void AddToHoliday(T event) {
        if (event.getClass() == General.class) {
            holidays.get(selectedHolidayIndex).addItinerary((General) event);
        } else if (event.getClass() == Flight.class) {
            holidays.get(selectedHolidayIndex).addFlight((Flight) event);
        } else if (event.getClass() == CarTrip.class) {
            holidays.get(selectedHolidayIndex).addCarTrip((CarTrip) event);
        }
        showHoliday(holidays.get(selectedHolidayIndex));
    }

    /**
     * A method for displaying the holidays events in the GUI. This method appends Panes of the events to a VBox is
     * sorted order by date and time.
     */
    private void showHoliday(HolidayPlan holiday) {
        if (finishedLoading) {
            mapHelper.ClearAll();
        }
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

        /* Show flights on map */
        for (Flight flight: holiday.getFlights()) {
            ArrayList<Airport> airports = flight.getRoute().GetAirports();
            mapHelper.DrawAirportMarks(airports);
            mapHelper.DrawLineBetween(airports);
        }
    }

    /**
     * A method to create a new holiday
     */
    public void OnNewHolidayButtonClicked() {
        System.out.println("Not implemented yet");
    }

    /**
     * A function that creates a new stage for the user to manually add an event to holiday
     * @throws IOException Exception
     */
    @FXML
    public void OnNewEventButtonClicked() throws IOException {
        final Stage popUp = new Stage();
        popUp.initModality(Modality.APPLICATION_MODAL);
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(
                        getClass().getClassLoader().getResource("addevent.fxml")
                )
        );
        Scene popUpScene = new Scene(root);
        //Set variables
        popUp.setTitle("Add Event");
        popUpScene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
        popUp.setScene(popUpScene);
        popUp.show();
    }
}
