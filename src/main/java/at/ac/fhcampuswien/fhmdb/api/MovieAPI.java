package at.ac.fhcampuswien.fhmdb.api;

import at.ac.fhcampuswien.fhmdb.exceptions.MovieApiException;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import okhttp3.*;               //für die HTTP-Anfrage
import com.google.gson.Gson;    //für die JSON-Verarbeitung

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MovieAPI {
    //Ein statisches OkHttpClient-Objekt wird erstellt, um HTTP-Anfragen an die API zu senden.
    private static final String URL = "http://prog2.fh-campuswien.ac.at/movies"; // https if certificates work
    private static final OkHttpClient client = new OkHttpClient();

    // Dies ist die Methodendeklaration für die getAllMovies-Methode. Die Methode gibt eine Liste von Movie-Objekten zurückund kann eine MovieApiException werfen, wenn ein Fehler auftritt.
    public static List<Movie> getAllMovies() throws MovieApiException {
        return getAllMovies(null, null, null, null);
    }
    public static List<Movie> getAllMovies(String query, String genre, String releaseYear, String ratingFrom) throws MovieApiException{
        // Aufbau der URL mit den übergebenen Parametern
        String urlAllQueries = new MovieAPIRequestBuilder()
                .query(query)
                .genre(genre)
                .releaseYear(releaseYear)
                .ratingForm(ratingFrom)
                .build();
        //build() gibt die vollständige URL als Zeichenkette zurück & wird der Variablen urlAllQueries zugewiesen
        System.out.println("query: " + urlAllQueries); // zu Debugging-Zwecken
        /*Request-Objekt erstellt, dass die URL der Anfrage enthält.
        Es werden Header-Eigenschaften gesetzt, um Server mitzuteilen, dass es sich um einen User-Agent handelt.
        Der removeHeader-Aufruf entfernt zuerst den vorhandenen "User-Agent" Header, bevor der neue Header hinzugefügt wird.*/
        Request request = new Request.Builder()
                .url(urlAllQueries)
                .removeHeader("User-Agent")
                .addHeader("User-Agent", "http.agent")  // needed for the server to accept the request
                .build();

        /* try-with-response um HTTP-Aufruf auszuführen und die Antwort zu erhalten.
        Die Response wird in der Klammer deklariert und automatisch geschlossen, nachdem der Codeblock ausgeführt wurde.*/
        try (Response response = client.newCall(request).execute()) {
            // Hier wird der Inhalt der Antwort als Zeichenkette (String) abgerufen
            String responseBody = response.body().string(); // gibt den Inhalt des Antwortkörpers zurück
            Gson gson = new Gson(); //Gson-Objekt Erstellt (Gson ist Java-Biblio. um Json-Objekt in Java-Object umzuwandeln)
            Movie[] movies = gson.fromJson(responseBody, Movie[].class);    //Gson in Array umgewandelt. gson.fromJson() akzeptiert die Antwortzeichenkette (responseBody) & Klassentyp (Movie[].class) & gibt Array von Movie-Objekten zurück
            return Arrays.asList(movies);   //Array in Liste konvertiert und zurückgegeben
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