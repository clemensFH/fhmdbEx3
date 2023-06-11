package at.ac.fhcampuswien.fhmdb.api;

import at.ac.fhcampuswien.fhmdb.models.Genre;

import java.util.UUID;

public class MovieAPIRequestBuilder {
    public static final String DELIMITER = "&";
    private static final String URL = "http://prog2.fh-campuswien.ac.at/movies"; // https if certificates work

    private String query;
    private String genre;
    private String releaseYear;    //private int releaseYear;
    private String ratingForm;    //private double ratingForm;
    private String id;

    private StringBuilder url = new StringBuilder();



    public MovieAPIRequestBuilder(String url) {
        this.url = new StringBuilder();
    }

    public MovieAPIRequestBuilder query(String query){
        this.query = query;
        return this;
    }
    public MovieAPIRequestBuilder genre(String genre){
        this.genre = genre;
        return this;
    }
    public MovieAPIRequestBuilder releaseYear(String releaseYear){
        this.releaseYear = releaseYear;
        return this;
    }
    public MovieAPIRequestBuilder ratingForm(String ratingForm){
        this.ratingForm = ratingForm;
        return this;
    }
    public MovieAPIRequestBuilder id(String id){
        this.id = id;
        return this;
    }
    public String build() { //String query, Genre genre, String releaseYear, String ratingFrom, UUID id
        StringBuilder url = new StringBuilder();

        if ((query != null && !query.isEmpty()) ||          //if anyone is filled
                genre != null || releaseYear != null || ratingForm != null) {
            url.append("?");
            //check all parameters and add them to the url
            if (query != null && !query.isEmpty()) {
                url.append("query=").append(query).append(DELIMITER);
            }
            if (genre != null) {
                url.append("genre=").append(genre).append(DELIMITER);
            }
            if (releaseYear != null) {
                url.append("releaseYear=").append(releaseYear).append(DELIMITER);
            }
            if (ratingForm != null) {
                url.append("ratingFrom=").append(ratingForm).append(DELIMITER);
            }
            if (id != null) {
                url.append("id=").append(id).append(DELIMITER);
            }
        }
        return url.toString();
    }
}

    /*
        public MovieAPIRequestBuilder queryStart(String query, Genre genre, String releaseYear, String ratingFrom) {
        if ((query != null && !query.isEmpty()) ||
                genre != null || releaseYear != null || ratingFrom != null) {  //if anyone is filled
            url.append(QUERY_START);
        }
        return this;
    }
    
    public MovieAPIRequestBuilder id(UUID id){
        if (id != null) {
            url.append("id=").append(id).append(DELIMITER);
        }
        return this;
    }
    */

/*  //Tutorium
    public MovieAPIRequestBuilder query(String query){
        this.query = query;
        return this;
    }
    public MovieAPIRequestBuilder genre(String genre){
        this.genre = genre;
        return this;
    }
    public MovieAPIRequestBuilder releaseYear(String releaseYear){
        this.releaseYear = releaseYear;
        return this;
    }
    public MovieAPIRequestBuilder ratingForm(String ratingForm){
        this.ratingForm = ratingForm;
        return this;
    }

    //Setter
    public void setQuery(String query) {
        this.query = query;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setRatingForm(String ratingForm) {
        this.ratingForm = ratingForm;
    }

    public void setId(String id) {
        this.id = id;
    }

    //Leon
    public String build(String query, Genre genre, String releaseYear, String ratingFrom, UUID id) {
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
            if (id != null) {
                url.append("id=").append(id).append(DELIMITER);
            }
        }
        return url.toString();
    }
 */
/*
private static boolean filled;
    //public static final String QUERY_START = "?";
            public static boolean queryStart(String query, Genre genre, String releaseYear, String ratingFrom) {
        if ((query != null && !query.isEmpty()) ||
                genre != null || releaseYear != null || ratingFrom != null) {  //if anyone is filled
            filled = true;
        } else {
            filled = false;
        }
        return filled;
    }
 */
