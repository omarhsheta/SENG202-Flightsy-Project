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

    /**
     * Check whether two filter objects are the same
     * @param obj Object to compare
     * @return Boolean whether two objects the same
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj.getClass() != this.getClass()) {
            return false;
        }

        Filter filterObject = (Filter) obj;
        return this.filter.equals(filterObject.GetFilter()) && this.connection.equals(filterObject.GetConnection());
    }
}
