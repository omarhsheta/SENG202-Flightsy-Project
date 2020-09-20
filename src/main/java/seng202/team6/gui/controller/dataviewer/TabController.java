package seng202.team6.gui.controller.dataviewer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import seng202.team6.gui.components.FilterTextField;
import seng202.team6.model.data.DataHandler;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Base controller for tabs
 */
public abstract class TabController<T> implements Initializable {
    @FXML
    protected TableView<T> table;

    protected DataHandler dataHandler;

    @FXML
    protected Pane filterPane;
    protected final ArrayList<FilterTextField> filterTextFields = new ArrayList<>();

    /**
     * Called when initializing this component (FXML Loading)
     * @param url Url
     * @param resourceBundle Resource Bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataHandler = DataHandler.GetInstance();
        SetCellFactories();
        //Grab all text filter components
        for (Node node : filterPane.getChildren()) {
            if (node != null && node.getClass() == FilterTextField.class) {
                filterTextFields.add((FilterTextField) node);
            }
        }
    }

    /**
     * Called to set cell values for the table view.
     */
    protected abstract void SetCellFactories();

    /**
     * FXML button action that takes place when the Filter button is clicked on the data view.
     * This function takes the filters from the GetFilters method and gets the filtered ArrayList from the DataHandler.
     * Then it inputs the data into the data viewer table.
     */
    @FXML
    protected abstract void OnFilterButtonClicked();
}
