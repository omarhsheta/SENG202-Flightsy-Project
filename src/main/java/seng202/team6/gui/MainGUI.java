package seng202.team6.gui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainGUI extends Application
{
    /*
        This is the example BootstrapFX GUI
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Code Way
//        Panel panel = new Panel("This is the title");
//        panel.getStyleClass().add("panel-primary");
//        BorderPane content = new BorderPane();
//        content.setPadding(new Insets(20));
//        Button button = new Button("Hello BootstrapFX");
//        button.getStyleClass().setAll("btn","btn-danger");
//        content.setCenter(button);
//        panel.setBody(content);
//
//        Scene scene = new Scene(panel);
//        scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
//
//        primaryStage.setTitle("BootstrapFX");
//        primaryStage.setScene(scene);
//        primaryStage.sizeToScene();
//        primaryStage.show();

        // FXML Way (example from lab)
        Parent root = FXMLLoader.load(getClass().getResource("/mainWindow.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
        primaryStage.setTitle("SENG202");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
