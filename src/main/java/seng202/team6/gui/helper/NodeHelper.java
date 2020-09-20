package seng202.team6.gui.helper;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import seng202.team6.gui.components.FilterTextField;

import java.io.IOException;
import java.util.ArrayList;

public class NodeHelper {
    /**
     * Create new node from FXML file
     * @param fxmlLocation Location of FXML file
     * @return FXML Component
     * @throws IOException IOException if file not found
     */
    public static <T, U> Pair<T, U> LoadNode(String subfolder, String fxmlLocation) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        T node = loader.load(NodeHelper.class.getResource("/" + subfolder + "/" + fxmlLocation + ".fxml").openStream());
        U controller = loader.getController();
        return new Pair<>(node, controller);
    }

    /**
     * Grab all filter box nodes from VBox
     * @param box Component to get all child filter text fields from
     * @return ArrayList of filter text fields
     */
    public static ArrayList<FilterTextField> GetAllNodes(VBox box) {
        ArrayList<FilterTextField> nodes = new ArrayList<>();
        for (Node node : box.getChildren()) {
            if (node != null && node.getClass() == FilterTextField.class) {
                nodes.add((FilterTextField) node);
            }
        }
        return nodes;
    }
}
