package stepdefinitions;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import jdk.jshell.spi.ExecutionControl;
import org.junit.Before;
import org.junit.runner.RunWith;
@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/resources/cucumber",
        plugin = {"pretty", "html:target/cucumber.html"},
        snippets = CucumberOptions.SnippetType.CAMELCASE)
public class CucumberRunner {
//    @Before
//    public void InitializeTest() {
//        ExecutionControl.NotImplementedException;
//    }
}
