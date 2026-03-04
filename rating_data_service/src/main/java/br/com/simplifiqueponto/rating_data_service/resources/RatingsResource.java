package br.com.simplifiqueponto.rating_data_service.resources;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.simplifiqueponto.rating_data_service.models.Rating;
import br.com.simplifiqueponto.rating_data_service.models.UserRating;


@RestController
@RequestMapping("/ratingsdata")
public class RatingsResource {

    @RequestMapping("/{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId) {
        return new Rating(movieId, 4);
    }

    @RequestMapping("/users/{userId}")
    public UserRating getUserRating(@PathVariable("userId") String userId) {
        List<Rating> ratings = Arrays.asList(
            new Rating("1", 4),
            new Rating("2", 3),
            new Rating("3", 5),
            new Rating("4", 1),
            new Rating("5", 2)
        );
        UserRating userRating = new UserRating();
        userRating.setUserRatings(ratings);
        return userRating;
    }
    
}
