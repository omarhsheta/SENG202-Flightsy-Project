package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class HolidayPlanStepDefinition {

    @Given("the holiday plan {String} does not exist")
    @When("the user names the holiday plan \"{String}\"")
    @Then("the holiday plan name is \"{String}\"")
    public void HolidayName(String name) {
        throw new io.cucumber.java.PendingException();
    }

    @Given("the user \"{String}\" has {int} holiday plans")
    @When("the user \"{String}\" creates a holiday plan")
    @Then("the user \"{String}\" has {int} holiday plans")
    public void baseHoliday(String name, int holidayNum) {
        throw new io.cucumber.java.PendingException();
    }

}
