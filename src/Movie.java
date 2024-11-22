import java.util.ArrayList;
import java.util.List;

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


        public String getTitle() {
            return title;
        }

        public String getYear() {
            return year;
        }

        public List<String> getCategories() {
            return categories;
        }

        public double getRating() {
            return rating;
        }

        // Metode til at oprette film fra data

        public static List<Movie> createMovies(String[] moviedata) {
            List<Movie> movies = new ArrayList<>();

            for (String movieData : moviedata) {

                String[] data = movieData.split(";");
                String title = data[0].trim();
                String year = data[1].trim();
                String[] categoryArray = data[2].trim().split(",");
                double rating = Double.parseDouble(data[3].trim());

                // Opret en liste af kategorier
                List<String> categories = new ArrayList<>();
                for (String category : categoryArray) {
                    categories.add(category.trim());
                }

                // Opret og tilføj film til listen
                Movie movie = new Movie(title, year, categories, rating);
                movies.add(movie);
            }

            return movies; // Returner listen af film
        }
    }

