package seng202.team6.model.data;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seng202.team6.model.entities.*;
import java.util.Random;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class DataHandlerTest {
    private Random random;
    private int randomBound = 10000000;
    private DataHandler dataHandler;

    private Airline testAirline1;
    private Airline testAirline2;
    private Airline testAirline3;
    private Airline testAirline4;
    private Airline testAirline5;
    private ArrayList<Airline> testAirlines;

    private Airport testAirport1;
    private Airport testAirport2;
    private Airport testAirport3;
    private Airport testAirport4;
    private Airport testAirport5;
    private ArrayList<Airport> testAirports;

    private Route testRoute1;
    private Route testRoute2;
    private Route testRoute3;
    private Route testRoute4;
    private Route testRoute5;
    private ArrayList<Route> testRoutes;

    @Before
    public void InitializeTest() {
        random = new Random();
        dataHandler = new DataHandler();

        testAirline1 = new Airline(random.nextInt(randomBound), "Virgin Airlines", "Virgin", "VI",
                "VIR", "VIRGIN", "Australia", 'Y');
        testAirline2 = new Airline(random.nextInt(randomBound), "Singapore Airlines", "Singapore", "SN",
                "SNG", "SINGAPORE", "Signapore", 'Y');
        testAirline3 = new Airline(random.nextInt(randomBound), "Qatar Airways", "Qatar", "QA",
                "QAT", "QATAR", "Qatar", 'Y');
        testAirline4 = new Airline(random.nextInt(randomBound), "Emirates", "Emirates", "EM",
                "EMI", "EMIRATES", "United Arab Emirates", 'Y');
        testAirline5 = new Airline(random.nextInt(randomBound), "Lufthansa", "Luft", "LF",
                "LFT", "LUFTHANSA", "Germany", 'Y');
        testAirlines = new ArrayList<Airline>();
        testAirlines.add(testAirline1);
        testAirlines.add(testAirline2);
        testAirlines.add(testAirline3);
        testAirlines.add(testAirline4);
        testAirlines.add(testAirline5);

        testAirport1 = new Airport(random.nextInt(randomBound), "London Heathrow Airport", "London", "England",
                "LHR", "LOND", (float)51.470020, (float)-0.454295, 25, 1, 'U');
        testAirport2 = new Airport(random.nextInt(randomBound), "Los Angeles Airport", "Los Angeles", "United States of America",
                "LAX", "LOSX", (float)33.94279, (float)-118.410042, 38, -7, 'U');
        testAirport3 = new Airport(random.nextInt(randomBound), "Tokyo Haneda Airport", "Tokyo", "Japan",
                "HND", "HNDA", (float)35.5494, (float)139.7798, 11, 9, 'A');
        testAirport4 = new Airport(random.nextInt(randomBound), "Amsterdam Airport Schipol", "Amsterdam", "Netherlands",
                "AMS", "AMSD", (float)52.3105, (float)4.7683, -3, 1, 'A');
        testAirport5 = new Airport(random.nextInt(randomBound), "Hong Kong Airport", "Hong Kong", "Hong Kong",
                "HKG", "HGKG", (float)22.3080, (float)113.9185, 9, 8, 'A');
        testAirports = new ArrayList<Airport>();
        testAirports.add(testAirport1);
        testAirports.add(testAirport2);
        testAirports.add(testAirport3);
        testAirports.add(testAirport4);
        testAirports.add(testAirport5);

        testRoute1 = new Route(testAirline1.getAirlineID(), testAirline1.getName(), testAirport1.getName(), testAirport1.getAirportID(),
                testAirport2.getName(), testAirport2.getAirportID(), null, 0, "CR2");
        testRoute2 = new Route(testAirline2.getAirlineID(), testAirline2.getName(), testAirport3.getName(), testAirport3.getAirportID(),
                testAirport4.getName(), testAirport4.getAirportID(), null, 0, "CR2");
        testRoute3 = new Route(testAirline3.getAirlineID(), testAirline3.getName(), testAirport5.getName(), testAirport5.getAirportID(),
                testAirport1.getName(), testAirport1.getAirportID(), null, 0, "A81");
        testRoute4 = new Route(testAirline4.getAirlineID(), testAirline4.getName(), testAirport2.getName(), testAirport2.getAirportID(),
                testAirport3.getName(), testAirport4.getAirportID(), null, 0, "AN4");
        testRoute5 = new Route(testAirline5.getAirlineID(), testAirline5.getName(), testAirport4.getName(), testAirport4.getAirportID(),
                testAirport5.getName(), testAirport5.getAirportID(), null, 0, "142");
        testRoutes = new ArrayList<Route>();
        testRoutes.add(testRoute1);
        testRoutes.add(testRoute2);
        testRoutes.add(testRoute3);
        testRoutes.add(testRoute4);
        testRoutes.add(testRoute5);
    }

    @After()
    public void ClearDatabase() {
        //clear test airlines,
    }


    /**
     * Test inserting one airline into the database
     */
    @Test
    public void testInsertOneAirline() {
        //dataHandler.InsertAirlines(testAirlines.get(0));
        //Check what the filters require
        //assertEquals();
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
