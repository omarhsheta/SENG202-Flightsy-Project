package seng202.team6.gui.controller.holidayview.eventbuttons;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seng202.team6.model.events.CarTrip;
import seng202.team6.model.events.General;

import java.time.format.DateTimeFormatter;

public class GeneralEventInformationController {

    @FXML
    Text titleInfo, DateTime, CityCountry, notes;

    @FXML
    Button closeButton;

    private General genEvent;
    private Stage stage;

    /**
     * Called to populate the generalEventInformation FXML file with information about the event.
     */
    public void populateInfo() {
        String deptTime = genEvent.getDateTime().format(DateTimeFormatter.ofPattern("hh:mm a"));
        String deptDate = genEvent.getDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyy"));

        titleInfo.setText(genEvent.getTitle());
        DateTime.setText(deptDate + " at " + deptTime);
        CityCountry.setText("At: " + genEvent.getCity() + ", " + genEvent.getCountry());
        notes.setText("Notes: " + genEvent.getNotes());
    }

    /**
     * Sets the variable genEvent to the General object supplied
     * @param genEvent General object supplied
     */
    public void setGenEvent(General genEvent) {
        this.genEvent = genEvent;
    }


    /**
     * sets the Stage variable stage to the supplied Stage.
     * @param newStage Stage object supplied
     */
    public void setStage(Stage newStage) {
        stage = newStage;
    }

    /**
     * Called when the user wants to close the window, calls stage.close().
     */
    @FXML
    private void closeWindow() {
        stage.close();
    }
}
