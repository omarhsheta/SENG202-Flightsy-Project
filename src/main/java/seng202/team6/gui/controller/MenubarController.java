package seng202.team6.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import seng202.team6.gui.WindowHandler;

import java.net.URL;
import java.util.ResourceBundle;

public class MenubarController implements Initializable
{
    @FXML
    Button flightPlannerButton;

    @FXML
    Button tripsButton;

    @FXML
    Button dataButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        flightPlannerButton.setOnMouseClicked(x -> WindowHandler.GetInstance().SetActiveWindow("main"));

        tripsButton.setOnMouseClicked(x -> WindowHandler.GetInstance().SetActiveWindow("findroutes"));

        dataButton.setOnMouseClicked(x -> WindowHandler.GetInstance().SetActiveWindow("dataviewer"));
    }
}
