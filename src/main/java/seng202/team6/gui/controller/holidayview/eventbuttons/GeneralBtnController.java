package seng202.team6.gui.controller.holidayview.eventbuttons;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import seng202.team6.gui.controller.routefinder.ResultController;
import seng202.team6.gui.helper.NodeHelper;
import seng202.team6.model.events.General;

public class GeneralBtnController extends ResultController {

    @FXML
    Text Title;

    @FXML
    Label Time, Date;

    General generalEvent;

    private final String subFolder = "holidayview/eventbuttons";
    private final String viewInfoComponent = "generaleventinformation";

    /**
     * Sets the data of the FXML component as well as links the component to a General event object.
     * @param title General title
     * @param time General Time of departure
     * @param date General Date
     * @param generalEvent General object
     */
    public void setData(String title, String time, String date, General generalEvent) {
        Title.setText(title);
        Time.setText(time);
        Date.setText(date);

        this.generalEvent = generalEvent;
    }

    /**
     * Called when the user wants to view information about the event in question.
     * This method creates a new stage and shows it to the user displaying all relevant information about the event.
     */
    @FXML
    private void OnViewInfoButtonClicked() {
        try {
            // Set up the event controller object
            Pair<BorderPane, GeneralEventInformationController> pair = NodeHelper.LoadNode(subFolder, viewInfoComponent);
            BorderPane infoBorderPane = pair.getKey();
            GeneralEventInformationController EventInformationController = pair.getValue();
            EventInformationController.setGenEvent(this.generalEvent);

            // Create and Show scene with events information
            Scene viewInfoScene = new Scene(infoBorderPane);
            viewInfoScene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
            Stage newStage = new Stage();
            newStage.setTitle(String.format("%s", this.generalEvent.getTitle()));
            newStage.setScene(viewInfoScene);
            newStage.show();
            EventInformationController.setStage(newStage);
            EventInformationController.populateInfo();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
