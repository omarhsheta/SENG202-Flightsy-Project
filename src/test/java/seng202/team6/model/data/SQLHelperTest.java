package seng202.team6.model.data;

import org.junit.Test;
import seng202.team6.model.entities.Airport;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class SQLHelperTest {
    /**
     * Test no filter query
     */
    @Test
    public void TestNoFilterQuery() {
        String expected = "SELECT * FROM test;";
        String query = SQLHelper.ExtractQuery("test", null);
        assertEquals(expected, query);
    }

    /**
     * Test single filter query
     */
    @Test
    public void TestSingleFilterQuery() {
        String expected = "SELECT * FROM test WHERE power > 9000;";
        ArrayList<Filter> testFilters = new ArrayList<>();
        testFilters.add(new Filter("power > 9000", "AND"));
        String query = SQLHelper.ExtractQuery("test", testFilters);
        assertEquals(expected, query);
    }

    /**
     * Test multiple query filtering
     */
    @Test
    public void TestMultipleFilterQueries() {
        String expected = "SELECT * FROM test WHERE filterone = 'hello' AND filtertwo = 'world' OR filterthree = '!!';";

        ArrayList<Filter> testFilters = new ArrayList<>();
        testFilters.add(new Filter("filterone = 'hello'", "AND"));
        testFilters.add(new Filter("filtertwo = 'world'", "OR"));
        testFilters.add(new Filter("filterthree = '!!'", "OR"));
        String query = SQLHelper.ExtractQuery("test", testFilters);
        assertEquals(expected, query);
    }

    /**
     * Test airport IATA to SQL list
     */
    @Test
    public void TestIATAMultiple() {
        ArrayList<Airport> airports = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Airport testPort = new Airport(0, null, null, null, "test",
                    null, (float)0, (float)0, 0, 0, 'T');
            airports.add(testPort);
        }

        String result = SQLHelper.GetAirportIATAList(airports);
        assertEquals("'test', 'test', 'test', 'test', 'test'", result);
    }

    /**
     * Test airport IATA to SQL list
     */
    @Test
    public void TestIATANone() {
        ArrayList<Airport> airports = new ArrayList<>();
        String result = SQLHelper.GetAirportIATAList(airports);
        assertEquals("", result);
    }
}
