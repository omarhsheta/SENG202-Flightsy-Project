package seng202.team6.model.data;

import seng202.team6.model.data.Filter;
import seng202.team6.model.entities.Airport;

import java.util.ArrayList;

/**
 * Static class to help parse SQL queries
 */
public class SQLHelper
{
    private SQLHelper() {}

    /**
     * Extract query from list of filter objects
     * @param tableName Database table
     * @param filters List of filters
     * @return SQL String query.
     */
    public static String ExtractQuery(String tableName, ArrayList<Filter> filters) {
        if (filters == null || filters.size() == 0) {
            return String.format("SELECT * FROM %s;", tableName);
        }

        StringBuilder builder = new StringBuilder();
        builder.append(String.format("SELECT * FROM %s WHERE ", tableName));
        for (int i = 0; i < filters.size() - 1; i++) {
            Filter filter = filters.get(i);
            builder.append(filter.GetFilter());
            builder.append(" ");
            builder.append(filter.GetConnection());
            builder.append(" ");
        }
        builder.append(filters.get(filters.size() - 1).GetFilter());
        builder.append(';');

        return builder.toString();
    }

    /**
     * Extract a SQL query string list of airport IATAs
     * @param airports Airport list
     * @return String of IATAs in SQL List form
     */
    public static String GetAirportIATAList(ArrayList<Airport> airports) {
        if (airports.size() == 0) {
            return "";
        }

        StringBuilder list = new StringBuilder();
        for (int i = 0; i < airports.size() - 1; i++) {
            list.append(String.format("'%s', ", airports.get(i).GetIATA()));
        }
        list.append(String.format("'%s'", airports.get(airports.size() - 1).GetIATA()));

        return list.toString();
    }
}
