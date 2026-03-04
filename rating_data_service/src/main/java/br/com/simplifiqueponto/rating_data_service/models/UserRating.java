package br.com.simplifiqueponto.rating_data_service.models;

import java.util.List;

public class UserRating {
    private List<Rating> userRatings;

    public List<Rating> getUserRatings() {
        return userRatings;
    }

    public void setUserRatings(List<Rating> userRatings) {
        this.userRatings = userRatings;
    }
}

