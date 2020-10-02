package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ImportHandlerStepDefinition {
    @Given("A user \"{String}\" selects an airport to import")
    @Given("A user \"{String}\" names the airport \"{String}\"")
    @When("A user \"{String}\" imports the selected airport")
    @Then("\"{String}\" exists in the list of airports")
    public void ImportAirportTest(String username, String AirportName) {
        throw new io.cucumber.java.PendingException();
    }

    @Given("A user \"{String}\" selects an airline to import")
    @Given("A user \"{String}\" names the airline \"{String}\"")
    @When("A user \"{String}\" imports the selected airline")
    @Then("\"{String}\" exists in the list of airlines")
    public void ImportAirlineTest(String username, String AirportName) {
        throw new io.cucumber.java.PendingException();
    }

    @Given("A user \"{String}\" selects an airline to import")
    @Given("\"{String}\" exists within the list of airports")
    @Given("\"{String}\" exists within the list of airports")
    @When("A user \"{String}\" imports a route between \"{String}\" and \"{String}\" airports")
    @Then("A route between \"{String}\" and \"{String}\" airports exists in the list of routes")
    public void ImportRouteTest(String username, String pointA, String pointB) {
        throw new io.cucumber.java.PendingException();
    }
}
