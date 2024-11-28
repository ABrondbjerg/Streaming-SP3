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

    public static void saveMovieToFile() {
        // Construct the file path based on the current user's username
        String userFilePath = "UserData" + File.separator + Streaming.getCurrentUser().getUsername() + "_saved.txt";

        try (FileWriter writer = new FileWriter(userFilePath, true)) {
            // Iterate through the movie list and write each movie with the correct index
            for (Movie movie : Streaming.myList) {
                // Write movie with semicolons between fields, as expected in the file
                writer.write(movie.getTitle() + "; "
                        + movie.getYear() + "; "
                        + movie.getCategories() + "; "
                        + String.format("%.1f", movie.getRating()).replace('.', ',') + ";\n");  // Format rating and replace dot with comma
            }
            System.out.println("Movies saved to " + userFilePath);
        } catch (IOException e) {
            System.out.println("Error saving movies to file: " + e.getMessage());
        }
    }



    public static ArrayList<Movie> readMovieData(String filePath) {
        ArrayList<Movie> movies = new ArrayList<>();
        File file = new File(filePath);

        // Kontrollerer om filen eksisterer
        if (!file.exists()) {
            System.out.println("No saved movies found at: " + filePath);
            return movies; // Returnerer en tom liste
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Trim linjen for at fjerne unødvendige mellemrum
                line = line.trim();
                if (!line.isEmpty()) { // Undgå at tilføje tomme linjer
                    movies.add(new Movie(line)); // Brug den nye konstruktør
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading movie data: " + e.getMessage());
        }

        return movies; // Returnerer listen over film
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
