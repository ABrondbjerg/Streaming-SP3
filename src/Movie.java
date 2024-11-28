import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Movie {
        private String title;
        private String year;
        private List<String> categories;
        private double rating;

        // Konstruktør
        public Movie(String title, String year, List<String> categories, double rating) {
            this.title = title;
            this.year = year;
            this.categories = categories;
            this.rating = rating;
        }
    public Movie(String title) {
        this.title = title;
    }
        public String getTitle() {
            return title;
        }

        public String getYear() {
            return year;
        }

        public List<String> getCategories() {
            return categories;
        }

        public static List<String> getAllUniqueCategories(List<Movie> movies) {
            Set<String> uniqueCategories = new HashSet<>();
            for (Movie movie : movies) {
                uniqueCategories.addAll(movie.getCategories());
            }
            return new ArrayList<>(uniqueCategories);
        }
        public static List<Movie> getMoviesByCategory(List<Movie> movies, String category) {
            List<Movie> filteredMovies = new ArrayList<>();

            for (Movie movie : movies) {
                for (String movieCategory : movie.getCategories()) {
                    if (movieCategory.equalsIgnoreCase(category.trim())) { // Case-insensitive sammenligning
                        filteredMovies.add(movie);
                    }
                }
            }
            return filteredMovies;
        }


        public double getRating() {
            return rating;
        }

        // Metode til at oprette film fra data

    public static List<Movie> createMovies(String fileName) throws FileNotFoundException {
        List<Movie> movies = new ArrayList<>();

        // Scanner til at læse filen
        Scanner scanner = new Scanner(new File(fileName));

        // Læs hver linje i filen og opret film
        while (scanner.hasNextLine()) {
            String movieData = scanner.nextLine();
            String[] data = movieData.split(";");
            String title = data[0].trim();
            String year = data[1].trim();
            String[] categoryArray = data[2].trim().split(",");
            double rating = Double.parseDouble(data[3].trim().replace(",", "."));

            // Opret liste af kategorier
            List<String> categories = new ArrayList<>();
            for (String category : categoryArray) {
                categories.add(category.trim());
            }

            // Opret film objekt
            Movie movie = new Movie(title, year, categories, rating);
            movies.add(movie);
        }

        return movies;
    }
    public String toString() {
        // Assuming 'title' and 'year' are Strings, and 'rating' is a double
        return String.format("Title: %s, Year: %s, Categories: %s, Rating: %.2f",
                title, year, String.join(", ", categories), rating);
    }
}

