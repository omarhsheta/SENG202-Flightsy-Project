package seng202.team6.gui.controller.routefinder;

import seng202.team6.model.MapHelper;

/**
 * Base class for results
 */
public class ResultController {
    protected static MapHelper mapHelper;

    /**
     * Set map that result links to
     * @param controller MapController object
     */
    public static void SetMap(MapHelper controller) {
        mapHelper = controller;
    }
}
