package seng202.team6.gui;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.scene.layout.Panel;
import seng202.team6.handler.WindowHandler;

public class MainGUI extends Application
{
    /*
        This is the example BootstrapFX GUI
     */
    @Override
    public void start(Stage primaryStage)
    {
        //Initialize WindowHandler singleton with stage.
        new WindowHandler(primaryStage);

        Panel panel = new Panel("This is the title");
        panel.getStyleClass().add("panel-primary");
        BorderPane content = new BorderPane();
        content.setPadding(new Insets(20));
        Button button = new Button("Hello BootstrapFX");
        button.getStyleClass().setAll("btn","btn-danger");
        content.setCenter(button);
        panel.setBody(content);

        Scene scene = new Scene(panel);
        scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");

        primaryStage.setTitle("BootstrapFX");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }
}
