package seng202.team6.gui.components;

import javafx.beans.NamedArg;
import javafx.scene.control.TextField;

/**
 * Extension on JavaFX TextField to support filtering
 */
public class FilterTextField extends TextField {
    private final String filter;

    /**
     * FilterTextField component
     * @param filter Injected through fxml
     */
    public FilterTextField(@NamedArg("filter") String filter) {
        this.filter = filter;
    }

    /**
     * Get filter associated with this component
     * @return Filter string
     */
    public String GetFilter() {
        return this.filter;
    }
}
