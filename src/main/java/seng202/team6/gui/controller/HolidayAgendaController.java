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
import seng202.team6.model.data.DataImportHandler;
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
     * Method to reset the combo box items on the view
     */
    private void RepopulateComboBox() {
        holidaySelectChoiceBox.getItems().clear();
        for (HolidayPlan holiday : holidays) {
            holidaySelectChoiceBox.getItems().add(holiday.GetName());
        }

        if (holidays.size() > 0) {
            holidaySelectChoiceBox.getSelectionModel().select(0);
        }
    }

    /**
     * Called when WebEngine is finished loading
     */
    private void OnLoad() {
        finishedLoading = true;
        mapHelper.ClearAll();
        /* Show flights on map */
        if (holidays.size() > 0) {
            for (Flight flight : holidays.get(selectedHolidayIndex).GetFlights()) {
                ArrayList<Airport> airports = flight.getRoute().GetAirports();
                mapHelper.DrawAirportMarks(airports);
                mapHelper.DrawLineBetween(airports);
            }
        }
    }

    public ArrayList<HolidayPlan> GetHolidays() {
        return holidays;
    }

    /**
     * A method that is called every time the choicebox value changes. This method changes the holiday visible to the recently selected holiday
     * @param index Index to swap to
     */
    public void ChangeHoliday(int index) {
        selectedHolidayIndex = index;
        if (index < 0) {
            return;
        }

        ShowHoliday(holidays.get(index));

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

        RepopulateComboBox();
    }

    /**
     * Create a new holiday
     * @param name Name of holiday
     */
    public void CreateNewHoliday(String name) {
        HolidayPlan plan = new HolidayPlan(name);
        String planName = plan.GetName();
        if (planName.isBlank() || planName.isEmpty()) {
            return;
        }

        for (HolidayPlan oldPlans : holidays) {
            if (oldPlans.GetName().equals(planName)) {
                System.out.println("Failed to create holiday, name already taken.");
                return;
            }
        }
        plan.SaveHoliday();
        holidays.add(plan);
        holidaySelectChoiceBox.getItems().add(planName);
        holidaySelectChoiceBox.getSelectionModel().select(planName);
    }

    /**
     * Add event to holiday
     * @param event Event to add
     * @param <T> Event to add
     */
    public <T extends Event> void AddToHoliday(T event) {
        if (holidays.size() == 0) {
            return;
        }

        HolidayPlan holiday = holidays.get(selectedHolidayIndex);
        if (event.getClass() == General.class) {
            holiday.AddItinerary((General) event);
        } else if (event.getClass() == Flight.class) {
            holiday.AddFlight((Flight) event);
        } else if (event.getClass() == CarTrip.class) {
            holiday.AddCarTrip((CarTrip) event);
        }
        holiday.SaveHoliday();
        ShowHoliday(holiday);
    }

    /**
     * A method for displaying the holidays events in the GUI. This method appends Panes of the events to a VBox is
     * sorted order by date and time.
     */
    private void ShowHoliday(HolidayPlan holiday) {
        eventsVBox.getChildren().clear();

        ArrayList<Event> allEvents = new ArrayList<>();
        allEvents.addAll(holiday.GetItineraries());
        allEvents.addAll(holiday.GetFlights());
        allEvents.addAll(holiday.GetCarTrips());

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
            for (Flight flight : holiday.GetFlights()) {
                ArrayList<Airport> airports = flight.getRoute().GetAirports();
                mapHelper.DrawAirportMarks(airports);
                mapHelper.DrawLineBetween(airports);
            }
        }
    }

    /**
     * A method to create a new holiday
     * @throws IOException IOException
     */
    @FXML
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

    /**
     * Called when the user wishes to delete the currently selected holiday
     */
    @FXML
    public void OnDeleteEventButtonClicked() {
        if (holidays.size() == 0 || holidaySelectChoiceBox.getSelectionModel().isEmpty()) {
            return;
        }
        HolidayPlan selected = holidays.get(selectedHolidayIndex);
        String name = selected.GetName();
        holidays.remove(selected);

        try {
            DataImportHandler.GetInstance().DeleteHolidayPlan(name);
            DataHandler.GetInstance().DeleteDataFile(name + ".json");
        } catch (Exception e) {
            System.out.println("Failed to delete holiday from database. \n" + e.toString());
        }

        eventsVBox.getChildren().clear();
        holidaySelectChoiceBox.getSelectionModel().clearSelection();
        RepopulateComboBox();
    }
}
