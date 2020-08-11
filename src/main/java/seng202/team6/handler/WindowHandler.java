package seng202.team6.handler;

import javafx.scene.Scene;
import javafx.stage.Stage;

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

    private Stage primaryStage;
    //List to hold all window GUI components
    private ArrayList<Scene> windowsList;

    /**
     * Constructor to ensure only one object,
     * This ensures singleton pattern not violated.
     * Silently fail if multiple WindowHandlers instantiated.
     */
    public WindowHandler(Stage primaryStage) {
        if (Instance != null) {
            return;
        }

        this.primaryStage = primaryStage;
        this.windowsList = new ArrayList<>();

        Instance = this;
    }

    /**
     * Set active window for primary stage
     * @param index Index of window to show
     */
    public void SetActiveWindow(int index)
    {
        primaryStage.setScene(this.windowsList.get(index));
    }

    /**
     * Set active window for primary stage
     * @param window Window to show
     */
    public void SetActiveWindow(Scene window)
    {
        primaryStage.setScene(window);
    }

    /**
     * Get all windows
     * @return List of windows
     */
    public ArrayList<Scene> GetWindowsList() {
        return this.windowsList;
    }

    /**
     * Add window to the end of the current window list
     * @param window Window to add
     */
    public void AddWindow(Scene window) {
        this.windowsList.add(window);
    }

    /**
     * Add window to specific index
     * @param window Window to add
     * @param index Index of window
     */
    public void AddWindow(Scene window, int index) {
        this.windowsList.add(index, window);
    }

    /**
     * Remove window object from list
     * @param window Window to remove
     */
    public void RemoveWindow(Scene window) {
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
