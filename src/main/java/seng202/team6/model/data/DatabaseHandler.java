package seng202.team6.model.data;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * This class handles setting up the database connection
 */
public class DatabaseHandler {
    private static DatabaseHandler Instance;

    /**
     * Singleton method to ensure only one connection to the database at one time
     * @return Single DataHandler object
     */
    public static DatabaseHandler GetInstance() {
        if (Instance == null) {
            Instance = new DatabaseHandler();
        }
        return Instance;
    }

    private Connection databaseConnection;
    public Connection GetConnection() {
        return databaseConnection;
    }

    private final String databaseSourceFolder = "database";
    private final String databaseOutputFolder = "data";
    private final String databaseFile = "database.sqlite";
    /**
     * Constructor class creates the connection to the SQLite database.
     */
    private DatabaseHandler() {
        try {
            //JDBC requires databases to be an external file
            TryCopyDatabase();
            // relative url to database
            String url = "jdbc:sqlite:" + databaseOutputFolder + "/" + databaseFile;
            // Create a connection to the database
            this.databaseConnection = DriverManager.getConnection(url);
            System.out.println("Successfully connected to SQLite.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method copies the database into an output folder
     * Required as JDBC loads databases from hard disk rather than
     * from embedded jar files
     */
    private void TryCopyDatabase() {
        File currentDir = new File(System.getProperty("user.dir"));
        File newDir = new File(currentDir, databaseOutputFolder);
        newDir.mkdir();
        File file = new File(newDir, databaseFile);
        if (!file.isFile()) {
            try {
                InputStream inStream = DatabaseHandler.class.getResourceAsStream("/" + databaseSourceFolder + "/" + databaseFile);
                Files.copy(inStream, file.toPath());
            } catch (Exception e) {
                System.out.println("Failed to copy database into directory \n " + e.toString());
            }
        }
    }

    /**
     * Creates and returns the database folders and returns the file object
     * @return File of where database is stored
     */
    public File GetDatabaseFileDirectory() {
        TryCopyDatabase();
        File currentDir = new File(System.getProperty("user.dir"));
        File newDir = new File(currentDir, databaseOutputFolder);
        File file = new File(newDir, databaseFile);
        if (file.isFile()) {
            return file;
        }
        return null;
    }
}
