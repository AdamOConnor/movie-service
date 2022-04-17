package edu.tus.movieservice;

import edu.tus.movieservice.config.Config;
import edu.tus.movieservice.dao.MovieRepository;
import edu.tus.movieservice.dto.Movie;
import edu.tus.movieservice.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Sql({"/data.sql"})
public class SpringBootInitialLoadIntegrationTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    Config config;

    @Test
    @GetMapping("/movie")
    @ResponseBody
    public ResponseEntity<List<Movie>> getMovies(@RequestParam("name") Optional<String> movieName,
                                                 @RequestParam("director") Optional<String> movieDirector) throws ResourceNotFoundException {
        List<Movie> movies = null;

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri();

        HttpHeaders responseHeaders = new HttpHeaders();

        // Create custom response header to return configuration parameters
        responseHeaders.add("Environment", config.getEnvironment());
        responseHeaders.add("Version", config.getVersion());

        if (movieName.isPresent() && movieDirector.isPresent()) {

            movies = movieRepository.findByNameAndDirectorContainingIgnoreCase(movieName.get(), movieDirector.get());

            if (movies.isEmpty()) {
                throw new ResourceNotFoundException("Movies not found by Director " + movieDirector.get() + " with name containing: " + movieName.get());
            } else {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .location(location)
                        .headers(responseHeaders)
                        .body(movies);
            }
        }

        if (movieName.isPresent()) {
            movies = movieRepository.findByNameContainingIgnoreCase(movieName.get());
            if (movies.isEmpty()) {
                throw new ResourceNotFoundException("Movie not found with the name containing: " + movieName.get());
            } else {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .location(location)
                        .headers(responseHeaders)
                        .body(movies);
            }
        }

        if (movieDirector.isPresent()) {
            movies = movieRepository.findByDirectorContainingIgnoreCase(movieDirector.get());
            if (movies.isEmpty()) {
                throw new ResourceNotFoundException("Movies not found by Director: " + movieDirector.get());
            } else {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .location(location)
                        .headers(responseHeaders)
                        .body(movies);
            }
        }

        return ResponseEntity.status(HttpStatus.OK)
                .location(location)
                .headers(responseHeaders)
                .body(movieRepository.findAll());
    }
}