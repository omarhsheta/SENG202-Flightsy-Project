package seng202.team6.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class FindRoutesController implements Initializable
{
    @FXML
    private WebView webView;

    private WebEngine webEngine;
    private final String mapHTML = "/map/main.html";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        webEngine = webView.getEngine();
        webEngine.load(getClass().getResource(mapHTML).toExternalForm());
    }
}
