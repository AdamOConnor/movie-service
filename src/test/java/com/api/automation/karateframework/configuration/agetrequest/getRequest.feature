Feature: To test the get end point of the application To test different get end point with different data formats supported by the application

Background: Setup the Base path
	 Given url 'http://localhost:8080'
 
Scenario: To get all the data from application in JSON format
    Given path '/movie'
    And header Accept = 'application/json'
    When method get # Send the get request
    Then status 200 # Send the get request
   
  