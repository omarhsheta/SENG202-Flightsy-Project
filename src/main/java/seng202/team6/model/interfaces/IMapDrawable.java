package seng202.team6.model.interfaces;

/**
 * Interface specifying that all classes that are drawable on
 * the map must implement these functions
 */
public interface IMapDrawable {
    /**
     * Convert object to javascript string representation
     * for google maps display purposes
     * @return String JS String of data
     */
    String ConvertToJavascriptString();
}
