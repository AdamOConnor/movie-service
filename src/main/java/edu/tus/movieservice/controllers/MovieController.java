package edu.tus.movieservice.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import edu.tus.movieservice.dto.config.Config;
import edu.tus.movieservice.dto.Movie;
import edu.tus.movieservice.dao.MovieRepository;
import edu.tus.movieservice.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

@Service
@RestController
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    Config config;

    @GetMapping("/movie")
    @ResponseBody
    public ResponseEntity<List<Movie>> getMovies(@RequestParam("name") Optional<String> movieName,
                                                 @RequestParam("director") Optional<String> movieDirector) throws ResourceNotFoundException {
        List<Movie> movies;

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri();

        HttpHeaders responseHeaders = new HttpHeaders();

        // Create custom response header to return configuration parameters this is just a test
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

    @GetMapping("/movie/{id}")
    public Movie getMovieById(@Valid @PathVariable int id) throws ResourceNotFoundException {
        Optional<Movie> movieFound = movieRepository.findById(id);
        if(movieFound.isPresent())
            return movieFound.get();
        else
            throw new ResourceNotFoundException("Unable to find movie with the associated id of: " + id);
    }

    @DeleteMapping("/movie/{id}")
    public void deleteMovie(@Valid @PathVariable(value = "id") Integer movieId) throws ResourceNotFoundException {

        try {
            movieRepository.deleteById(movieId);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Unable to delete movie with the associated id of: " + movieId);
        }
    }

    @PostMapping("/movie")
    public ResponseEntity postMovie(@Valid @RequestBody Movie movie) {
        Movie saveMovie = movieRepository.save(movie);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("{id}")
                .buildAndExpand(saveMovie.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/movie/{id}")
    public ResponseEntity updateMovie(@Valid @PathVariable int id, @Valid @RequestBody Movie movie) throws ResourceNotFoundException {

        Optional<Movie> savedMovie = movieRepository.findById(id);
        if(savedMovie.isPresent()) {
            movieRepository.save(movie);
            // return 200
            return  ResponseEntity.status(HttpStatus.OK).body(movie);
        }else {
            throw new ResourceNotFoundException("No Movie with found id: " + id);
        }
    }

}

