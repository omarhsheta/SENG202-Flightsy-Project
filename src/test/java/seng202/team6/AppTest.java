package seng202.team6;


import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {

    /**
     * Rigourous Test :-)
     */
    @Test
    public void testApp()
    {
        assertTrue( true );
    }

    /**
     * Callum's test
     */
    @Test
    public void CallumTest()
    {
        int num = 5;
        assertEquals(5, num);
    }

    /**
     * Fletcher's test
     */
    @Test
    public void FletchersTest()
    {
        int math = 2 + 2 - 1;
        // Quick math from Big Shaq
        assertEquals(3, math);

    }

    /**
     * Rutger's test
     */
    @Test
    public void RutgersTest()
    {
        int number = 9 + 10;
        // Twenny wan
        assertEquals(19, number);
    }

    /**
     * Omar's test
     */
    @Test
    public void OmarsTest()
    {
        int number = 2;
        assertEquals(2, number);
    }

    /**
     * Joseph's test
     */
    @Test
    public void JosephTest()
    {
        int number = 100;
        assertEquals(100, number);
    }

    /**
     * Connor's test
     */
    @Test
    public void ConnorTest()
    {
        int powerLevel = 9069;
        assertTrue(powerLevel >= 9000);
    }
}
