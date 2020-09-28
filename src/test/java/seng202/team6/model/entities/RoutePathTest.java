package seng202.team6.model.entities;

import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Test the routepath methods
 */
public class RoutePathTest {
    RoutePath path1;
    RoutePath path2;

    @Before
    public void Initialize() {
        ArrayList<Pair<Double, Double>> coords = new ArrayList<>();
        coords.add(new Pair<>(0d, 0d));
        coords.add(new Pair<>(100d, 100d));
        coords.add(new Pair<>(75.24d, 74.24d));
        coords.add(new Pair<>(0d, 0d));
        path1 = new RoutePath("AKL", "CHC", coords);

        ArrayList<Pair<Double, Double>> coords2 = new ArrayList<>();
        path2 = new RoutePath("SYD", "MEL", coords2);
    }

    @Test
    public void TestJSONSerialize() {
        String expected = "{\"source\":\"AKL\",\"destination\":\"CHC\"," +
                            "\"coordinates\":[{\"key\":0.0,\"value\":0.0}," +
                            "{\"key\":100.0,\"value\":100.0}," +
                            "{\"key\":75.24,\"value\":74.24}," +
                            "{\"key\":0.0,\"value\":0.0}]}";
        assertEquals(expected, path1.ToJson());

        expected = "{\"source\":\"SYD\",\"destination\":\"MEL\",\"coordinates\":[]}";
        assertEquals(expected, path2.ToJson());
    }

    @Test
    public void TestJSONDeserialize() {
        String json = "{\"source\":\"AKL\",\"destination\":\"CHC\"," +
                "\"coordinates\":[{\"key\":0.0,\"value\":0.0}," +
                "{\"key\":100.0,\"value\":100.0}," +
                "{\"key\":75.24,\"value\":74.24}," +
                "{\"key\":0.0,\"value\":0.0}]}";

        RoutePath deserialized = RoutePath.FromJSON(json);
        assertEquals("AKL", deserialized.source);
        assertEquals("CHC", deserialized.destination);
        assertEquals(4, deserialized.coordinates.size());
        assertEquals(new Pair<>(75.24, 74.24), deserialized.coordinates.get(2));

        json = "{\"source\":\"SYD\",\"destination\":\"MEL\",\"coordinates\":[]}";
        deserialized = RoutePath.FromJSON(json);
        assertEquals("SYD", deserialized.source);
        assertEquals("MEL", deserialized.destination);
        assertEquals(0, deserialized.coordinates.size());
    }
}
