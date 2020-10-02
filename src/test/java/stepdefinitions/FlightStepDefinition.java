package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class FlightStepDefinition {


    @Given("the user \"{String}\" has ${int} total flights")
    public void NumOfFlights(String userName, int currentFlights) {
        throw new io.cucumber.java.PendingException();
    }

    @When("the user \"{String}\" adds a flight from the past")
    public void addFlight(String userName) {
        throw new io.cucumber.java.PendingException();
    }


    @Then("the user \"{String}\" has ${int} total flights")
    public void UpdatedNumOfFlights(String userName, int currentFlights) {
        throw new io.cucumber.java.PendingException();
    }


}