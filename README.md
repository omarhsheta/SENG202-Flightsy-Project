 - How to:
	 - Install app
	 - deploy app
	 - run app
	 - import project
	 - build project

# Team 6 - SENG202 - Flightsy
Flightsy is a Holiday planner application written in Java. The applicaiton uses JavaFX for the GUI, SQLite for the dbms, JDBC for the Java/SQLite interface.
Flightsy was designed and developed by Team 6 consisting of:
 - Callum McLouglin
 - Connor Hitchcock
 - Joseph Kelly
 - Omar Sheta
 - Rutger van Kruiningen
 - Fletcher Dick

## Prerequestitessaf

## Importing Project (using IntelliJ)

 1. Open IntelliJ to the welcome screen
 2. Select 'Get from Version Control' and enter https://eng-git.canterbury.ac.nz/fch58/seng202-team6.git as the URL parameter
 3. Select a directory and click 'Clone'
 4. Open a command line interface inside the project directory and run `mvn validate` to ensure all information needed to build has been succesfully cloned from the repository
 5. In the Maven auto pop-up click 'Install Maven Changes'
**Note:** *If you run into dependeny issues when running the app or the Maven pop up doesn't appear then open the Maven sidebar and click the Refresh icon labled 'Reimport All Maven Projects'.*

## Build Project

 1. Open a command line interface inside the project directory and run `mvn package` to build a .jar file. The file is located at target/Flightsy-1.0-SNAPSHOT.jar .
**Note:** *The .jar file will need to be moved into the root of the project in order to run.'.*
