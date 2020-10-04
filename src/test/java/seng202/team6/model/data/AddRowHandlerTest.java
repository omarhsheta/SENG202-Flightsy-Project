package seng202.team6.model.data;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class AddRowHandlerTest {

    private AddRowHandler addRowHandler;

    @Before
    public void InitializeTest() {
        addRowHandler = new AddRowHandler();
    }


    @Test
    public void TestCheckAirportCorrectFields() {
        ArrayList<String> testAirport = new ArrayList<>(Arrays.asList("2009","Christchurch Intl","Christchurch",
                "New Zealand","CHC","NZCH","-43.489358","172.532225","123","12","Z","Pacific/Auckland"));
        assertEquals(null, addRowHandler.CheckAirport(testAirport));
    }

    @Test
    public void TestCheckAirlineCorrectFields() {
        ArrayList<String> testAirline = new ArrayList<>(Arrays.asList("345","Air New Zealand",null,"NZ","ANZ",
                "NEW ZEALAND","New Zealand","Y"));
        assertEquals(null, addRowHandler.CheckAirline(testAirline));
    }

    @Test
    public void TestCheckRouteCorrectFields() {
        ArrayList<String> testRoute = new ArrayList<>(Arrays.asList("AA","24","CHC","2009","SYD","3361","Y","0","737"));
        assertEquals(null, addRowHandler.CheckRoute(testRoute));
    }



    @Test
    public void TestCheckAirportNullFields() {
        ArrayList<String> testAirport = new ArrayList<>(Arrays.asList(null, null, null, null, null, null, null, null,
                null, null, null));
        String errorMessage = "Check the Airport ID field and try again";
        assertEquals(errorMessage, addRowHandler.CheckAirport(testAirport));
    }

    @Test
    public void TestCheckAirlineNullFields() {
        ArrayList<String> testAirline = new ArrayList<>(Arrays.asList(null, null, null,
                null, null, null, null, null));
        String errorMessage = "Check the Airline ID field and try again";
        assertEquals(errorMessage, addRowHandler.CheckAirline(testAirline));
    }

    @Test
    public void TestCheckRouteNullFields() {
        ArrayList<String> testRoute = new ArrayList<>(Arrays.asList(null, null, null,
                null, null, null, null, null, null));
        String errorMessage = "Check the Airline ID field and try again";
        assertEquals(errorMessage, addRowHandler.CheckRoute(testRoute));
    }

}
