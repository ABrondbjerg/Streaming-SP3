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
                System.out.println("Mac er altså godt");
                writer.write(movie.getTitle() + "; "
                        + movie.getYear() + "; "
                        + movie.getCategories() + "; "
                        + String.format("%.1f", movie.getRating()).replace('.', ',') + ";\n");  // Format rating and replace dot with comma

            System.out.println("Movies saved to " + userFilePath);
        } catch (IOException e) {
            System.out.println("Error saving movies to file: " + e.getMessage());
        }
    }



    public static ArrayList<Movie> readMovieData(String filePath) {
        ArrayList<Movie> movies = new ArrayList<>();
        File file = new File(filePath);

        try (Scanner scanner = new Scanner(file)) {

            // Skip the first two lines (username and password)

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                // Remove trailing semicolon if present and replace commas in ratings with periods
                if (line.endsWith(";")) {
                    line = line.substring(0, line.length() - 1);
                }
                line = line.replace(',', '.');  // Convert rating commas back to dots

                String[] parts = line.split(";");
                String[] categoryParts = line.split(",");// Split by semicolons

                if (parts.length == 4) {  // There should be 4 fields (title, year, categories, rating)
                    try {
                        String title = parts[0].trim();
                        String year = parts[1].trim();
                        List <String> categories = List.of(categoryParts);
                        float rating = Float.parseFloat(parts[3].trim());

                        // Create a Movie object and add it to the list
                        movies.add(new Movie(title, year, categories, rating));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number format in line: " + line);
                    }
                } else {
                    System.out.println("Invalid format (unexpected number of fields) in line: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
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
