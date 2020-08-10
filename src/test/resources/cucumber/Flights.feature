Feature: Flights

  Scenario: A user adds a flight from the past
    Given the user "Racheal" has 0 total flights
    When the user "Racheal" adds a flight from the past
    Then the user "Racheal" has 1 total flights

  Scenario: A user adds a flight in the future
    Given the user "Steve" has 0 total flights
    When the user "Steve" adds a flight in the future
    Then the user "Steve" has 1 total flights

  Scenario: A user adds a past and future flights whilst already having multiple flights
    Given the user "Sarah" has 10 flights from the past
    Given the user "Sarah" has 10 flights in the future
    When the user "Sarah" adds a flight from the past
    When the user "Sarah" adds a flight in the future
    Then the user "Sarah" has 22 total flights


  Scenario: A user adds a past flight with a CSV file
    Given the user "Johnny" has 0 total flights
    When the user "Johnny" adds a flight in the past with a 4 MB CSV file
    Then the user "Johnny" has 1 total flights

  Scenario: A user adds a future flight with a CSV file
    Given the user "Harry" has 0 total flights
    When the user "Harry" adds a flight in the future with a 6 MB CSV file
    Then the user "Harry" has 1 total flights

