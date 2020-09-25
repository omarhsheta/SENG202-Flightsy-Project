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
import seng202.team6.model.data.DataHandler;
import seng202.team6.model.events.CarTrip;
import seng202.team6.model.events.Flight;
import seng202.team6.model.events.General;
import seng202.team6.model.user.HolidayPlan;

import java.net.URL;
import java.util.ArrayList;
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
        ; // to get index of tab selected
    }


    public void addToHoliday(Button event) {
        int currHoliday = holidaysTabPane.getSelectionModel().getSelectedIndex();
        holidays.get(currHoliday);
    }

    private void showHoliday() {
        int currHoliday = holidaysTabPane.getSelectionModel().getSelectedIndex();
        HolidayPlan holiday = holidays.get(currHoliday);
        eventsVBox.getChildren().clear();

        for(General itinerary: holiday.getItineraries()) {
            eventsVBox.getChildren().add(itinerary.toPane());
        }
        for(Flight flight: holiday.getFlights()) {
            eventsVBox.getChildren().add(flight.toPane());
        }
        for(CarTrip carTrip: holiday.getCarTrips()) {
            eventsVBox.getChildren().add(carTrip.toPane());
        }
    }
}
