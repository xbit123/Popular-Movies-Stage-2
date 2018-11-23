package com.udproj.popularmovies.utils;

import com.udproj.popularmovies.model.Movie;
import com.udproj.popularmovies.model.Review;
import com.udproj.popularmovies.model.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class JsonUtils {

    //Parses JSON from the web response and returns a List of Trailers
    public static List<Trailer> getTrailersFromJson(String trailersJsonString) throws JSONException {
        final String TMDB_RESULTS = "results";
        final String TMDB_ID = "id";
        final String TMDB_YOUTUBE_ID = "key";
        final String TMDB_TITLE = "name";
        final String TMDB_TYPE = "type";
        final String TMDB_TRAILER = "Trailer";

        JSONObject trailersJson = new JSONObject(trailersJsonString);

        if (trailersJson.has("erorrs")) {
            throw new JSONException(trailersJson.getJSONArray("errors").getString(0));
        }

        JSONArray resultsArray = trailersJson.getJSONArray(TMDB_RESULTS);
        List<Trailer> parsedTrailers = new ArrayList<>();
        for (int i = 0; i < resultsArray.length(); i++) {
            JSONObject trailerJsonObj = resultsArray.getJSONObject(i);
            if (trailerJsonObj.getString(TMDB_TYPE).equals(TMDB_TRAILER)) {
                parsedTrailers.add(new Trailer(
                        trailerJsonObj.getString(TMDB_ID),
                        trailerJsonObj.getString(TMDB_YOUTUBE_ID),
                        trailerJsonObj.getString(TMDB_TITLE)
                ));
            }
        }
        return parsedTrailers;
    }

    //Parses JSON from the web response and returns a List of Reviews
    public static List<Review> getReviewsFromJson(String reviewsJsonString) throws JSONException {
        final String TMDB_RESULTS = "results";
        final String TMDB_AUTHOR = "author";
        final String TMDB_CONTENT = "content";
        final String TMDB_ID = "id";

        JSONObject reviewsJson = new JSONObject(reviewsJsonString);

        if (reviewsJson.has("erorrs")) {
            throw new JSONException(reviewsJson.getJSONArray("errors").getString(0));
        }

        JSONArray resultsArray = reviewsJson.getJSONArray(TMDB_RESULTS);
        List<Review> parsedReviews = new ArrayList<>();
        for (int i = 0; i < resultsArray.length(); i++) {
            JSONObject reviewJsonObj = resultsArray.getJSONObject(i);
            parsedReviews.add(new Review(
                    reviewJsonObj.getString(TMDB_AUTHOR),
                    reviewJsonObj.getString(TMDB_CONTENT),
                    reviewJsonObj.getString(TMDB_ID)
            ));
        }
        return parsedReviews;
    }

    //Parses JSON from the web response and returns a List of Movies
    public static List<Movie> getMoviesFromJson(String moviesJsonString) throws JSONException {
        final String TMDB_RESULTS = "results";
        final String TMDB_VOTE_COUNT = "vote_count";
        final String TMDB_ID = "id";
        final String TMDB_VOTE_AVERAGE = "vote_average";
        final String TMDB_TITLE = "title";
        final String TMDB_POPULARITY = "popularity";
        final String TMDB_POSTER_PATH = "poster_path";
        final String TMDB_OVERVIEW = "overview";
        final String TMDB_RELEASE_DATE = "release_date";

        JSONObject moviesJson = new JSONObject(moviesJsonString);

        //Check if an error was received from server
        if (moviesJson.has("errors")) {
            throw new JSONException(moviesJson.getJSONArray("errors").getString(0));
        }

        JSONArray resultsArray = moviesJson.getJSONArray(TMDB_RESULTS);
        List<Movie> parsedMovies = new ArrayList<>();
        for (int i = 0; i < resultsArray.length(); i++) {
            JSONObject movieJsonObj = resultsArray.getJSONObject(i);
            parsedMovies.add(new Movie(
                    movieJsonObj.getInt(TMDB_VOTE_COUNT),
                    movieJsonObj.getInt(TMDB_ID),
                    movieJsonObj.getDouble(TMDB_VOTE_AVERAGE),
                    movieJsonObj.getString(TMDB_TITLE),
                    movieJsonObj.getDouble(TMDB_POPULARITY),
                    movieJsonObj.getString(TMDB_POSTER_PATH),
                    movieJsonObj.getString(TMDB_OVERVIEW),
                    Integer.valueOf(movieJsonObj.getString(TMDB_RELEASE_DATE).substring(0, 4))
            ));
        }
        return parsedMovies;
    }
}
