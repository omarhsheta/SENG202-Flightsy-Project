package seng202.team6.gui.controller.holidayview.eventbuttons;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import seng202.team6.gui.controller.routefinder.ResultController;
import seng202.team6.gui.helper.NodeHelper;
import seng202.team6.model.events.CarTrip;

public class DriveBtnController extends ResultController {

    @FXML
    Text Title, DesCity;

    @FXML
    Label Time, Date;

    CarTrip carTrip;

    private final String subFolder = "holidayview/eventbuttons";
    private final String viewInfoComponent = "cartripeventinformation";

    /**
     * Sets the data of the FXML component as well as links the component to a CarTrip object.
     * @param title CarTrip title
     * @param time CarTrip Time of departure
     * @param date CarTrip Date
     * @param dCity CarTrip Departure city
     * @param carTrip CarTrip object
     */
    public void setData(String title, String time, String date, String dCity, CarTrip carTrip) {
        Title.setText(title);
        Time.setText(time);
        Date.setText(date);
        DesCity.setText(dCity);

        this.carTrip = carTrip;
    }

    /**
     * Called when the user wants to view information about the event in question.
     * This method creates a new stage and shows it to the user displaying all relevant information about the route.
     */
    @FXML
    private void OnViewInfoButtonClicked() {
        try {
            // Set up the event controller object
            Pair<BorderPane, CarTripEventInformationController> pair = NodeHelper.LoadNode(subFolder, viewInfoComponent);
            BorderPane infoBorderPane = pair.getKey();
            CarTripEventInformationController EventInformationController = pair.getValue();
            EventInformationController.setCarTrip(this.carTrip);

            // Create and Show scene with events information
            Scene viewInfoScene = new Scene(infoBorderPane);
            viewInfoScene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
            Stage newStage = new Stage();
            newStage.setTitle(String.format("%s", this.carTrip.getTitle()));
            newStage.setScene(viewInfoScene);
            newStage.show();
            EventInformationController.setStage(newStage);
            EventInformationController.populateInfo();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
