Feature: HolidayPlan

  Scenario: Create holiday plan with valid name
    Given the holiday plan "Family Hawaii Getaway" does not exist
    When the user names the holiday plan "Family Hawaii Getaway"
    Then the holiday plan name is "Family Hawaii Getaway"

  Scenario: Create holiday plan with valid name including numbers
    Given the holiday plan "Gr3at H0l1day" does not exist
    When the user names the holiday plan "Gr3at H0l1day"
    Then the holiday plan name is "Gr3at H0l1day"

  Scenario: Create holiday plan with invalid name that is too long
    Given the holiday plan "This is Gonna Be The Best Holiday Ever" does not exist
    When the user names the holiday plan "This is Gonna Be The Best Holiday"
    Then the holiday plan name is ""
#
#  Scenario: Create holiday plan with invalid name including characters
#    Given the holiday plan "F!j! Holiday" does not exist
#    When the user names the holiday plan "F!j! Holiday"
#    Then the holiday plan name is ""
#
#
#  Scenario: A user creates a holiday plan
#    Given the user "Subaru" has 0 holiday plans
#    When the user "Subaru" creates a holiday plan
#    Then the user "Subaru" now has 1 holiday plans
#
#  Scenario: A user creates a holiday plan whilst already having four
#    Given the user "Love" has 4 holiday plans
#    When the user "Love" creates a holiday plan
#    Then the user "Love" now has 5 holiday plans
#
#  Scenario: A user creates a holiday plan unsuccessfully whilst already having five
#    Given the user "Emilia" has 5 holiday plans
#    When the user "Emilia" creates a holiday plan
#    Then the user "Emilia" now has 5 holiday plans
