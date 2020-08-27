package seng202.team6.model.data;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class DataHandlerTest
{
    private DataHandler handler;

    /**
     * Initialize singleton database connection
     */
    @Before
    public void InitializeTest() {
        handler = DataHandler.GetInstance();
    }

    /**
     * Test no filter query
     */
    @Test
    public void TestNoFilterQuery() {
        String expected = "SELECT * FROM test;";
        String query = handler.ExtractQuery("test", null);
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
        String query = handler.ExtractQuery("test", testFilters);
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
        String query = handler.ExtractQuery("test", testFilters);
        assertEquals(expected, query);
    }
}
