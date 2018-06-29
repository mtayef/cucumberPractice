Feature: Application login

  Scenario: Login to application
    Given Application and browser up and running
    And user enters the username and password
    When clicked on login button user shouldbe logged in successfully
    Then close the browser and application
