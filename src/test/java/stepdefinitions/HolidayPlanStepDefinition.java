package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import seng202.team6.gui.controller.HolidayAgendaController;
import seng202.team6.model.user.HolidayPlan;

public class HolidayPlanStepDefinition {

    int numHolidays;
//    HolidayAgendaController holidayAgendaController = HolidayAgendaController.GetInstance();
    HolidayPlan temp;
    String holidayName;
    

    @Given("the holiday plan {string} does not exist")
    public void theHolidayPlanDoesNotExist(String holidayName) throws Throwable {
//        holidayAgendaController.GetHolidays().removeAll(holidayAgendaController.GetHolidays());
        this.holidayName = holidayName;
    }

    @When("the user names the holiday plan {string}")
    public void theUserNamesTheHolidayPlan(String holidayName) throws Throwable {
//        holidayAgendaController.CreateNewHoliday(holidayName);
        temp = new HolidayPlan(holidayName);
    }

    @Then("the holiday plan name is {string}")
    public void theHolidayPlanNameIs(String holidayName) throws Throwable {
        Assert.assertEquals(holidayName, temp.getName());
    }

//    @Given("the user {string} has {int} holiday plans")
//    public void theUserHasHolidayPlans(String user, int numHolidays) throws Throwable {
//        this.numHolidays = numHolidays;
//        holidayAgendaController.GetHolidays().removeAll(holidayAgendaController.GetHolidays());
//
//        for (int i = 1; i <= numHolidays; i++) {
//            holidayName = String.format("Holiday%d", numHolidays);
//            holidayAgendaController.CreateNewHoliday(holidayName);
//        }
//    }
//
//    @When("the user {string} creates a holiday plan")
//    public void theUserCreatesAHolidayPlan(String user) throws Throwable {
//        holidayAgendaController.CreateNewHoliday("New Holiday");
//    }
//
//    @Then("the user {string} now has {int} holiday plans")
//    public void theUserNowHasHolidayPlans(String user, int numHolidays) throws Throwable {
//        this.numHolidays = numHolidays;
//        Assert.assertEquals(numHolidays, holidayAgendaController.GetHolidays().size());
//    }
}
