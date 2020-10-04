package seng202.team6.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainGUI extends Application {
    /**
     * Name of the GUI
     */
    private final String GUI_TITLE = "Flightsy - Travel Planner";
    private final int MIN_WIDTH = 1200;
    private final int MIN_HEIGHT = 715;

    private final String menubarFXML = "menubar";

    /**
     * Resources to load
     */
    private final String[] resourceFXML = new String[] {
            "holidayview/holidayview",
            "routefinder/routefinderview",
            "dataviewer/dataviewer"
    };

    /**
     * Main startup of GUI
     * @param primaryStage Autopassed variable from GUI Constructor
     */
    @Override
    public void start(Stage primaryStage) {
        //Root pane is borderpane, top is menubar, center is each pane
        BorderPane rootPane = new BorderPane();
        try {
            Node menuBar = FXMLLoader.load(getClass().getResource("/" + this.menubarFXML + ".fxml"));
            rootPane.setTop(menuBar);
        } catch (Exception e) {
            System.out.println("Failed to load menubar, \n" + e.toString());
        }

        //Instantiate WindowHandler singleton
        new WindowHandler(rootPane);

        //Load each FXML class once to have fast response time rather than on the fly.
        //MAYBE CHANGE if loading heavy classes
        try {
            for (String resource : this.resourceFXML) {
                Node source = FXMLLoader.load(getClass().getResource("/" + resource + ".fxml"));
                WindowHandler.GetInstance().AddWindow(resource, source);
            }
        } catch (Exception e) {
            System.out.println("Failed to load FXML window, \n" + e.toString());
        }

        //Set primaryScene to a new scene with rootPane as main content
        Scene primaryScene = new Scene(rootPane);
        primaryScene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");

        //Set variables
        primaryStage.setTitle(this.GUI_TITLE);
        primaryStage.initStyle(StageStyle.UTILITY);

        primaryStage.setMinHeight(this.MIN_HEIGHT);
        primaryStage.setHeight(this.MIN_HEIGHT);
        primaryStage.setMinWidth(this.MIN_WIDTH);
        primaryStage.setWidth(this.MIN_WIDTH);
        //primaryStage.setMaxHeight(1055); YET TO BE DISCUSSED DONT HATE ME SENPAI

        primaryStage.setScene(primaryScene);

        //Set active window
        WindowHandler.GetInstance().SetActiveWindow("holidayview/holidayview");
        primaryStage.show();
    }
}
