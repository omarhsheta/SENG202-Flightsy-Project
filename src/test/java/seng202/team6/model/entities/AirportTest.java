package seng202.team6.model.entities;

import org.junit.Before;
import org.junit.Test;
import seng202.team6.model.entities.Airport;

import static org.junit.Assert.assertEquals;

public class AirportTest {
    private Airport airport1;
    private Airport airport2;
    @Before
    public void initialize() {
        airport1 = new Airport(1, "CHC", "VOID", "VOID", "VOID", "VOID",
                35, -35,  100, 3, '3');
        airport2 = new Airport(1, "CHC", "VOID", "VOID", "VOID", "VOID",
                -35, 35,  100, 3, '3');
    }
    @Test
    public void GetDistanceTest() {
        double result = airport1.GetDistance(airport2);
        System.out.println(Math.floor(result));
        assertEquals(98, Math.floor(result), 1e-15);
    }
}
