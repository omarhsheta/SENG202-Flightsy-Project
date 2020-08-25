package seng202.team6.model.entities;

public class Filter {
    private String filter;
    private String connection;
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
