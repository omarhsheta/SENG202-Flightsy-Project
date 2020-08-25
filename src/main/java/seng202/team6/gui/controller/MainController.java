package seng202.team6.gui.controller;

import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seng202.team6.model.entities.Airport;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable
{
    @FXML
    private WebView webView2;

    @FXML
    private Button button;

    private WebEngine webEngine;
    private final String mapHTML = "/map/main.html";
    private MapController controller;

    /**
     * Called when this FXML page is loaded
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        webEngine = webView2.getEngine();
        webEngine.load(getClass().getResource(mapHTML).toExternalForm());

        //Temporary test to show how to use MapController
        controller = new MapController(webEngine);

        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                OnLoad();
            }
        });
        //End temporary
    }

    /**
     * Called when WebEngine is finished loading
     */
    private void OnLoad() {
        ArrayList<Airport> airports = new ArrayList<>();
        airports.add(new Airport(1, "Christchurch International", "Christchurch", "New Zealand",
                "CHC", "NZCH", -43.4876f, 172.5374f, 37, 12, 'Y'));
        controller.DrawAirportMarks(airports);
    }

}
