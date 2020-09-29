package seng202.team6.gui.controller.routefinder;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seng202.team6.model.entities.RoutePath;


public class FlightPathResultController extends ResultController {

    @FXML
    private Label flightLabel;

    private RoutePath path;


    /**
     * Set Route Path info
     * @param route RoutePath object
     */
    public void SetRoutePath(RoutePath route) {
        flightLabel.setText(String.format("%s TO %s", route.GetSource(), route.GetDestination()));
        path = route;
    }

    /**
     * Called when the user wants to view the airport in question
     * This method moves the map viewpoint to the airport in question.
     */
    @FXML
    private void OnViewButtonClicked()
    {
        if (mapHelper == null) {
            return;
        }
        mapHelper.ClearAll();
        mapHelper.DrawRoutePath(path);
    }
}
