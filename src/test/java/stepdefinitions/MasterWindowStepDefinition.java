package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MasterWindowStepDefinition {
    @Given("the user \"{String}\" has {int} panels open")
    @When("the user \"{String}\" opens a new panel")
    @Then("the user \"{String}\" has {int} panels open")
    public void CurrentPanelsTest(String username, int numOfPanels) {
        throw new io.cucumber.java.PendingException();
    }

}
