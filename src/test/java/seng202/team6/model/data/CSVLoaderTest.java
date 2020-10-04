package seng202.team6.model.data;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class CSVLoaderTest {
    private static final String resourceFolder = "src/test/resources/CSVLoader/";
    private CSVLoader csvLoader;

    @Before
    public void InitializeTest() {
        csvLoader = new CSVLoader();
    }


    /**
     * Test that lines in CSV files are parsed correctly
     */
    @Test
    public void TestParseNoQuotesLine() {
        String testString = "69,Flightsy Airways,FA,CHC,NZ,KIA KAHA,New Zealand,Y";
        ArrayList<String> expected = new ArrayList<>(Arrays.asList("69", "Flightsy Airways", "FA", "CHC", "NZ", "KIA KAHA", "New Zealand", "Y"));
        assertEquals(expected, csvLoader.ParseLine(testString));
    }

    /**
     * Test that quotation marks in CSV files are removed from fields and that commas in inside quotation marks are not
     * treated as separators
     */
    @Test
    public void TestParseQuotesLine() {
        String testString = "\"69\",\"Flightsy, Airways\",\"FA\",\"CHC\",\"NZ\",\"KIA KAHA\",\"New Zealand\",\"Y\"";
        ArrayList<String> expected = new ArrayList<>(Arrays.asList("69", "Flightsy, Airways", "FA", "CHC", "NZ", "KIA KAHA", "New Zealand", "Y"));
        assertEquals(expected, csvLoader.ParseLine(testString));
    }



    /**
     * Test getting Airline list from CSV file and that invalid data is discarded
     */
    @Test
    public void TestAirlineEntryCheckList() {
        ArrayList<ArrayList<String>> parsedLines = csvLoader.ProcessCSVFile(resourceFolder + "AirlineTest.csv");
        ArrayList<ArrayList<String>> testAirlines = new ArrayList<>();
        ArrayList<String> testAirline1 = new ArrayList<>(Arrays.asList("69", "Flightsy Airways", "FA", "CHC", "NZ", "KIA KAHA", "New Zealand", "Y"));
        ArrayList<String> testAirline2 = new ArrayList<>(Arrays.asList("0", "0", "0", "0", "0", "0", "0", "0"));

        for (int i = 0; i < parsedLines.size(); i++) {
            ArrayList<String> entry = parsedLines.get(i);
            if (csvLoader.AirlineEntryCheck(entry)) {
                testAirlines.add(entry);
            }
        }
        assertEquals(testAirline1, testAirlines.get(0));
        assertEquals(testAirline2, testAirlines.get(1));
        assertEquals(2, testAirlines.size());// Test for invalid data
    }

    /**
     * Test getting Airport list from CSV file and that invalid data is discarded
     */
    @Test
    public void TestAirportEntryCheckList() {
        ArrayList<ArrayList<String>> parsedLines = csvLoader.ProcessCSVFile(resourceFolder + "AirportTest.csv");
        ArrayList<ArrayList<String>> testAirports = new ArrayList<>();
        ArrayList<String> testAirport1 = new ArrayList<>(Arrays.asList("69", "The Dystopian Airport", "Dystopia", "North Korea", "DYS", "DYNK", "42069", "42069", "9001", "3", "Y"));
        ArrayList<String> testAirport2 = new ArrayList<>(Arrays.asList("69", "The Dystopian Airport", "Dystopia", "North Korea", "DYS", "DYNK", "42069", "42069", "9001", "3", "U"));
        ArrayList<String> testAirport3 = new ArrayList<>(Arrays.asList("0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"));

        for (int i = 0; i < parsedLines.size(); i++) {
            ArrayList<String> entry = parsedLines.get(i);
            if (csvLoader.AirportEntryCheck(entry)) {
                testAirports.add(entry);
            }
        }
        assertEquals(testAirport1, testAirports.get(0));
        assertEquals(testAirport2, testAirports.get(1));
        assertEquals(testAirport3, testAirports.get(2));
        assertEquals(3, testAirports.size());// Test for invalid data
    }




    /**
     * Test getting route list from CSV file and that invalid data is discarded
     */
    @Test
    public void TestRouteEntryCheckList() {
        ArrayList<ArrayList<String>> parsedLines = csvLoader.ProcessCSVFile(resourceFolder + "RouteTest.csv");
        ArrayList<ArrayList<String>> testRoutes = new ArrayList<>();
        ArrayList<String> testRoute1 = new ArrayList<>(Arrays.asList("Flightsy Airways", "69", "CHC", "420", "TLV", "666", "N", "5", "CR2"));
        ArrayList<String> testRoute2 = new ArrayList<>(Arrays.asList("Flightsy Airways", "69", "CHC", "420", "TLV", "666", "Y", "5", "CR2"));
        ArrayList<String> testRoute3 = new ArrayList<>(Arrays.asList("0", "0", "0", "0", "0", "0", "0", "0", "0"));

        for (int i = 0; i < parsedLines.size(); i++) {
            ArrayList<String> entry = parsedLines.get(i);
            if (csvLoader.RouteEntryCheck(entry)) {
                testRoutes.add(entry);
            }
        }
        assertEquals(testRoute1, testRoutes.get(0));
        assertEquals(testRoute2, testRoutes.get(1));
        assertEquals(testRoute3, testRoutes.get(2));
        assertEquals(3, testRoutes.size());// Test for invalid data
    }

    /**
     * Test parsing null characters from CSV file
     */
    @Test
    public void TestNullParse() {
        ArrayList<ArrayList<String>> lines = csvLoader.ProcessCSVFile(resourceFolder + "NullTest.csv");
        ArrayList<String> expected = new ArrayList<>() {
            {
                add(null);
                add(null);
                add("Words");
            }
        };

        assertEquals(expected, lines.get(0));
    }



    /**
     * Test parsing an empty CSV file
     */
    @Test
    public void TestEmptyParse() {
        ArrayList<ArrayList<String>> testLines = csvLoader.ProcessCSVFile(resourceFolder + "EmptyTest.csv");
        assertEquals(0, testLines.size());
    }

}
