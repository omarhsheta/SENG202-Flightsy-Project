package seng202.team6.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seng202.team6.gui.components.FilterTextField;
import seng202.team6.model.data.DataHandler;
import seng202.team6.model.data.Filter;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FindRoutesController implements Initializable
{
    @FXML
    private WebView webView;

    private WebEngine webEngine;
    private final String mapHTML = "/map/main.html";

    @FXML
    private Pane resultsPane;

    @FXML
    private Pane filterPane;
    private final ArrayList<FilterTextField> filterTextFields = new ArrayList<>();

    private MapController controller;

    /**
     * Initialize trip window component
     * @param url Url
     * @param resourceBundle ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        webEngine = webView.getEngine();
        webEngine.load(getClass().getResource(mapHTML).toExternalForm());

        controller = new MapController(webEngine);

        //Grab all text filter components
        for (Node node : filterPane.getChildren()) {
            if (node != null && node.getClass() == FilterTextField.class) {
                filterTextFields.add((FilterTextField) node);
            }
        }
    }

    @FXML
    private void OnFilterButtonClicked() {
        ArrayList<Filter> filters = new ArrayList<>();
        for (FilterTextField box : this.filterTextFields) {
            if (!box.getText().equals("")) {
                String filterString = String.format(box.GetFilter(), box.getText());
                filters.add(new Filter(filterString, "AND"));
            }
        }

        try {
            controller.ClearAll();
            controller.DrawAirportMarks(DataHandler.GetInstance().FetchAirports(filters));
        } catch (Exception ignored) {
        }
    }
}
