Feature: MasterWindow

  Scenario: A user opens a new panel while having multiple panels open
    Given the user "Issac" has 3 panels open
    When the user "Issac" opens a new panel
    Then the user "Issac" has 4 panels open

  Scenario: A user opens a new panel while having many multiple panels open
    Given the user "Liam" has 4 panels open
    When the user "Liam" opens a new panel
    Then the user "Liam" has 5 panels open

  Scenario: A user opens a new panel while having the maximum number of panels open
    Given the user "Ava" has 5 panels open
    When the user "Ava" opens a new panel
    Then the user "Ava" has 5 panels open