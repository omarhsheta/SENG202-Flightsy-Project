package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import seng202.team6.model.entities.Route;
import seng202.team6.model.events.Flight;
import seng202.team6.model.user.HolidayPlan;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;


public class FlightStepDefinition {

    HolidayPlan holidayPlan = new HolidayPlan("Holiday");
    Route testRoute = new Route(-9999, "2B","AER",2965,
            "KZN",2990,'N',0,"CR2");



    @Given("the user {string} has {int} total flights")
    public void theUserHasTotalFlights(String user, int numFlights) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        holidayPlan = new HolidayPlan("Holiday");
        
        for (int i = 1; i <= numFlights; i++) {
            holidayPlan.AddFlight(new Flight(i,1,1,1,1,1,1,
                    1, 2, 1, "Test", "", testRoute));
        }

    }

    @When("the user {string} adds a flight from the past")
    public void theUserAddsAFlightFromThePast(String user) throws Throwable {
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDateTime deptTime =  dateTime.minusDays(7);
        LocalDateTime destTime = dateTime.minusDays(6);
        
        holidayPlan.AddFlight(new Flight(deptTime.getDayOfMonth(), deptTime.getMonthValue(), deptTime.getYear(),
                deptTime.getHour(), deptTime.getMinute(), destTime.getDayOfMonth(), destTime.getMonthValue(),
                destTime.getYear(), destTime.getHour(), destTime.getMinute(), "Test", "", testRoute));
    }

    @When("the user {string} adds a flight in the future")
    public void theUserAddsAFlightInTheFuture(String user) throws Throwable {
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDateTime deptTime =  dateTime.plusDays(6);
        LocalDateTime destTime = dateTime.plusDays(7);

        holidayPlan.AddFlight(new Flight(deptTime.getDayOfMonth(), deptTime.getMonthValue(), deptTime.getYear(),
                deptTime.getHour(), deptTime.getMinute(), destTime.getDayOfMonth(), destTime.getMonthValue(),
                destTime.getYear(), destTime.getHour(), destTime.getMinute(), "Test", "Test", testRoute));
    }

    @Given("the user {string} has {int} flights from the past")
    public void theUserHasFlightsFromThePast(String user, int numFlights) throws Throwable {
        LocalDateTime destTime = LocalDateTime.now();
        LocalDateTime deptTime = destTime.minusDays(1);

        for (int i = 0; i < numFlights; i++) {
            deptTime = deptTime.minusDays(1);
            destTime = destTime.minusDays(1);
            
            holidayPlan.AddFlight(new Flight(deptTime.getDayOfMonth(), deptTime.getMonthValue(), deptTime.getYear(),
                    deptTime.getHour(), deptTime.getMinute(), destTime.getDayOfMonth(), destTime.getMonthValue(),
                    destTime.getYear(), destTime.getHour(), destTime.getMinute(), "Test", "Test", testRoute));
        }
        
    }

    @Given("the user {string} has {int} flights in the future")
    public void theUserHasFlightsInTheFuture(String user, int numFlights) throws Throwable {
        LocalDateTime deptTime = LocalDateTime.now();
        LocalDateTime destTime = deptTime.plusDays(1);

        for (int i = 0; i < numFlights; i++) {
            deptTime = deptTime.plusDays(1);
            destTime = destTime.plusDays(1);

            holidayPlan.AddFlight(new Flight(deptTime.getDayOfMonth(), deptTime.getMonthValue(), deptTime.getYear(),
                    deptTime.getHour(), deptTime.getMinute(), destTime.getDayOfMonth(), destTime.getMonthValue(),
                    destTime.getYear(), destTime.getHour(), destTime.getMinute(), "Test", "", testRoute));
        }
    }


    @Then("the user {string} now has {int} total flights")
    public void theUserNowHasTotalFlights(String user, int numFlights) {
        Assert.assertEquals(numFlights, holidayPlan.GetFlights().size());
        holidayPlan = new HolidayPlan("Holiday");
    }
}