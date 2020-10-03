package seng202.team6.gui.controller.holidayview;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seng202.team6.gui.controller.HolidayAgendaController;

public class NewHolidayController {
    @FXML
    private TextField nameField;

    private Stage stage;

    @FXML
    public void OnCreateNewHoliday() {
        if (nameField.getText() == null || nameField.getText().equals("")) {
            return;
        }
        HolidayAgendaController.GetInstance().CreateNewHoliday(nameField.getText());
        CloseStage();
    }

    public void SetStage(Stage stage) {
        this.stage = stage;
    }

    private void CloseStage() {
        if (this.stage != null) {
            stage.close();
        }
    }
}
