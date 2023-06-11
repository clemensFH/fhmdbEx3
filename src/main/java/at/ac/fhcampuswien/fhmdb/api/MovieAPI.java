package at.ac.fhcampuswien.fhmdb.api;

import at.ac.fhcampuswien.fhmdb.exceptions.MovieApiException;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import okhttp3.*;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MovieAPI {
    private static final String URL = "http://prog2.fh-campuswien.ac.at/movies"; // https if certificates work
    private static final OkHttpClient client = new OkHttpClient();

    public static List<Movie> getAllMovies() throws MovieApiException {
        return getAllMovies(null, null, null, null);
    }
    public static List<Movie> getAllMovies(String query, Genre genre, String releaseYear, String ratingFrom) throws MovieApiException{
        String urlAllQueries = new MovieAPIRequestBuilder(URL)
                .query(query)
                .genre(genre.toString())
                .releaseYear(releaseYear)
                .ratingForm(ratingFrom)
                .build();
        Request request = new Request.Builder()
                .url(urlAllQueries)
                .removeHeader("User-Agent")
                .addHeader("User-Agent", "http.agent")  // needed for the server to accept the request
                .build();
        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            Gson gson = new Gson();
            Movie[] movies = gson.fromJson(responseBody, Movie[].class);
            return Arrays.asList(movies);
        } catch (Exception e) {
            throw new MovieApiException("MovieApiException: Error requesting movie from API. Please check your internet connection and try again later.\n");
        }
    }
    public Movie requestMovieById(UUID id) throws MovieApiException{
        String urlID = new MovieAPIRequestBuilder(URL + "/movies" + id).build();
        Request request = new Request.Builder()
                .url(urlID)
                .build();
        try (Response response = client.newCall(request).execute()) {
            Gson gson = new Gson();
            return gson.fromJson(response.body().string(), Movie.class);
        } catch (Exception e) {
            throw new MovieApiException("MovieApiException: Error requesting movie from API. Please check your internet connection and try again later.\n");
        }
    }
}
/*  Ursprünglich
    private String build(UUID id) { //build ID URL
        StringBuilder url = new StringBuilder(URL);
        if (id != null) {
            url.append("/").append(id);
        }
        return url.toString();
    }

    private static String buildUrl(String query, Genre genre, String releaseYear, String ratingFrom) { //build specificRequest-URL
        StringBuilder url = new StringBuilder(URL);
        if ((query != null && !query.isEmpty()) ||          //if anyone is filled
                genre != null || releaseYear != null || ratingFrom != null) {
            url.append("?");
            if (query != null && !query.isEmpty()) {        //check all parameters and add them to the url
                url.append("query=").append(query).append(DELIMITER); //query="Wort"&
            }
            if (genre != null) {
                url.append("genre=").append(genre).append(DELIMITER);
            }
            if (releaseYear != null) {
                url.append("releaseYear=").append(releaseYear).append(DELIMITER);
            }
            if (ratingFrom != null) {
                url.append("ratingFrom=").append(ratingFrom).append(DELIMITER);
            }
        }
        return url.toString();
    }

    public static List<Movie> getAllMovies(String query, Genre genre, String releaseYear, String ratingFrom) throws MovieApiException{
        String url = buildUrl(query, genre, releaseYear, ratingFrom);
        Request request = new Request.Builder()
                .url(url)
                .removeHeader("User-Agent")
                .addHeader("User-Agent", "http.agent")  // needed for the server to accept the request
                .build();
        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            Gson gson = new Gson();
            Movie[] movies = gson.fromJson(responseBody, Movie[].class);
            return Arrays.asList(movies);
        } catch (Exception e) {
            throw new MovieApiException("MovieApiException: Error requesting movie from API. Please check your internet connection and try again later.\n");
        }

    public Movie getMovieById(String id) throws MovieApiException {
        String url = buildUrl(UUID.fromString(id));
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            Gson gson = new Gson();
            return gson.fromJson(response.body().string(), Movie.class);
        } catch (IOException e) {
            throw new MovieApiException("MovieApiException: Error retrieving movie from API. Please check your internet connection and try again later.\n");
        }
    }

    }

 */