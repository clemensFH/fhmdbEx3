package at.ac.fhcampuswien.fhmdb.api;

public class MovieAPIRequestBuilder {
    public static final String DELIMITER = "&";
    private StringBuilder url;

    public MovieAPIRequestBuilder(String url) { //? never used
        this.url = new StringBuilder();
    }

    private boolean questionmarkSet() {
        boolean isSet = url.toString().contains("?");
        return isSet;
    }
    public MovieAPIRequestBuilder query(String query){
        if (query != null && !query.isEmpty()) {
            if(!questionmarkSet()){
                url.append("?");
            }
            url.append("query=").append(query).append(DELIMITER);
        }
        return this;
    }
    public MovieAPIRequestBuilder genre(String genre){
        if (genre != null) {
            if(!questionmarkSet()){
                url.append("?");
            }
            url.append("genre=").append(genre).append(DELIMITER);
        }
        return this;
    }
    public MovieAPIRequestBuilder releaseYear(String releaseYear){
        if (releaseYear != null) {
            if(!questionmarkSet()){
                url.append("?");
            }
            url.append("releaseYear=").append(releaseYear).append(DELIMITER);
        }
        return this;
    }
    public MovieAPIRequestBuilder ratingForm(String ratingForm){
        if (ratingForm != null) {
            if(!questionmarkSet()){
                url.append("?");
            }
            url.append("ratingFrom=").append(ratingForm).append(DELIMITER);
        }
        return this;
    }
    public MovieAPIRequestBuilder id(String id){
        if (id != null) {
            if(!questionmarkSet()){
                url.append("?");
            }
            url.append("id=").append(id).append(DELIMITER);
        }
        return this;
    }
    public String build() {
        return url.toString();
    }
}