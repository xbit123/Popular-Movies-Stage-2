package com.udproj.popularmovies.utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String API_KEY = "";
    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
    private static final String MOVIE_LIST_BASE_URL = "http://api.themoviedb.org/3/movie/";

    private static final String IMAGE_DEFAULT_SIZE_PATH = "w342";
    private static final String REVIEWS_PATH = "reviews";
    private static final String TRAILERS_PATH = "videos";
    private static final String API_KEY_PARAM = "api_key";

    private static final String YOUTUBE_THUMBNAIL_BASE_URL = "https://img.youtube.com/vi/";
    private static final String YOUTUBE_THUMBNAIL_IMAGE_NAME = "mqdefault.jpg";

    private static final String YOUTUBE_VIDEO_BASE_URL = "https://www.youtube.com/watch";
    private static final String YOUTUBE_V = "v";

    public static Uri buildTrailerUri(String youtubeId) {
        return Uri.parse(YOUTUBE_VIDEO_BASE_URL).buildUpon()
                .appendQueryParameter(YOUTUBE_V, youtubeId)
                .build();
    }

    public static URL buildTrailerThumbnailUrl(String youtubeId) {
        Uri uri = Uri.parse(YOUTUBE_THUMBNAIL_BASE_URL).buildUpon()
                .appendPath(youtubeId)
                .appendPath(YOUTUBE_THUMBNAIL_IMAGE_NAME)
                .build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built Thumbnail URI " + url);

        return url;
    }

    public static URL buildTrailersUrl(int id) {
            Uri uri = Uri.parse(MOVIE_LIST_BASE_URL).buildUpon()
                .appendPath(String.valueOf(id))
                .appendPath(TRAILERS_PATH)
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built Trailers URI " + url);

        return url;
    }

    public static URL buildReviewsUrl(int id) {
        Uri uri = Uri.parse(MOVIE_LIST_BASE_URL).buildUpon()
                .appendPath(String.valueOf(id))
                .appendPath(REVIEWS_PATH)
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built Reviews URI " + url);

        return url;
    }

    public static URL buildMovieListUrl(String list) {
        Uri uri = Uri.parse(MOVIE_LIST_BASE_URL).buildUpon()
                .appendPath(list)
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built Movie List URI " + url);

        return url;
    }

    public static URL buildImageUrl(String path){
        Uri uri = Uri.parse(IMAGE_BASE_URL).buildUpon()
                .appendPath(IMAGE_DEFAULT_SIZE_PATH)
                .appendEncodedPath(path)
                .build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built Image URI " + url);

        return url;
    }

    //Returns String response from http Url
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
