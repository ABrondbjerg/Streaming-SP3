public class Movie {
    private String title;
    private int year;
    private String category;
    private float rating;

    public Movie(String title, int year, String category, float rating) {
        this.title = title;
        this.year = year;
        this.category = category;
        this.rating = rating;
    }

    // Getters
    public String getTitle() { return title; }
    public int getYear() { return year; }
    public String getCategory() { return category; }
    public float getRating() { return rating; }

    @Override
    public String toString() {
        return "Title: " + title + ", Year: " + year + ", Category: " + category + ", Rating: " + rating;
    }
}
