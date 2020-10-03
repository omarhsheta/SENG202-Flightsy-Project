package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import seng202.team6.model.data.DataExportHandler;
import seng202.team6.model.data.DataImportHandler;
import seng202.team6.model.data.Filter;
import seng202.team6.model.entities.Airline;
import seng202.team6.model.entities.Airport;
import seng202.team6.model.entities.Route;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ImportHandlerStepDefinition {
    private DataImportHandler dataImportHandler = DataImportHandler.GetInstance();
    private DataExportHandler dataExportHandler = DataExportHandler.GetInstance();
    private Connection databaseConnection;



    private Airline testAirline1 = new Airline(-1, "test", "test", "test",
            "test", "test", "test", 't');
    private Airport testAirport1 = new Airport(-1, "Name", "City", "Country",
            "IATA", "ICAO", (float) 0, (float) 0, 0, 1, 'U');
    private Route testRoute1 = new Route(9999, "test", "test", 9999,
            "test", 99999, 'Y', 0, "test");
    private Route testRoute2 = new Route(9999, "test", "test", 9999,
            "test", 99999, 'Y', 0, "test");

    private String user;
    private String airportName;
    private String airlineName;
    private Airline selectedAirline;
    private Airport selectedAirport;
    private String destName;
    private String sourceName;


    @Given("A user selects an airport to import")
    public void aUserSelectsAnAirportToImport() {
        selectedAirport = testAirport1;
    }

    @Given("A user names the airport {string}")
    public void aUserNamesTheImportedAirport(String airportName) {
        this.airportName = airportName;
        selectedAirport.SetName(airportName);
    }

    @When("A user imports the selected airport")
    public void aUserImportsTheSelectedAirport() throws Throwable {
        dataImportHandler.InsertAirport(selectedAirport);
    }

    @Then("{string} exists in the list of airports")
    public void existsInTheListOfAirports(String airportName) {
        ArrayList<Filter> filters = new ArrayList<>();
        filters.add(new Filter(String.format("NAME = '%s'", airportName), null));
        ArrayList<Airport> fetchedAirports = dataExportHandler.FetchAirports(filters);
        Assert.assertTrue(fetchedAirports.size() > 0);
    }

    @Given("A user selects an airline to import")
    public void aUserSelectsAnAirlineToImport() {
        selectedAirline = testAirline1;
    }

    @Given("A user names the airline {string}")
    public void aUserNamesTheImportedAirline(String airlineName) {
        this.airlineName = airlineName;
        selectedAirline.SetName(airlineName);

    }

    @When("A user imports the selected airline")
    public void aUserImportsTheSelectedAirline() throws Throwable {
        dataImportHandler.InsertAirline(selectedAirline);
    }



    @Then("{string} exists in the list of airlines")
    public void existsInTheListOfAirlines(String airlineName) {
        ArrayList<Filter> filters = new ArrayList<>();
        filters.add(new Filter(String.format("NAME = '%s'", airlineName), null));
        ArrayList<Airline> fetchedAirlines = dataExportHandler.FetchAirlines(filters);
        Assert.assertTrue(fetchedAirlines.size() > 0);
    }

    @Given("{string} exists within the list of airports")
    public void existsWithinTheListOfAirports(String airportName) throws Throwable {
        this.airportName = airportName;
        testAirport1.SetName(airportName);
        dataImportHandler.InsertAirport(testAirport1);
    }

    @When("A user imports a route between {string} and {string} airports")
    public void aUserImportsARouteBetweenRouteBetweenAndAirports(String sourceName, String destName) throws Throwable {
        this.sourceName = sourceName;
        this.destName = destName;

        testRoute1.SetDestinationAirport(destName);
        testRoute2.SetSourceAirport(sourceName);

        dataImportHandler.InsertRoute(testRoute1);
        dataImportHandler.InsertRoute(testRoute2);
    }

    @Then("A route between {string} and {string} airports exists in the list of routes")
    public void aRouteBetweenAndAirportsExistsInTheListOfRoutes(String sourceName, String destName) {

        ArrayList<Filter> filterSrc = new ArrayList<>();
        ArrayList<Filter> filterDst = new ArrayList<>();
        ArrayList<Filter> filters = new ArrayList<>();
        filterSrc.add(new Filter(String.format("NAME = '%s'", sourceName), null));
        filterDst.add(new Filter(String.format("NAME = '%s'", destName), null));
        String srcCode = dataExportHandler.FetchAirports(filterSrc).get(0).getIATA();
        String dstCode = dataExportHandler.FetchAirports(filterDst).get(0).getIATA();
        filters.add(new Filter(String.format("source_airport = '%s' AND destination_airport = '%s'", srcCode, dstCode), null));
        ArrayList<Route> fetchedRoutes = dataExportHandler.FetchRoutes(filters);
        Assert.assertTrue(fetchedRoutes.size() > 0);
    }
}
