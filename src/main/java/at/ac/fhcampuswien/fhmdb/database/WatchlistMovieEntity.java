package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;
import java.lang.StringBuilder;

@DatabaseTable(tableName = "watchlist")
public class WatchlistMovieEntity {
    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField()
    private String apiId;

    @DatabaseField()
    private String title;

    @DatabaseField()
    private String description;

    @DatabaseField()
    private String genres;

    @DatabaseField()
    private int releaseYear;

    @DatabaseField()
    private String imgUrl;

    @DatabaseField(columnName = "length")
    private int lengthInMinutes;

    @DatabaseField()
    private double rating;

    public WatchlistMovieEntity(){} // no-arg constructor needed so that object can be returned by a query
    public WatchlistMovieEntity(String apiId, String title, String description, List<Genre> genres, int releaseYear, String imgUrl, int lengthInMinutes, double rating) {
        this.apiId = apiId;
        this.title = title;
        this.description = description;
        this.genres = genresToString(genres);
        this.releaseYear = releaseYear;
        this.imgUrl = imgUrl;
        this.lengthInMinutes = lengthInMinutes;
        this.rating = rating;
    }

    // ToDo: Genres to String
    public String genresToString(List<Genre> genres) {
        StringBuilder strbul = new StringBuilder();
        for (Genre genresElement : genres) {
            strbul.append(genresElement);
            strbul.append(",");
        }
        strbul.setLength(strbul.length()-1); // to remove last comma
        String newGenreString = strbul.toString();
        return newGenreString;
    }
    // https://java2blog.com/java-list-to-string/#:~:text=We%20can%20use%20StringBuilder%20class,String%20class%20at%20the%20end.
}