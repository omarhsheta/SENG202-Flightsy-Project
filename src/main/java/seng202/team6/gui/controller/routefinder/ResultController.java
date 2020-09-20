package seng202.team6.gui.controller.routefinder;

import seng202.team6.gui.controller.MapController;

/**
 * Base class for results
 */
public class ResultController {
    protected static MapController mapController;

    /**
     * Set map that result links to
     * @param controller MapController object
     */
    public static void SetMap(MapController controller) {
        mapController = controller;
    }
}
