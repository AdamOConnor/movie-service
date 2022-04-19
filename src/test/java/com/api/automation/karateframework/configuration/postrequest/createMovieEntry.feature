Feature: To create the movie entry in the application
         Use POST /movie to create a movie entry in the application
         
Background: Create and initialise the base url
	Given url 'http://localhost:8080'

  Scenario:To create a job entry using JSON format
    Given path '/movie'
    And request {"name": "Fantastic Beasts: The Secrets of Dumbledore","director": "David Yates","year": 2022,"rating": "6.6/10","description": "Professor Albus Dumbledore knows the powerful, dark wizard Gellert Grindelwald is moving to seize control of the wizarding world. Unable to stop him alone, he entrusts magizoologist Newt Scamander to lead an intrepid team of wizards and witches.","inventory": {"initialQuantity": 2,"currentQuantity": 2}}
    And headers {Accept : 'application/json', Content-Type: 'application/json'}
    When method post
    Then status 201
   