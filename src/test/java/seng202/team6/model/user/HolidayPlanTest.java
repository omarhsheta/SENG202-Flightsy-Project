package seng202.team6.model.user;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import seng202.team6.model.entities.Route;
import seng202.team6.model.events.CarTrip;
import seng202.team6.model.events.Flight;
import seng202.team6.model.events.General;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HolidayPlanTest {
    HolidayPlan plan1;
    HolidayPlan plan2;

    @Before
    public void Initialize() {
        plan1 = new HolidayPlan("Testplan");
        plan2 = new HolidayPlan("Hi");

        plan1.AddCarTrip(new CarTrip(5, 5, 5, 10, 10, "Test", "N",
                "Christchurch", "New Zealand", "California", "United States"));

        plan1.AddCarTrip(new CarTrip(2, 4, 9, 1, 10, "Trip2", null,
                "Christchurch", "New Zealand", "California", "United States"));

        plan1.AddFlight(new Flight(6, 6, 2000, 0, 0, 7, 6,
                2020, 3, 10, "Flight", null,
                new Route(10, "TestAirline", "AKL", 50,
                        "CHC", 60, 'N', 0, null)));

        plan2.AddItinerary(new General(10, 2, 0, 10, 59, "General", "No",
                "Christchurch", "New Zealand"));
    }

    /**
     * Test serializing holiday plan to json
     */
    @Ignore
    public void TestJSONSerialize() {
        String expected1 = "{\"name\":\"Testplan\",\"itineraries\":[],\"flights\":[{\"route\":{\"AirlineID\":10,\"Airline\":\"TestAirline\"," +
                "\"SourceAirport\":\"AKL\",\"SourceAirportID\":50,\"DestinationAirport\":\"CHC\",\"DestinationAirportID\":60," +
                "\"Codeshare\":\"N\",\"Stops\":0},\"arrivalDateTime\":{\"date\":{\"year\":2020,\"month\":6,\"day\":7}," +
                "\"time\":{\"hour\":3,\"minute\":10,\"second\":0,\"nano\":0}},\"subFolder\":\"holidayview\"," +
                "\"holidayFlightComponent\":\"holidayflight\",\"dateTime\":{\"date\":{\"year\":2000,\"month\":6,\"day\":6}," +
                "\"time\":{\"hour\":0,\"minute\":0,\"second\":0,\"nano\":0}},\"Title\":\"Flight\"}]," +
                "\"carTrips\":[{\"OriginCity\":\"Christchurch\",\"OriginCountry\":\"New Zealand\",\"DestinationCity\":\"California\"," +
                "\"DestinationCountry\":\"United States\",\"subFolder\":\"holidayview/eventbuttons\",\"holidayFlightComponent\":\"DriveBtn\"," +
                "\"dateTime\":{\"date\":{\"year\":5,\"month\":5,\"day\":5},\"time\":{\"hour\":10,\"minute\":10,\"second\":0,\"nano\":0}}," +
                "\"Title\":\"Test\",\"Notes\":\"N\"},{\"OriginCity\":\"Christchurch\",\"OriginCountry\":\"New Zealand\"," +
                "\"DestinationCity\":\"California\",\"DestinationCountry\":\"United States\",\"subFolder\":\"holidayview/eventbuttons\"," +
                "\"holidayFlightComponent\":\"DriveBtn\",\"dateTime\":{\"date\":{\"year\":9,\"month\":4,\"day\":2}," +
                "\"time\":{\"hour\":1,\"minute\":10,\"second\":0,\"nano\":0}},\"Title\":\"Trip2\"}],\"isInDatabase\":false}";

        String expected2 = "{\"name\":\"Hi\",\"itineraries\":[{\"City\":\"Christchurch\",\"Country\":\"New Zealand\"," +
                "\"dateTime\":{\"date\":{\"year\":0,\"month\":2,\"day\":10},\"time\":{\"hour\":10,\"minute\":59,\"second\":0,\"nano\":0}}," +
                "\"Title\":\"General\",\"Notes\":\"No\"}],\"flights\":[],\"carTrips\":[],\"isInDatabase\":false}";

        assertEquals(expected1, plan1.ToJson());
        assertEquals(expected2, plan2.ToJson());
    }

    /**
     * Test deserializing json back to object
     */
    @Test
    public void TestJSONDeserialize() {
        String json = "{\"name\":\"Testplan\",\"itineraries\":[],\"flights\":" +
                "[{\"route\":{\"AirlineID\":10,\"Airline\":\"TestAirline\",\"SourceAirport\":\"AKL\"," +
                "\"SourceAirportID\":50,\"DestinationAirport\":\"CHC\",\"DestinationAirportID\":60," +
                "\"Codeshare\":\"N\",\"Stops\":0},\"arrivalDateTime\":{\"date\":{\"year\":2020,\"month\":6,\"day\":7},\"" +
                "time\":{\"hour\":3,\"minute\":10,\"second\":0,\"nano\":0}},\"subFolder\":\"holidayview\"," +
                "\"holidayFlightComponent\":\"holidayflight\",\"dateTime\":{\"date\":{\"year\":2000,\"month\":6,\"day\":6}," +
                "\"time\":{\"hour\":0,\"minute\":0,\"second\":0,\"nano\":0}},\"Title\":\"Flight\"}]," +
                "\"carTrips\":[{\"OriginCity\":\"Christchurch\",\"OriginCountry\":\"New Zealand\",\"DestinationCity\":\"California\"," +
                "\"DestinationCountry\":\"United States\",\"dateTime\":{\"date\":{\"year\":5,\"month\":5,\"day\":5}," +
                "\"time\":{\"hour\":10,\"minute\":10,\"second\":0,\"nano\":0}},\"Title\":\"Test\",\"Notes\":\"N\"}," +
                "{\"OriginCity\":\"Christchurch\",\"OriginCountry\":\"New Zealand\",\"DestinationCity\":\"California\"," +
                "\"DestinationCountry\":\"United States\",\"dateTime\":{\"date\":{\"year\":9,\"month\":4,\"day\":2}," +
                "\"time\":{\"hour\":1,\"minute\":10,\"second\":0,\"nano\":0}},\"Title\":\"Trip2\"}]}";

        HolidayPlan deserialized = HolidayPlan.FromJSON(json);

        assertNotNull(deserialized);

        assertEquals(2, deserialized.GetCarTrips().size());
        assertEquals(1, deserialized.GetFlights().size());
        assertEquals(0, deserialized.GetItineraries().size());

        assertEquals("Testplan", deserialized.getName());

        String json2 = "{\"name\":\"Hi\",\"itineraries\":[{\"City\":\"Christchurch\",\"Country\":\"New Zealand\"," +
                "\"dateTime\":{\"date\":{\"year\":0,\"month\":2,\"day\":10},\"time\":{\"hour\":10,\"minute\":59,\"second\":0,\"nano\":0}}," +
                "\"Title\":\"General\",\"Notes\":\"No\"}],\"flights\":[],\"carTrips\":[]}";

        HolidayPlan deserialized2 = HolidayPlan.FromJSON(json2);

        assertNotNull(deserialized);

        assertEquals(0, deserialized2.GetCarTrips().size());
        assertEquals(0, deserialized2.GetFlights().size());
        assertEquals(1, deserialized2.GetItineraries().size());

        assertEquals("Hi", deserialized2.getName());
    }
}
