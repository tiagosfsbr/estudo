package br.com.simplifiqueponto.movie_info_service.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.simplifiqueponto.models.Movie;

import org.springframework.web.bind.annotation.PathVariable;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/movies")
public class MovieResource {
    
    private static final List<Movie> MOVIES = Arrays.asList(
        new Movie("1", "Inception3"),
        new Movie("2", "The Dark Knight"),
        new Movie("3", "Interstellar"),
        new Movie("4", "The Matrix"),
        new Movie("5", "Pulp Fiction")
    );
    
    @RequestMapping("/{movieId}")
    public Movie getMovieInfo(@PathVariable("movieId") String movieId) {
        return MOVIES.stream()
            .filter(movie -> movie.getMovieId().equals(movieId))
            .findFirst()
            .orElse(new Movie(movieId, "Filme não encontrado"));
    }
    
}
