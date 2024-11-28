import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileIO {

    public static ArrayList<Movie> myList = new ArrayList<>();  // Define myList globally


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

    public static void saveMovieToFile(ArrayList<Movie> myList, String userFilePath) {
        try (FileWriter writer = new FileWriter(userFilePath, true)) {  // 'false' ensures the file is overwritten
            // Iterate through the movie list and write each movie with the correct index
            for (int i = 0; i < myList.size(); i++) {
                Movie movie = myList.get(i);
                // Write movie with semicolons between fields, as expected in the file
                writer.write(movie.getTitle() + "; "
                        + movie.getYear() + "; "
                        + movie.getCategory() + "; "
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

        try (Scanner scanner = new Scanner(file)) {

            // Skip the first two lines (username and password)

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                // Remove trailing semicolon if present and replace commas in ratings with periods
                if (line.endsWith(";")) {
                    line = line.substring(0, line.length() - 1);
                }
                line = line.replace(',', '.');  // Convert rating commas back to dots

                String[] parts = line.split(";"); // Split by semicolons

                if (parts.length == 4) {  // There should be 4 fields (title, year, categories, rating)
                    try {
                        String title = parts[0].trim();
                        int year = Integer.parseInt(parts[1].trim());
                        String categories = parts[2].trim();
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

    // Helper method to update the user's file
    static void updateUserFile(ArrayList<Movie> movies, String userFilePath) {
        try (FileWriter writer = new FileWriter(userFilePath, false)) {
            for (Movie movie : movies) {
                writer.write(movie.getTitle() + "; " + movie.getYear() + "; "
                        + movie.getCategory() + "; " + movie.getRating() + ";\n");
            }
            System.out.println("User file updated successfully.");
        } catch (IOException e) {
            System.out.println("Error updating user file: " + e.getMessage());
        }
    }

}
