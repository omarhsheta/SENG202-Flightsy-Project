package seng202.team6;

import javafx.application.Application;
import seng202.team6.gui.MainGUI;

/*
 * Main Entry point to program
 * Initializes the GUI.
 */
public class App
{
    public static void main( String[] args )
    {
        //Launch javaFX GUI
        Application.launch(MainGUI.class, args);
    }
}
