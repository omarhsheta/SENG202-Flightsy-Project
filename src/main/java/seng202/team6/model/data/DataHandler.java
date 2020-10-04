package seng202.team6.model.data;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * This class handles setting up the database connection
 */
public class DataHandler {
    private static DataHandler Instance;

    /**
     * Singleton method to ensure only one connection to the database at one time
     * @return Single DataHandler object
     */
    public static DataHandler GetInstance() {
        if (Instance == null) {
            Instance = new DataHandler();
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
    private DataHandler() {
        try {
            //JDBC requires databases to be an external file
            TryCopyDatabase(GetDataFolder());
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
     * Create and or return data folder for storing all project files
     * @return File Filepath of data folder
     */
    public File GetDataFolder() {
        File currentDir = new File(System.getProperty("user.dir"));
        File newDir = new File(currentDir, databaseOutputFolder);
        newDir.mkdir();
        return newDir;
    }

    /**
     * This method copies the database into an output folder
     * Required as JDBC loads databases from hard disk rather than
     * from embedded jar files
     */
    private void TryCopyDatabase(File folder) {
        File file = new File(folder, databaseFile);
        if (!file.isFile()) {
            try {
                InputStream inStream = DataHandler.class.getResourceAsStream("/" + databaseSourceFolder + "/" + databaseFile);
                Files.copy(inStream, file.toPath());
            } catch (Exception e) {
                System.out.println("Failed to copy database into directory \n " + e.toString());
            }
        }
    }

    /**
     * Write string to file
     * @param filename Filename
     * @param json Json to write to file
     */
    public void WriteDataFile(String filename, String json) {
        File newFile = new File(GetDataFolder(), filename);

        try {
            if (!newFile.createNewFile()) {
                DeleteDataFile(filename);
            }

            FileWriter writer = new FileWriter(newFile);
            writer.write(json);
            writer.close();

        } catch (Exception e) {
            System.out.println(String.format("Failed to write to data file: %s", filename));
        }
    }

    /**
     * Try delete file from data folder
     * @param filename File to delete
     */
    public boolean DeleteDataFile(String filename) {
        File newFile = new File(GetDataFolder(), filename);
        try {
            if (newFile.exists()) {
                newFile.delete();
                return true;
            }
        } catch (Exception e) {
            System.out.println(String.format("Failed to delete data file: %s", filename));
        }
        return false;
    }

    /**
     * Read JSON string from file
     * @param filename File to read
     * @return String Contents or null
     */
    public String ReadDataFile(String filename) {
        File newFile = new File(GetDataFolder(), filename);

        try {
            if (!newFile.exists() || !newFile.isFile()) {
                return null;
            }

            return Files.readString(newFile.toPath(), StandardCharsets.US_ASCII);

        } catch (Exception e) {
            System.out.println(String.format("Failed to write to data file: %s", filename));
        }
        return null;
    }

    /**
     * Creates and returns the database folders and returns the file object
     * @return File of where database is stored
     */
    public File GetDatabaseFileDirectory() {
        File file = new File(GetDataFolder(), databaseFile);
        if (file.isFile()) {
            return file;
        }
        return null;
    }
}
