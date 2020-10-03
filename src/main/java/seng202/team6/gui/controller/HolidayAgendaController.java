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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import seng202.team6.gui.controller.holidayview.NewHolidayController;
import seng202.team6.gui.helper.NodeHelper;
import seng202.team6.model.MapHelper;
import seng202.team6.model.data.DataExportHandler;
import seng202.team6.model.data.DataHandler;
import seng202.team6.model.entities.Airport;
import seng202.team6.model.events.CarTrip;
import seng202.team6.model.events.Event;
import seng202.team6.model.events.Flight;
import seng202.team6.model.events.General;
import seng202.team6.model.user.HolidayPlan;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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

        LoadHolidays();

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
        mapHelper.ClearAll();
        /* Show flights on map */
        if (holidays.size() > 0) {
            for (Flight flight : holidays.get(selectedHolidayIndex).getFlights()) {
                ArrayList<Airport> airports = flight.getRoute().GetAirports();
                mapHelper.DrawAirportMarks(airports);
                mapHelper.DrawLineBetween(airports);
            }
        }
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
     * Load holidays from the database
     */
    public void LoadHolidays() {
        try {
            ArrayList<String> holidayFiles = DataExportHandler.GetInstance().FetchHolidayPlans(null);
            for (String dir : holidayFiles) {
                HolidayPlan plan = HolidayPlan.FromJSON(DataHandler.GetInstance().ReadDataFile(dir));
                holidays.add(plan);
            }
        } catch (Exception e) {
            System.out.println("Failed to load holidays.");
        }

        for (HolidayPlan holiday : holidays) {
            holidaySelectChoiceBox.getItems().add(holiday.getName());
        }

        if (holidays.size() > 0) {
            holidaySelectChoiceBox.getSelectionModel().select(0);
        }
    }

    /**
     * Create a new holiday
     * @param name Name of holiday
     */
    public void CreateNewHoliday(String name) {
        for (HolidayPlan plan : holidays) {
            if (plan.getName().equals(name)) {
                System.out.println("Failed to create holiday, name already taken.");
                return;
            }
        }
        HolidayPlan plan = new HolidayPlan(name);
        plan.SaveHoliday();
        holidays.add(plan);
        holidaySelectChoiceBox.getItems().add(name);
        holidaySelectChoiceBox.getSelectionModel().select(name);
    }

    /**
     * Add event to holiday
     * @param <T> Event to add
     */
    public <T extends Event> void AddToHoliday(T event) {
        if (holidays.size() == 0) {
            return;
        }

        HolidayPlan holiday = holidays.get(selectedHolidayIndex);
        if (event.getClass() == General.class) {
            holiday.addItinerary((General) event);
        } else if (event.getClass() == Flight.class) {
            holiday.addFlight((Flight) event);
        } else if (event.getClass() == CarTrip.class) {
            holiday.addCarTrip((CarTrip) event);
        }
        holiday.SaveHoliday();
        showHoliday(holiday);
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
            if (earliestEvent == null) {
                continue;
            }
            eventsVBox.getChildren().add(earliestEvent.toPane());
            allEvents.remove(earliestEvent);
            earliestEvent = null;
        }

        if (finishedLoading) {
            mapHelper.ClearAll();
            /* Show flights on map */
            for (Flight flight : holiday.getFlights()) {
                ArrayList<Airport> airports = flight.getRoute().GetAirports();
                mapHelper.DrawAirportMarks(airports);
                mapHelper.DrawLineBetween(airports);
            }
        }
    }

    /**
     * A method to create a new holiday
     */
    public void OnNewHolidayButtonClicked() throws IOException {
        Stage popUp = new Stage();
        popUp.initModality(Modality.APPLICATION_MODAL);
        Pair<BorderPane, NewHolidayController> pair = NodeHelper.LoadNode("holidayview", "addHoliday");
        Pane pane = pair.getKey();
        NewHolidayController controller = pair.getValue();
        //Set variables
        popUp.setTitle("Add Holiday");
        Scene popUpScene = new Scene(pane);
        popUpScene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
        popUp.setScene(popUpScene);
        controller.SetStage(popUp);
        popUp.show();
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
                        getClass().getClassLoader().getResource("holidayview/addevent.fxml")
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
