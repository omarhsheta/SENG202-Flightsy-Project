package seng202.team6.gui.components;

import javafx.beans.NamedArg;
import javafx.scene.control.TextField;
import seng202.team6.model.data.Filter;

import java.util.ArrayList;

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

    /**
     * GetFilters method that takes a parameter <code>filterTextFields</code> which is an ArrayList of FilterTextField objects,
     * and takes the filter formatting and text from the object. It then creates a Filter object from this and adds the
     * filter to an ArrayList of Filter objects and returns the ArrayList.
     * @param filterTextFields An ArrayList of FilterTextFields
     * @return An ArrayList of Filter objects.
     */
    public static ArrayList<Filter> ExtractFilters(ArrayList<FilterTextField> filterTextFields) {
        ArrayList<Filter> filters = new ArrayList<>();

        for (FilterTextField box : filterTextFields) {
            if (!box.getText().equals("")) {
                String filterString = String.format(box.GetFilter(), box.getText());
                filters.add(new Filter(filterString, "AND"));
            }
        }
        return filters;
    }

    /**
     * GetFilters method that takes a parameter <code>filterTextFields</code> which is a FilterTextField object,
     * and takes the filter formatting and text from the object. It then creates a Filter object from this and adds the
     * filter to an ArrayList of Filter objects and returns the ArrayList.
     * @param filterTextField A FilterTextField object
     * @return An ArrayList of Filter objects.
     */
    public static ArrayList<Filter> ExtractFilters(FilterTextField filterTextField) {
        ArrayList<Filter> filters = new ArrayList<>();

        if (!filterTextField.getText().equals("")) {
            String filterString = String.format(filterTextField.GetFilter(), filterTextField.getText());
            filters.add(new Filter(filterString, "AND"));
        }
        return filters;
    }
}
