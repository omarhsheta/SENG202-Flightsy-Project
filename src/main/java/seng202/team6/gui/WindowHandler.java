package seng202.team6.gui;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;

/**
 * WindowHandler class to handle all windows for the GUI component of the program.
 */
public class WindowHandler
{
    //Static Instance object
    private static WindowHandler Instance;

    /**
     * Get singleton instance
     * @return Current WindowHandler object
     */
    public static WindowHandler GetInstance()
    {
        return Instance;
    }

    /**
     * Reset singleton class
     */
    public static void ResetInstance()
    {
        Instance = null;
    }

    private BorderPane parentPane;
    //List to hold all window GUI components
    private ArrayList<Node> windowsList;

    /**
     * Constructor to ensure only one object,
     * This ensures singleton pattern not violated.
     * Silently fail if multiple WindowHandlers instantiated.
     */
    public WindowHandler(BorderPane parent) {
        if (Instance != null) {
            return;
        }

        this.parentPane = parent;
        this.windowsList = new ArrayList<>();

        Instance = this;
    }

    /**
     * Get active window
     * @return Current window object
     */
    public Node GetActiveWindow()
    {
        return this.parentPane.getCenter();
    }

    /**
     * Set active window for primary stage
     * @param index Index of window to show
     */
    public void SetActiveWindow(int index)
    {
        this.parentPane.setCenter(this.windowsList.get(index));
    }

    /**
     * Set active window for primary stage
     * @param window Window to show
     */
    public void SetActiveWindow(Node window)
    {
        this.parentPane.setCenter(window);
    }

    /**
     * Get all windows
     * @return List of windows
     */
    public ArrayList<Node> GetWindowsList() {
        return this.windowsList;
    }

    /**
     * Add window to the end of the current window list
     * @param window Window to add
     */
    public void AddWindow(Node window) {
        this.windowsList.add(window);
    }

    /**
     * Add window to specific index
     * @param window Window to add
     * @param index Index of window
     */
    public void AddWindow(Node window, int index) {
        this.windowsList.add(index, window);
    }

    /**
     * Remove window object from list
     * @param window Window to remove
     */
    public void RemoveWindow(Node window) {
        this.windowsList.remove(window);
    }

    /**
     * Remove window at index
     * @param index Index to remove object from
     */
    public void RemoveWindow(int index) {
        this.windowsList.remove(index);
    }
}
