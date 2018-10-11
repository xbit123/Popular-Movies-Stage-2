package com.udproj.popularmovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class JsonUtils {

    //This method parses JSON from a web response and returns a Movie object
    public static Movie[] getMoviesFromJson(String moviesJsonString) throws JSONException {
        final String TMDB_RESULTS = "results";
        final String TMDB_VOTE_COUNT = "vote_count";
        final String TMDB_VOTE_AVERAGE = "vote_average";
        final String TMDB_TITLE = "title";
        final String TMDB_POSTER_PATH = "poster_path";
        final String TMDB_OVERVIEW = "overview";
        final String TMDB_RELEASE_DATE = "release_date";

        JSONObject moviesJson = new JSONObject(moviesJsonString);

        //Check if an error was received from server
        if (moviesJson.has("errors")) {
            throw new JSONException(moviesJson.getJSONArray("errors").getString(0));
        }

        JSONArray resultsArray = moviesJson.getJSONArray(TMDB_RESULTS);
        Movie[] parsedMovies = new Movie[resultsArray.length()];
        for (int i = 0; i < resultsArray.length(); i++) {
            JSONObject movieJsonObj = resultsArray.getJSONObject(i);
            parsedMovies[i] = new Movie(
                    movieJsonObj.getInt(TMDB_VOTE_COUNT),
                    movieJsonObj.getDouble(TMDB_VOTE_AVERAGE),
                    movieJsonObj.getString(TMDB_TITLE),
                    movieJsonObj.getString(TMDB_POSTER_PATH),
                    movieJsonObj.getString(TMDB_OVERVIEW),
                    Integer.valueOf(movieJsonObj.getString(TMDB_RELEASE_DATE).substring(0,4))
            );
        }
        return parsedMovies;
    }
}
