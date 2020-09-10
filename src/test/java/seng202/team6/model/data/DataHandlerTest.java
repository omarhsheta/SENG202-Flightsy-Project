package seng202.team6.model.data;

import org.junit.Before;
import org.junit.Test;
import seng202.team6.model.entities.*;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class DataHandlerTest {

    @Before
    public void InitializeTest(){}


    /**
     * Test inserting one airline into the database
     */
    @Test
    public void testInsertOneAirline() {
        // insert one airline
    }

    /**
     * Test inserting two airlines into the database
     */
    @Test
    public void testInsertTwoAirlines() {
        // insert two airline
    }

    /**
     * Test inserting five airlines into the database
     */
    @Test
    public void testInsertFiveAirlines() {
        // insert five airlines
    }

    @Test
    public void testInsertEmptyAirline() {
        // insert empty airline
    }

    @Test
    public void testInsertInvalidAirline() {
        // insert invalid airline
    }

    @Test
    public void testInsertTwoValidOneInvalidAirlines() {
        // insert two valid, one invalid airlines
    }


    @Test
    public void testInsertOneAirport() {
        // insert one airport
    }

    @Test
    public void testInsertTwoAirports() {
        // insert two airports
    }

    @Test
    public void testInsertFiveAirports() {
        // insert five airports
    }

    @Test
    public void testInsertEmptyAirport() {
        // insert empty airport
    }

    @Test
    public void testInsertInvalidAirport() {
        // insert invalid airport
    }

    @Test
    public void testInsertTwoValidOneInvalidAirports() {
        // insert two valid, one invalid airports
    }


    @Test
    public void testInsertOneRoute() {
        // insert one route
    }

    @Test
    public void testInsertTwoRoutes() {
        // insert two routes
    }

    @Test
    public void testInsertFiveRoutes() {
        // insert five routes
    }

    @Test
    public void testInsertEmptyRoute() {
        // insert empty route
    }

    @Test
    public void testInsertInvalidRoute() {
        // insert invalid route
    }

    @Test
    public void testInsertTwoValidOneInvalidRoutes() {
        // insert two valid, one invalid routes
    }


    @Test
    public void testUpdateOneAirline() {
        // update one airline
    }

    @Test
    public void testUpdateTwoAirlines() {
        // update two airlines
    }

    @Test
    public void testUpdateFiveAirlines() {
        // update five airlines
    }

    @Test
    public void testUpdateAirlineEmpty() {
        // update an airline with empty parameters

    }
    @Test
    public void testUpdateAirlineOneCharIATA() {
        // update airline with invalid IATA with one char
    }

    @Test
    public void testUpdateAirlineThreeCharIATA() {
        // update airline with invalid IATA with three chars
    }

    @Test
    public void testUpdateAirlineTwoCharICAO() {
        // update airline with invalid ICAO with two chars
    }

    @Test
    public void testupdateAirlineFourCharICAO() {
        // update airline with invalid ICAO with four chars
    }

    @Test
    public void testInvalidAirlineParams() {
        // update airline with invalid parameter data
    }


    @Test
    public void testUpdateOneAirport() {
        // update one airline
    }

    @Test
    public void testUpdateTwoAirports() {
        // update two airlines
    }

    @Test
    public void testUpdateFiveAirports() {
        // update five airlines
    }

    @Test
    public void testUpdateAirportEmpty() {
        // update an airline with empty parameters

    }
    @Test
    public void testUpdateAirportTwoCharIATA() {
        // update airline with invalid IATA with one char
    }

    @Test
    public void testUpdateAirportFourCharIATA() {
        // update airline with invalid IATA with three chars
    }

    @Test
    public void testUpdateAirportThreeCharICAO() {
        // update airline with invalid ICAO with two chars
    }

    @Test
    public void testupdateAirportFiveCharICAO() {
        // update airline with invalid ICAO with four chars
    }

    @Test
    public void testInvalidAirportParams() {
        // update airline with invalid parameter data
    }


    @Test
    public void testUpdateOneRoute() {
        // update one route
    }

    @Test
    public void testUpdateTwoRoutes() {
        // update two routes
    }

    @Test
    public void testUpdateFiveRoutes() {
        // update five routes
    }

    @Test
    public void testUpdateRouteEmpty() {
        // update a route with empty parameters
    }

    @Test
    public void testUpdateSourceThreeCharICAO() {
        // update route with invalid source airport IATA with three chars
    }

    @Test
    public void testUpdateSourceFiveCharICAO() {
        // update route with invalid source airport IATA with five chars
    }

    @Test
    public void testUpdateDestinationThreeCharICAO() {
        // update route with invalid destination airport IATA with three chars
    }

    @Test
    public void testUpdateDestinationFiveCharICAO() {
        // update route with invalid destination airport IATA with five chars
    }

    @Test
    public void testInvalidRouteParams() {
        // update route with invalid parameter data
    }


    @Test
    public void testDeleteOneAirline() {
        // delete one airline
    }

    @Test
    public void testDeleteTwoAirlines() {
        // delete two airlines
    }

    @Test
    public void testDeleteFiveAirlines() {
        // delete five airlines
    }

    @Test
    public void testDeleteAirlineEmptyID() {
        // delete airline where a null AirlineID is provided
    }

    @Test
    public void testDeleteAirlineInvalidID() {
        // delete airline where an invalid AirlineID is provided
    }


    @Test
    public void testDeleteOneAirport() {
        // delete one airline
    }

    @Test
    public void testDeleteTwoAirports() {
        // delete two airlines
    }

    @Test
    public void testDeleteFiveAirports() {
        // delete five airlines
    }

    @Test
    public void testDeleteAirportEmptyID() {
        // delete airline where a null AirlineID is provided
    }

    @Test
    public void testDeleteAirportInvalidID() {
        // delete airline where an invalid AirlineID is provided
    }


    @Test
    public void testDeleteOneRoute() {
        // delete one route
    }

    @Test
    public void testDeleteTwoRoutes() {
        // delete two routes
    }

    @Test
    public void testDeleteFiveRoutes() {
        // delete five routes
    }

    @Test
    public void testDeleteRouteEmptyID() {
        // delete route where a null AirlineID is provided
    }

    @Test
    public void testDeleteRouteInvalidAirlineID() {
        // delete airline where an invalid AirlineID is provided
    }

    @Test
    public void testDeleteRouteInvalidSourceID() {
        // delete airline where an invalid SourceAirportID is provided
    }

    @Test
    public void testDeleteRouteInvalidDestinationID() {
        // delete airline where an invalid DestinationAirportID is provided
    }
}
