package seng202.team6.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import seng202.team6.model.data.CSVLoader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class DataViewerController implements Initializable
{
    @FXML
    private BorderPane borderPane;

    @FXML
    private TabPane tabbedPane;

    private CSVLoader csvLoader;

    /**
     * Resources to load
     */
    private final ArrayList<Pair<String, String>> resourceFXML = new ArrayList<>() {
        {
            add(new Pair<>("Airports", "airporttab"));
            add(new Pair<>("Airlines", "airlinetab"));
            add(new Pair<>("Routes", "routetab"));
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            //Load each FXML tab
            for (Pair<String, String> resourcePair : this.resourceFXML) {
                Node source = FXMLLoader.load(getClass().getResource("/dataviewer/" + resourcePair.getValue() + ".fxml"));
                Tab newTab = new Tab(resourcePair.getKey(), source);
                tabbedPane.getTabs().add(newTab);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        csvLoader = new CSVLoader();
    }

    /**
     * Opens native file explorer for user to select a CSV file to import to the database
     * @return File object of the selected file for import
     */
    private File SelectFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV File", "*.csv"),
                new FileChooser.ExtensionFilter("DAT File", "*.dat"),
                new FileChooser.ExtensionFilter("Text File", "*.txt"));

        return fileChooser.showOpenDialog(borderPane.getScene().getWindow());
    }


    /**
     * Gets Airport data file, passes to CSVLoader to get Airport objects,
     * then passes objects to DataHandler to add to database
     */
    @FXML
    public void AirportFileImport() {
        File selectedFile = SelectFile();
        if (selectedFile != null) {
            String filePath = selectedFile.getAbsolutePath();
            csvLoader.ImportCSVAirports(filePath);
        }
    }


    /**
     * Gets Airline data file, passes to CSVLoader to get Airline objects,
     * then passes objects to DataHandler to add to database
     */
    @FXML
    public void AirlineFileImport() {
        File selectedFile = SelectFile();
        if (selectedFile != null) {
            String filePath = selectedFile.getAbsolutePath();
            csvLoader.ImportCSVAirlines(filePath);

        }
    }


    /**
     * Gets Route data file, passes to CSVLoader to get Route objects,
     * then passes objects to DataHandler to add to database
     */
    @FXML
    public void RouteFileImport() {
        File selectedFile = SelectFile();
        if (selectedFile != null) {
            String filePath = selectedFile.getAbsolutePath();
            csvLoader.ImportCSVRoutes(filePath);

        }
    }

    /**
     * Gets Flight path data file, passes to CSVLoader to get Flight path object,
     * then passes objects to DataHandler to add to database
     */
    @FXML
    public void FlightPathFileImport() {
        File selectedFile = SelectFile();
        if (selectedFile != null) {
            String filePath = selectedFile.getAbsolutePath();
            csvLoader.ImportCSVRoutePath(filePath);
        }
    }


    /**
     * A function that creates a new stage for the user to manually add a row into the database
     * @throws IOException Exception
     */
    @FXML
    public void OpenPopUp() throws IOException {
        final Stage popUp = new Stage();
        popUp.initModality(Modality.APPLICATION_MODAL);
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(
                        getClass().getClassLoader().getResource("addrow.fxml")
                )
        );
        Scene popUpScene = new Scene(root);
        //Set variables
        popUp.setTitle("Add Row");
        popUpScene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
        popUp.setScene(popUpScene);
        popUp.show();
    }
}
