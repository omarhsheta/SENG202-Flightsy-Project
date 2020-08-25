package seng202.team6.model.data;
import javafx.util.Pair;
import seng202.team6.model.entities.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;


public class CSVLoader {

    /**
     * Processes a row from a spreadsheet file and returns it as an ArrayList of Strings
     * @param line It is a row from spreadsheet as a String
     * @return ArrayList of parameters from the spreadsheet's row
     */
    private ArrayList<String> ParseLine(String line) {
        ArrayList<String> values = new ArrayList<>();

        boolean isEscaped = false;
        int mark = 0;
        for (int i = 0, n = line.length(); i < n; i++)
        {
            char c = line.charAt(i);
            if (c == '"') {
                //If char is '"' count it as 'escaped'
                isEscaped = !isEscaped;
            }

            //If char is ',' and we're not escaped, then its the end
            if (c == ',' && !isEscaped) {
                String subseq = line.subSequence(mark, i).toString().replace("\"", "");
                mark = i + 1;
                values.add(ParseSpecialChar(subseq));
            } else if (i == n - 1) {  //If we're at the end of the line, take the rest
                String subseq = line.subSequence(mark, i + 1).toString().replace("\"", "");
                values.add(ParseSpecialChar(subseq));
            }
        }
        return values;
    }

    /**
     * Parse \\N into null
     * @param str String to check if == \N
     * @return Original string or null
     */
    private String ParseSpecialChar(String str)
    {
        if (str != null && str.equals("\\N"))
            return null;
        return str;
    }


    /**
     * Processes a CSV file and runs each row through ParseLine method, then returns an ArrayList of ArrayLists
     * of Strings
     * @param path the location of the spreadsheet file
     * @return an ArrayList of ArrayLists of rows
     */
    public ArrayList<ArrayList<String>> ProcessedFile(String path) {
        ArrayList<ArrayList<String>> valuesList = new ArrayList<>();
        try {
            BufferedReader buffReader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = buffReader.readLine()) != null) {
                valuesList.add(ParseLine(line));
            }
            return valuesList;
        } catch (IOException e) {
            return null;
        }

    }

    /**
     * Processes a raw data file and returns an ArrayList of object type "Plane"
     * @param path the location of the CSV/DAT file that contains raw data about Planes
     * @return an ArrayList of Planes
     */
    public ArrayList<Plane> PlaneList(String path) {
        ArrayList<ArrayList<String>> lines = ProcessedFile(path);
        ArrayList<Plane> result = new ArrayList<>();
        for (ArrayList<String> line: lines) {
            if (line.size() != 3) {
                //Incorrect amount of data
                //Does not add current Plane as it is invalid
                continue;
            }
            String name = line.get(0);
            String IATA = line.get(1);
            String ICAO = line.get(2);
            Plane temp = new Plane(name, IATA, ICAO);
            result.add(temp);
        }
        return result;
    }

    /**
     * Processes a raw data file and returns an ArrayList of object type "Airline"
     * @param path the location of the CSV/DAT file that contains raw data about Airlines
     * @return an ArrayList of Airlines
     */
    public ArrayList<Airline> AirlineList(String path) {
        ArrayList<ArrayList<String>> lines = ProcessedFile(path);
        ArrayList<Airline> result = new ArrayList<>();
        for (ArrayList<String> line: lines) {
            if (line.size() != 8) {
                //Incorrect amount of data
                //Does not add current Airline as it is invalid
                continue;
            }
            int airlineID;
            try {
                airlineID = Integer.parseInt(line.get(0));
            }
            catch (NumberFormatException e1) {
                //Not a number
                //Does not add current Airline as it is invalid
                continue;
            }
            String name = line.get(1);
            String alias = line.get(2);
            String newIATA = line.get(3);
            String newICAD = line.get(4);
            String newCallsign = line.get(5);
            String newCountry = line.get(6);
            char newActive = line.get(7).charAt(0);
            Airline temp = new Airline(airlineID, name, alias, newIATA, newICAD, newCallsign, newCountry, newActive);
            result.add(temp);
        }
        return result;
    }

    /**
     * Processes a raw data file and returns an ArrayList of object type "Airport"
     * @param path the location of the CSV/DAT file that contains raw data about Airports
     * @return an ArrayList of Airports
     */
    public ArrayList<Airport> AirportList(String path) {
        ArrayList<ArrayList<String>> lines = ProcessedFile(path);
        ArrayList<Airport> result = new ArrayList<>();
        for (ArrayList<String> line: lines) {
            if (line.size() != 14) {
                //Incorrect amount of data
                //Does not add current Airport as it is invalid
                continue;
            }
            int airportID;
            try {
                airportID = Integer.parseInt(line.get(0));
            }
            catch (NumberFormatException e1) {
                //Not a number
                //Does not add current Airport as it is invalid
                continue;
            }
            String name = line.get(1);
            String city = line.get(2);
            String country = line.get(3);
            String newIATA = line.get(4);
            String newICAO = line.get(5);
            float newLatitude;
            float newLongitude;
            int newAltitude;
            float newTimezone;
            try {
                newLatitude = Float.parseFloat(line.get(6));
                newLongitude = Float.parseFloat(line.get(7));
                newAltitude = Integer.parseInt(line.get(8));
                newTimezone = Float.parseFloat(line.get(9));
            }
            catch (NumberFormatException e1) {
                //Not a number
                //Does not add current Airport as it is invalid
                continue;
            }
            char newDST = line.get(10).charAt(0);
            Airport temp = new Airport(airportID, name, city, country, newIATA, newICAO, newLatitude, newLongitude,
                    newAltitude, newTimezone, newDST);
            result.add(temp);
        }
        return result;
    }

    /**
     * Processes a raw data file and returns an ArrayList of object type "Route"
     * @param path the location of the CSV/DAT file that contains raw data about Routes
     * @return an ArrayList of Routes
     */
    public ArrayList<Route> RouteList(String path) {
        ArrayList<ArrayList<String>> lines = ProcessedFile(path);
        ArrayList<Route> result = new ArrayList<>();
        for (ArrayList<String> line : lines) {
            int len = line.size();
            if (len != 9 && len != 8) {
                //Incorrect amount of data
                //Does not add current Route as it is invalid
                continue;
            }
            int airlineID;
            int sourceID;
            int destinationID;
            char codeshare;
            int stops;
            try {
                airlineID = Integer.parseInt(line.get(0));
                if (line.get(3).contains("N")) {
                    sourceID = 0;
                } else {
                    sourceID = Integer.parseInt(line.get(3));
                }
                if (line.get(5).contains("N")) {
                    destinationID = 0;
                } else {
                    destinationID = Integer.parseInt(line.get(5));
                }
                if (line.get(6).length() == 0) {
                    codeshare = 0;
                } else {
                    codeshare = line.get(6).charAt(0);
                }
                stops = Integer.parseInt(line.get(7));
            }
            catch (NumberFormatException e1) {
                //Not a number
                //Does not add current Route as it is invalid
                continue;
            }

            String name = line.get(1);
            String source = line.get(2);
            String destination = line.get(4);

            if (len != 9) {
                Route temp = new Route(airlineID, name, source, sourceID, destination, destinationID, codeshare,
                        stops, "");
                result.add(temp);
            } else {
                String equip = line.get(8);
                Route temp = new Route(airlineID, name, source, sourceID, destination, destinationID, codeshare,
                        stops, equip);
                result.add(temp);
            }

        }
        return result;
    }

    public RoutePath PathLister(String path) {
        ArrayList<ArrayList<String>> lines = ProcessedFile(path);
        ArrayList<Pair<Double, Double>> coordinates = new ArrayList<>();
        int lastInd = lines.size()-1;
        String source = null;
        String destination = null;
        for (ArrayList<String> line : lines) {
            if (line.size() != 5) {
                //Incorrect amount of data
                //Does not add current part of the Path as it is invalid
                continue;
            }
            if (lines.indexOf(line) == 0) {
                source = line.get(1);
            } else if (lines.indexOf(line) == lastInd) {
                destination = line.get(1);
            }
            Pair<Double, Double> point;
            try {
                point = new Pair<>(Double.parseDouble(line.get(3)), Double.parseDouble(line.get(4)));
            }
            catch (NumberFormatException e1) {
                //Not a number
                //Does not add current coordinate as it is invalid
                continue;
            }
            coordinates.add(point);
        }
        return new RoutePath(source, destination, coordinates);
    }
}
