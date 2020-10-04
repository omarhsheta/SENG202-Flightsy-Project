package seng202.team6.model.data;

import javafx.util.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seng202.team6.model.entities.RoutePath;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class DataHandlerTest
{
    private DataHandler dataHandler;

    private String fileOne;
    private String fileTwo;

    private String contents;

    @Before
    public void Initialize() {
        dataHandler = DataHandler.GetInstance();

        fileOne = "test.txt";
        fileTwo = "doesntexist.txt";
        contents = "This is a sentence.";
    }

    /**
     * Delete made files
     */
    @After
    public void DeleteFile() {
        dataHandler.DeleteDataFile(fileOne);
        dataHandler.DeleteDataFile(fileTwo);
    }

    /**
     * Test creating database file
     */
    @Test
    public void TestDatabaseCreation() {
        File file = dataHandler.GetDatabaseFileDirectory();
        assertTrue(file.exists());
        assertTrue(file.isFile());
    }

    /**
     * Test creating a file
     */
    @Test
    public void TestFileCreate() {
        File newFile = new File(dataHandler.GetDataFolder(), fileOne);
        assertFalse(newFile.exists() && newFile.isFile());
        dataHandler.WriteDataFile(fileOne, "");
        assertTrue(newFile.exists() && newFile.isFile());
    }

    /**
     * Test reading a file
     */
    @Test
    public void TestFileRead() {
        dataHandler.WriteDataFile(fileOne, contents);
        assertEquals(contents, dataHandler.ReadDataFile(fileOne));
    }

    /**
     * Test reading a null file
     */
    @Test
    public void TestFileReadNull() {
        String filename = "doesntexist.txt";
        assertNull(dataHandler.ReadDataFile(filename));
    }

    /**
     * Test deleting an existing file
     */
    @Test
    public void TestFileDelete() {
        dataHandler.WriteDataFile(fileOne, "");
        File newFile = new File(dataHandler.GetDataFolder(), fileOne);
        assertTrue(newFile.exists() && newFile.isFile());
        dataHandler.DeleteDataFile(fileOne);
        assertFalse(newFile.exists() && newFile.isFile());
    }

    /**
     * Test deleting a non existant file
     */
    @Test
    public void TestFileDeleteNull() {
        assertFalse(dataHandler.DeleteDataFile(fileTwo));
    }

    /**
     * Test overwriting the contents
     */
    @Test
    public void TestFileReadWriteOverwrite() {

        dataHandler.WriteDataFile(fileOne, contents);
        File newFile = new File(dataHandler.GetDataFolder(), fileOne);
        assertTrue(newFile.exists() && newFile.isFile());
        assertEquals(contents, dataHandler.ReadDataFile(fileOne));

        contents = "Now overwrite the contents pls";
        dataHandler.WriteDataFile(fileOne, contents);
        assertTrue(newFile.exists() && newFile.isFile());
        assertEquals(contents, dataHandler.ReadDataFile(fileOne));
    }

    /**
     * Test writing class to file and reading back
     */
    @Test
    public void TestFileJSONReadWrite() {
        ArrayList<Pair<Double, Double>> coords = new ArrayList<>();
        coords.add(new Pair<>(0d, 0d));
        coords.add(new Pair<>(100d, 100d));
        coords.add(new Pair<>(75.24d, 74.24d));
        coords.add(new Pair<>(0d, 0d));
        RoutePath path = new RoutePath("AKL", "CHC", coords);

        dataHandler.WriteDataFile(fileOne, path.ToJson());
        String json = dataHandler.ReadDataFile(fileOne);

        RoutePath deserialized = RoutePath.FromJSON(json);

        assertEquals("AKL", deserialized.GetSource());
        assertEquals("CHC", deserialized.GetDestination());
        assertEquals(4, deserialized.GetCoordinates().size());
        assertEquals(new Pair<>(75.24, 74.24), deserialized.GetCoordinates().get(2));
    }
}
