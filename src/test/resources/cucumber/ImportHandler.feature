Feature: ImportHandler

  Scenario: A user imports an airport with a name
    Given A user selects an airport to import
    Given A user names the airport "Heathrow"
    When A user imports the selected airport
    Then "Heathrow" exists in the list of airports

  Scenario: A user imports an airline with a name
    Given A user selects an airline to import
    Given A user names the airline "Emirates"
    When A user imports the selected airline
    Then "Emirates" exists in the list of airlines

  Scenario: A user imports a route
    Given A user selects an airline to import
    Given "Heathrow" exists within the list of airports
    Given "Kingsford Smith" exists within the list of airports
    When A user imports a route between "Heathrow" and "Kingsford Smith" airports
    Then A route between "Heathrow" and "Kingsford Smith" airports exists in the list of routes