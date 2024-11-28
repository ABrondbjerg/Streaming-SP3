import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileIO {

    public static ArrayList<String> readUserData(String path) {
        ArrayList<String> data = new ArrayList();
        File file = new File(path);
        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                data.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File was not found");
        }
        return data;
    }

    public static void saveMovieToFile(Movie movie) {
        // Construct the file path based on the current user's username
        String userFilePath = "UserData" + File.separator + Streaming.getCurrentUser().getUsername() + "_saved.txt";

        try (FileWriter writer = new FileWriter(userFilePath, true)) {
            // Iterate through the movie list and write each movie with the correct index

                // Write movie with semicolons between fields, as expected in the file
                writer.write(movie.getTitle() + "; "
                        + movie.getYear() + "; "
                        + movie.getCategories() + "; "
                        + String.format("%.1f", movie.getRating()).replace('.', ',') + ";\n");  // Format rating and replace dot with comma

        } catch (IOException e) {
            System.out.println("Error saving movies to file: " + e.getMessage());
        }
    }
        public static ArrayList<Movie> readMovieData(String filePath) throws IOException {
            ArrayList<Movie> movies = new ArrayList<>();
            File file = new File(filePath);
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] fields = line.split(";");
                    String[] fields2 = line.split(",");

                    // Validate the number of fields
                    if (fields.length == 4) {
                        // Assuming fields are title, year, genre, and rating
                        String title = fields[0].trim(); // Titlen
                        String year = fields[1].trim();  // År
                        String categoryString = fields[2].trim(); // Kategorier
                        String ratingString = fields[3].trim();  // Rating
                        ratingString = ratingString.replace(',', '.');
                        double rating = Double.parseDouble(ratingString);

                        categoryString = categoryString.replace("[", "").replace("]", "");
                        List<String> categories = List.of(categoryString.split(",\\s*"));

                        Movie movie = new Movie(title, year, categories, rating);
                        movies.add(movie);
                    } else {
                        System.out.println("Invalid format in line: " + line);
                    }
                }
            }
            return movies;
        }


    static void updateUserFile(ArrayList<Movie> movies, String userFilePath) {
        try (FileWriter writer = new FileWriter(userFilePath, false)) {
            for (Movie movie : movies) {
                writer.write(movie.getTitle() + "; " + movie.getYear() + "; "
                        + movie.getCategories() + "; " + movie.getRating() + ";\n");
            }
            System.out.println("User file updated successfully.");
        } catch (IOException e) {
            System.out.println("Error updating user file: " + e.getMessage());
        }
    }

    public static void deleteMovie(String filePath, int movieIndex) {

        try {
            // Read the current movie list
            ArrayList<Movie> movies = readMovieData(filePath);

            if (movies == null || movies.isEmpty()) {
                System.out.println("No movies found in the file.");
                return;
            }

            if (movieIndex < 0 || movieIndex >= movies.size()) {
                System.out.println("Invalid movie index.");
                return;
            }

            // Remove the movie
            Movie removedMovie = movies.remove(movieIndex);
            System.out.println("Deleted movie: " + removedMovie.getTitle());

            // Write updated movie list back to the file
            try (FileWriter writer = new FileWriter(filePath, false)) { // Overwrite file
                for (Movie movie : movies) {
                    writer.write(movie.getTitle() + "; " + movie.getYear() + "; " +
                            String.join(", ", movie.getCategories()) + "; " + movie.getRating() + ";\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Error while updating the file: " + e.getMessage());
        }

    }
}
