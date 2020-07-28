package seng202.team6;

import com.google.gson.Gson;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {

        System.out.println( "Hwllo Seng202 team 6" );

        MyObject myObject = new MyObject( "chair", 3 );
        Gson gson = new Gson();
        String jsonString = gson.toJson( myObject );

        System.out.println( "myObject = " + myObject );
        System.out.println( "myObject stringfyied = " + jsonString );
    }
}
