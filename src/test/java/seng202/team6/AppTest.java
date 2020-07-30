package seng202.team6;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }

    /**
     * Callum's test
     */
    public void CallumTest()
    {
        int num = 5;
        assertEquals(5, num);
    }

    /**
     * Fletcher's test
     */
    public void FletchersTest()
    {
        int math = 2 + 2 - 1;
        // Quick math from Big Shaq
        assertEquals(3, math);
        
    }

    /**
     * Rutger's test
     */
    public void RutgersTest()
    {
        int number = 9 + 10;
        // Twenny wan
        assertEquals(19, number);

    }
}
