package cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(glue="seng202-team6/src/test/resources/cucumber/Flight.feature", //MAY NEED TO TINKER WITH THAT DELETE ME IF FIXED OK BYE NOW
        plugin = {"pretty", "html:target/cucumber.html"},snippets = CucumberOptions.SnippetType.CAMELCASE)
public class FlightStepDefinition {

    @Given("the user {String} has ${int} total flights")
    public void NumOfFlights(String userName, int currentFlights) {
        throw new io.cucumber.java.PendingException();
    }

    @When("the user {String} adds a flight from the past")
    public void addFlight(String userName) {
        throw new io.cucumber.java.PendingException();
    }


    @Then("the user {String} has ${int} total flights")
    public void UpdatedNumOfFlights(String userName, int currentFlights) {
        throw new io.cucumber.java.PendingException();
    }


}