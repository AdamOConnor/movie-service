package edu.tus.movieservice;

import edu.tus.movieservice.dto.config.Config;
import edu.tus.movieservice.dto.Movie;
import edu.tus.movieservice.dto.Inventory;
import edu.tus.movieservice.controllers.MovieController;
import edu.tus.movieservice.dao.MovieRepository;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebMvcTest(MovieController.class)
class MovieServiceControllerTest {

    @MockBean
    private MovieRepository movieRepository;

    @MockBean
    Config config;

    @Autowired
    private MockMvc mockMvc;

    private Movie movie;

    private List<Movie> movieList = new ArrayList<>();

    private Inventory inventory;

    LocalDate testSpecificDate = LocalDate.of(2022,4,21);

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
        inventory = new Inventory(5, 5);
        movie = new Movie("Adams Movie Title","Adam O'Connor","2005", "6/10", "This is a test description",inventory);
        movieList.add(movie);
    }

    @Test
    void getMovieById(){
        Mockito.when(movieRepository.findById(1)).thenReturn(Optional.ofNullable(movie));

        RestAssuredMockMvc
                .given()
                .when()
                .get("/movie/1")
                .then()
                .statusCode(200)
                .body("id",Matchers.equalTo(null))
                .body("name", Matchers.equalTo("Adams Movie Title"))
                .body("director", Matchers.equalTo("Adam O'Connor"))
                .body("year", Matchers.equalTo("2005"))
                .body("rating", Matchers.equalTo("6/10"))
                .body("description", Matchers.equalTo("This is a test description"))
                .body("inventory.id", Matchers.equalTo(inventory.getId()))
                .body("inventory.initialQuantity", Matchers.equalTo(inventory.getInitialQuantity()))
                .body("inventory.currentQuantity", Matchers.equalTo(inventory.getCurrentQuantity()));
    }

    @Test
    void getMovieByDirectorSearch(){
        Mockito.when(movieRepository.findByDirectorContainingIgnoreCase("Adam")).thenReturn(movieList);

        RestAssuredMockMvc
                .given()
                .param("director", "Adam")
                .when()
                .get("/movie")
                .then()
                .statusCode(200)
                .body("[0].id",Matchers.equalTo(null))
                .body("[0].name", Matchers.equalTo("Adams Movie Title"))
                .body("[0].director", Matchers.equalTo("Adam O'Connor"))
                .body("[0].year", Matchers.equalTo("2005"))
                .body("[0].rating", Matchers.equalTo("6/10"))
                .body("[0].description", Matchers.equalTo("This is a test description"))
                .body("[0].inventory.id", Matchers.equalTo(inventory.getId()))
                .body("[0].inventory.initialQuantity", Matchers.equalTo(inventory.getInitialQuantity()))
                .body("[0].inventory.currentQuantity", Matchers.equalTo(inventory.getCurrentQuantity()));
    }

    @Test
    void getMovieByNameSearch(){
        Mockito.when(movieRepository.findByNameContainingIgnoreCase("Adams")).thenReturn(movieList);

        RestAssuredMockMvc
                .given()
                .param("name", "Adams")
                .when()
                .get("/movie")
                .then()
                .statusCode(200)
                .body("[0].id",Matchers.equalTo(null))
                .body("[0].name", Matchers.equalTo("Adams Movie Title"))
                .body("[0].director", Matchers.equalTo("Adam O'Connor"))
                .body("[0].year", Matchers.equalTo("2005"))
                .body("[0].rating", Matchers.equalTo("6/10"))
                .body("[0].description", Matchers.equalTo("This is a test description"))
                .body("[0].inventory.id", Matchers.equalTo(inventory.getId()))
                .body("[0].inventory.initialQuantity", Matchers.equalTo(inventory.getInitialQuantity()))
                .body("[0].inventory.currentQuantity", Matchers.equalTo(inventory.getCurrentQuantity()));
    }

    @Test
    void getBookByTitleAndAuthorSearch(){
        Mockito.when(movieRepository.findByNameAndDirectorContainingIgnoreCase("Adams", "Adam")).thenReturn(movieList);

        RestAssuredMockMvc
                .given()
                .param("name", "Adams")
                .param("director", "Adam")
                .when()
                .get("/movie")
                .then()
                .statusCode(200)
                .body("[0].id",Matchers.equalTo(null))
                .body("[0].name", Matchers.equalTo("Adams Movie Title"))
                .body("[0].director", Matchers.equalTo("Adam O'Connor"))
                .body("[0].year", Matchers.equalTo("2005"))
                .body("[0].rating", Matchers.equalTo("6/10"))
                .body("[0].description", Matchers.equalTo("This is a test description"))
                .body("[0].inventory.id", Matchers.equalTo(inventory.getId()))
                .body("[0].inventory.initialQuantity", Matchers.equalTo(inventory.getInitialQuantity()))
                .body("[0].inventory.currentQuantity", Matchers.equalTo(inventory.getCurrentQuantity()));
    }

    @Test
    void postMovieTest(){
        Mockito.when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        RestAssuredMockMvc
                .given()
                .contentType("application/json")
                .body("{\n" +
                        "    \"name\": \"Adams Test Movie\",\n" +
                        "    \"director\": \"Adam\",\n" +
                        "    \"year\": 2003,\n" +
                        "    \"rating\": \"5/10\",\n" +
                        "    \"description\": \"This is to test the posting of a movie\",\n" +
                        "    \"inventory\": {\n" +
                        "        \"initialQuantity\": 4,\n" +
                        "        \"currentQuantity\": 4\n" +
                        "    }\n" +
                        "}")
                .when()
                .post("/movie")
                .then()
                .statusCode(201)
                .header("Location", Matchers.containsString("http://localhost/movie"));

    }

    @Test
    void deleteMovieById(){
        Mockito.doNothing().when(movieRepository).deleteById(1);
        RestAssuredMockMvc
                .given()
                .when()
                .delete("/movie/1")
                .then()
                .statusCode(200);
    }
}