package seng202.team6.model.data;
import javafx.util.Pair;
import seng202.team6.model.entities.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


public class CSVLoader {

    private DataImportHandler dataImport = DataImportHandler.GetInstance();

    /**
     * Processes a row from a spreadsheet file and returns it as an ArrayList of Strings
     *
     * @param line It is a row from spreadsheet as a String
     * @return ArrayList of parameters from the spreadsheet's row
     */
    public ArrayList<String> ParseLine(String line) {
        ArrayList<String> values = new ArrayList<>();

        boolean isEscaped = false;
        int mark = 0;
        for (int i = 0, n = line.length(); i < n; i++) {
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
     *
     * @param str String to check if == \N
     * @return Original string or null
     */
    private String ParseSpecialChar(String str) {
        if (str != null && str.equals("\\N"))
            return null;
        return str;
    }


    /**
     * Processes a CSV file and runs each row through ParseLine method, then returns an ArrayList of ArrayLists
     * of Strings
     *
     * @param path the location of the spreadsheet file
     * @return an ArrayList of ArrayLists of rows
     */
    public ArrayList<ArrayList<String>> ProcessCSVFile(String path) {
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
     * Checks that the fields in a row entry match the required types for Airport data
     * @param fields ArrayList of String objects for each field of Airport data
     * @return boolean true if the entry is valid, otherwise false indicating it can be discarded
     */
    public boolean AirportEntryCheck(ArrayList<String> fields) {
        if (fields.size() != 12) {
            //Incorrect amount of data
            //Does not add current Airport as it is invalid
            return false;
        }
        try {
            Integer.parseInt(fields.get(0));
            Float.parseFloat(fields.get(6));
            Float.parseFloat(fields.get(7));
            Integer.parseInt(fields.get(8));
            Integer.parseInt(fields.get(9));
        } catch (NumberFormatException e1) {
            //Not a number
            //Does not add current Airport as it is invalid
            return false;
        }

        if ((fields.get(10) == null) || (fields.get(10).length() != 1)) {
            //DST field missing or not of char type
            fields.set(10, "U");
        }

        fields.remove(11);

        return true;
    }


    /**
     * Checks that the fields in a row entry match the required types for Airline data
     * @param fields ArrayList of String objects for each field of Airline data
     * @return boolean true if the entry is valid, otherwise false indicating it can be discarded
     */
    public boolean AirlineEntryCheck(ArrayList<String> fields) {
        if (fields.size() != 8) {
            //Incorrect amount of data
            //Does not add current Airline as it is invalid
            return false;
        }

        try {
            Integer.parseInt(fields.get(0));
        } catch (NumberFormatException e1) {
            //Not a number
            //Does not add current Airline as it is invalid
            return false;
        }

        //Active field missing or not of char type
        return (fields.get(7) != null) && (fields.get(7).length() == 1);
    }


    /**
     * Checks that the fields in a row entry match the required types for Route data
     * @param fields ArrayList of String objects for each field of Route data
     * @return boolean true if the entry is valid, otherwise false indicating it can be discarded
     */
    public boolean RouteEntryCheck(ArrayList<String> fields) {
        int len = fields.size();
        if (len != 9 && len != 8) {
            //Incorrect amount of data
            //Does not add current Route as it is invalid
            return false;
        }

        try {
            Integer.parseInt(fields.get(1));
            if (fields.get(3) == null) {
                fields.set(3, "0");
            } else {
                Integer.parseInt(fields.get(3));
            }
            if (fields.get(5) == null) {
                fields.set(5, "0");
            } else {
                Integer.parseInt(fields.get(5));
            }
            Integer.parseInt(fields.get(7));
        } catch (NumberFormatException e1) {
            //Not a number
            //Does not add current Route as it is invalid
            return false;
        }

        if ((fields.get(6) == null) || (fields.get(6).length() != 1)) {
            fields.set(6, "N");
        }

        if (len == 8) {
            fields.add("");
        }

        return true;
    }


    /**
     * Processes a raw data file and returns an ArrayList of object type "Airline"
     * @param path the location of the CSV/DAT file that contains raw data about Airlines
     */
    public void ImportCSVAirlines(String path) {
        ArrayList<ArrayList<String>> lines = ProcessCSVFile(path);
        for (ArrayList<String> entry : lines) {
            if (AirlineEntryCheck(entry)) {
                try {
                    Airline airline = new Airline(Integer.parseInt(entry.get(0)), entry.get(1), entry.get(2), entry.get(3),
                            entry.get(4), entry.get(5), entry.get(6), entry.get(7).charAt(0));
                    dataImport.InsertAirline(airline);
                } catch (SQLException ignored) {
                    // Ignore duplicate entry
                }
            }
        }
    }


    /**
     * Processes a raw data file and returns an ArrayList of object type "Airport"
     * @param path the location of the CSV/DAT file that contains raw data about Airports
     */
    public void ImportCSVAirports(String path) {
        ArrayList<ArrayList<String>> lines = ProcessCSVFile(path);
        for (ArrayList<String> entry : lines) {
            if (AirportEntryCheck(entry)) {
                try {
                    Airport airport = new Airport(Integer.parseInt(entry.get(0)), entry.get(1), entry.get(2), entry.get(3),
                            entry.get(4), entry.get(5), Float.parseFloat(entry.get(6)), Float.parseFloat(entry.get(7)),
                            Integer.parseInt(entry.get(8)), Integer.parseInt(entry.get(9)), entry.get(10).charAt(0));
                    dataImport.InsertAirport(airport);
                } catch (SQLException ignored) {
                    // Ignore duplicate entry
                }
            }
        }
    }


    /**
     * Processes a raw data file and returns an ArrayList of object type "Route"
     * @param path the location of the CSV/DAT file that contains raw data about Routes
     */
    public void ImportCSVRoutes(String path) {
        ArrayList<ArrayList<String>> lines = ProcessCSVFile(path);
        for (ArrayList<String> entry : lines) {
            if (RouteEntryCheck(entry)) {
                try {
                    Route route = new Route(Integer.parseInt(entry.get(0)), entry.get(1), entry.get(2),
                            Integer.parseInt(entry.get(3)), entry.get(4), Integer.parseInt(entry.get(5)),
                            entry.get(6).charAt(0), Integer.parseInt(entry.get(7)), entry.get(8));
                    dataImport.InsertRoute(route);
                } catch (SQLException ignored) {
                    // Ignore duplicate entry
                }

            }
        }
    }


    /**
     * Convert CSV file to a RoutePath object
     * @param path File path to CSV file
     * @return RoutePath object with coordinate data from file
     */
    public RoutePath GetCSVRoutePath(String path) {
        ArrayList<ArrayList<String>> lines = ProcessCSVFile(path);
        ArrayList<Pair<Double, Double>> coordinates = new ArrayList<>();
        int lastInd = lines.size() - 1;
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
