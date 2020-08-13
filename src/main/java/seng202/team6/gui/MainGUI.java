package seng202.team6.gui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;

public class MainGUI extends Application
{
    /**
     * Name of the GUI
     */
    private final String GUI_TITLE = "Flightsy";

    /**
     * Resources to load
     */
    private final String[] resourceFXML = new String[] {
            "/main.fxml",
            "/findroutes.fxml",
    };

    /**
     * Main startup of GUI
     * @param primaryStage Autopassed variable from GUI Constructor
     * @throws IOException Thrown if .fxml files can't be found
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        //Root pane is borderpane, top is menubar, center is each pane
        BorderPane rootPane = new BorderPane();
        Node menuBar = FXMLLoader.load(getClass().getResource("/menubar.fxml"));
        rootPane.setTop(menuBar);

        //Instantiate WindowHandler singleton
        new WindowHandler(rootPane);

        //Load each FXML class once to have fast response time rather than on the fly.
        //MAYBE CHANGE if loading heavy classes
        for (String resource : this.resourceFXML) {
            Node source = FXMLLoader.load(getClass().getResource(resource));
            WindowHandler.GetInstance().AddWindow(source);
        }

        //Set primaryScene to a new scene with rootPane as main content
        Scene primaryScene = new Scene(rootPane);
        primaryScene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");

        //Set variables
        primaryStage.setTitle(this.GUI_TITLE);
        primaryStage.sizeToScene();
        primaryStage.setScene(primaryScene);

        //Set active window to the first one
        WindowHandler.GetInstance().SetActiveWindow(0);
        primaryStage.show();
    }
}
