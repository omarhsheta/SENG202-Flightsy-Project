package seng202.team6.model.data;

/**
 * Filter object to handle filtering of data from
 * a database
 */
public class Filter {
    private String filter;
    private String connection;

    /**
     * Filter constructor
     * @param filter Filter string
     * @param connection Connection after this query e.g 'AND', 'OR'
     */
    public Filter(String filter, String connection) {
        this.filter = filter;
        this.connection = connection;
    }

    public String GetFilter() {
        return this.filter;
    }
    public String GetConnection() {
        return this.connection;
    }
}
