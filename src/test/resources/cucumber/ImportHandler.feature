Feature: ImportHandler

  Scenario: A user imports an airport with a name
    Given A user "Joseph" selects an airport to import
    When A user "Joseph" names the imported airport "Heathrow"
    Then "Heathrow" exists in the list of airports

  Scenario: A user imports an airline with a name
    Given A user "Henry" selects an airline to import
    When A user "Henry" names the imported airline "Emirates"
    Then "Emirates" exists in the list of airlines

  Scenario: A user imports a route
    Given A user "Jessica" selects an airline to import
    Given "Heathrow" exists within the list of airports
    Given "Kingsford Smith" exists within the list of airports
    When A user "Jessica" selects the imported route between "Heathrow" and "Kingsford Smith" airports
    Then A route between "Heathrow" and "Kingsford Smith" airports exists in the list of routes