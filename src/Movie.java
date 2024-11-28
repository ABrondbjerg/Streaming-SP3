public class Movie {
    private String title;
    private int year;
    private String category;
    private float rating;

    // Constructor
    public Movie(String title, int year, String category, float rating) {
        this.title = title;
        this.year = year;
        this.category = category;
        this.rating = rating;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getCategory() {
        return category;
    }

    public float getRating() {
        return rating;
    }


    // toString method to provide a string representation of the Movie object
    @Override
    public String toString() {
        return this.title + " (" + this.year + "), " + this.category + ", Rating: " + this.rating;
    }

    public String toFileString(int index) {
        // Adjust the format of how the movie is written to the file (no semicolon here for categories)
        return getTitle() + ";" + getYear() + ";" + getCategory() + ";" + getRating() + ";";
    }

}
