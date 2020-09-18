# Team 6 - SENG202 - Flightsy
Flightsy is a Holiday planner application written in Java. The applicaiton uses JavaFX for the GUI, SQLite for the dbms, JDBC for the Java/SQLite interface.
Flightsy was designed and developed by Team 6 consisting of:
 - Callum McLouglin
 - Connor Hitchcock
 - Joseph Kelly
 - Omar Sheta
 - Rutger van Kruiningen
 - Fletcher Dick

## Prerequisites

 - [Download](https://maven.apache.org/download.cgi) and [install](https://maven.apache.org/install.html) Maven.
 - JDK 11.0.2

## Importing Project (using IntelliJ)

 1. Open IntelliJ to the welcome screen.
 2. Select 'Get from Version Control' and enter https://eng-git.canterbury.ac.nz/fch58/seng202-team6.git as the URL parameter.
 3. Select a directory and click 'Clone'.
 4. Open a command line interface inside the project directory and run `mvn validate` to ensure all information needed to build has been successfully cloned from the repository.
 5. In the Maven auto pop-up click 'Install Maven Changes'.
**Note:** *If you run into dependency issues when running the app or the Maven pop up doesn't appear then open the Maven sidebar and click the Refresh icon labeled 'Reimport All Maven Projects'.*

## Build Project

 1. Open a command line interface inside the project directory and run `mvn package` to build a .jar file. The file is located at target/Flightsy-1.0-SNAPSHOT.jar .

## Install App

 1. Open a command line interface inside the project directory and run `mvn install` to install the package into the local repository.

## Deploy App
 1. Open a command line interface inside the project directory and run `mvn deploy` to copy the final package to the remote repository.

## Run App

 1. If you haven't already, Build the project.
 2. Open a command line interface inside the project directory and run `cd target` to change into the target directory.
 3. Run the command `java -jar Flightsy-1.0-SNAPSHOT.jar` to open the application.
