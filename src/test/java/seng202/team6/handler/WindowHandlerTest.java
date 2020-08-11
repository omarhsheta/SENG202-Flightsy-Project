package seng202.team6.handler;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

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
        Scene scene = new Scene(new Group());
        Scene sceneTwo = new Scene(new Group());
        Scene sceneThree = new Scene(new Group());

        //Add scenes in random order
        handler.AddWindow(sceneTwo);
        handler.AddWindow(scene, 0);
        handler.AddWindow(sceneThree, 0);

        //Put scenes in same order as above
        ArrayList<Scene> ordering = new ArrayList<>();
        ordering.add(sceneThree);
        ordering.add(scene);
        ordering.add(sceneTwo);

        //Check ordering is the same for windowHandler and the above.
        assertEquals(ordering, WindowHandler.GetInstance().GetWindowsList());
    }
}
