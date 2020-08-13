package seng202.team6.handler;

import javafx.scene.layout.Pane;
import org.junit.Before;
import org.junit.Test;
import seng202.team6.gui.WindowHandler;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Test file for WindowHandler.java
 */

public class WindowHandlerTest
{
    WindowHandler handler;

    @Before
    public void Init() {
        WindowHandler.ResetInstance();
        handler = new WindowHandler(null);
    }

    /**
     * Test singleton aspect of WindowHandler
     */
    @Test
    public void TestSingleton() {
        assertEquals(WindowHandler.GetInstance(), handler);
    }

    /**
     * Test trying to instantiate multiple singletons and
     * ensure the original is not overwritten.
     */
    @Test
    public void TestMultipleSingleton()
    {
        WindowHandler handlerTwo = new WindowHandler(null);
        WindowHandler handlerThree = new WindowHandler(null);

        assertEquals(WindowHandler.GetInstance(), handler);
    }

    /**
     * Test indexing of windowList
     */
    @Test
    public void TestIndexOrdering()
    {
        //Establish some scenes.
        Pane windowOne = new Pane();
        Pane windowTwo = new Pane();
        Pane windowThree = new Pane();

        //Add scenes in random order
        handler.AddWindow(windowTwo);
        handler.AddWindow(windowOne, 0);
        handler.AddWindow(windowThree, 0);

        //Put scenes in same order as above
        ArrayList<Pane> ordering = new ArrayList<>();
        ordering.add(windowThree);
        ordering.add(windowOne);
        ordering.add(windowTwo);

        //Check ordering is the same for windowHandler and the above.
        assertEquals(ordering, WindowHandler.GetInstance().GetWindowsList());
    }
}
