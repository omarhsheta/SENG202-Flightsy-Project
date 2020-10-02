package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
@RunWith(Cucumber.class)
@CucumberOptions(features="seng202-team6/src/test/resources/cucumber",
        plugin = {"pretty", "html:target/cucumber.html"},
        snippets = CucumberOptions.SnippetType.CAMELCASE)
public class CucumberRunner {

}
