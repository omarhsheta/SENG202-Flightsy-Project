Feature: Flights

  Scenario: A user adds a flight from the past
    Given the user "Racheal" has 0 total flights
    When the user "Racheal" adds a flight from the past
    Then the user "Racheal" now has 1 total flights

  Scenario: A user adds a flight in the future
    Given the user "Steve" has 0 total flights
    When the user "Steve" adds a flight in the future
    Then the user "Steve" now has 1 total flights

  Scenario: A user adds a past and future flights whilst already having multiple flights
    Given the user "Sarah" has 10 flights from the past
    Given the user "Sarah" has 10 flights in the future
    When the user "Sarah" adds a flight from the past
    When the user "Sarah" adds a flight in the future
    Then the user "Sarah" now has 22 total flights


