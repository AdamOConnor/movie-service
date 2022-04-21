Feature: To test the delete end point
  DELETE /movie/{id}

  Background: Create and Initialize base Url
    Given url 'http://localhost:8080'

  Scenario: To delete the movie entry from application using movie id
    # Create a new movie entry
    # Delete the newly created movie entry
    # Get request with query parameter and validate for 404
    * def movieId = 1
    # delete request
    Given path '/movie/' + movieId
    And headers {Accept:'application/json'}
    When method delete
    Then status 200
    # Get Request
    Given path '/movie/' + movieId
    And header Accept = 'application/json'
    When method get
    Then status 404

  Scenario: To delete the movie entry from application using movie id and delete the movie entry twice
    # Create a new movie entry
    # Delete the newly created movie entry
    # Get request with query parameter and validate for 404
    * def movieId = 2
    # delete request
    Given path '/movie/' + movieId
    And headers {Accept:'application/json'}
    When method delete
    Then status 200
    # delete request
    Given path '/movie/' + movieId
    And headers {Accept:'application/json'}
    When method delete
    Then status 404