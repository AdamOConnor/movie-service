package edu.tus.movieservice.dao;

import edu.tus.movieservice.dto.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Integer> {
    // Derived Query - search for movies by title and author containing and ignore case
    List<Movie> findByNameAndDirectorContainingIgnoreCase(String name, String director);

    // Derived Query - search by name ignoring case
    List<Movie> findByNameContainingIgnoreCase(String name);

    // Derived Query - find by Director ignoring case
    List<Movie> findByDirectorContainingIgnoreCase(String director);
}
