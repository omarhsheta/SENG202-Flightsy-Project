package seng202.team6.model.data;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

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

    @After
    public void DeleteFile() {
        dataHandler.DeleteDataFile(fileOne);
        dataHandler.DeleteDataFile(fileTwo);
    }

    @Test
    public void TestDatabaseCreation() {
        File file = dataHandler.GetDatabaseFileDirectory();
        assertTrue(file.exists());
        assertTrue(file.isFile());
    }

    @Test
    public void TestFileCreate() {
        File newFile = new File(dataHandler.GetDataFolder(), fileOne);
        assertFalse(newFile.exists() && newFile.isFile());
        dataHandler.WriteDataFile(fileOne, "");
        assertTrue(newFile.exists() && newFile.isFile());
    }

    @Test
    public void TestFileRead() {
        dataHandler.WriteDataFile(fileOne, contents);
        assertEquals(contents, dataHandler.ReadDataFile(fileOne));
    }

    @Test
    public void TestFileReadNull() {
        String filename = "doesntexist.txt";
        assertNull(dataHandler.ReadDataFile(filename));
    }

    @Test
    public void TestFileDelete() {
        dataHandler.WriteDataFile(fileOne, "");
        File newFile = new File(dataHandler.GetDataFolder(), fileOne);
        assertTrue(newFile.exists() && newFile.isFile());
        dataHandler.DeleteDataFile(fileOne);
        assertFalse(newFile.exists() && newFile.isFile());
    }

    @Test
    public void TestFileDeleteNull() {
        assertFalse(dataHandler.DeleteDataFile(fileTwo));
    }

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
}
