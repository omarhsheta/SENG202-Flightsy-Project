package seng202.team6.gui.controller.holidayview.eventbuttons;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seng202.team6.model.events.CarTrip;

import java.time.format.DateTimeFormatter;

public class CarTripEventInformationController {

    @FXML
    Text titleInfo, Time, leaving, destination, notes;

    @FXML
    Button closeButton;

    private CarTrip carTrip;
    private Stage stage;

    /**
     * Called to populate the cartripinformation FXML file with information about the event.
     */
    public void populateInfo() {
        String deptTime = carTrip.getDateTime().format(DateTimeFormatter.ofPattern("hh:mm a"));
        String deptDate = carTrip.getDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyy"));

        titleInfo.setText(carTrip.getTitle());
        Time.setText(deptDate + " at " + deptTime);
        leaving.setText("Leaving: " + carTrip.getOCity() + ", " + carTrip.getOCountry());
        destination.setText("To: " + carTrip.getDCity() + ", " + carTrip.getDCountry());
        notes.setText("Notes: " + carTrip.getNotes());
    }

    /**
     * Sets the variable carTrip to the CarTrip object supplied
     * @param carTrip CarTrip object supplied
     */
    public void setCarTrip(CarTrip carTrip) {
        this.carTrip = carTrip;
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
