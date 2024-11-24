import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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

    public static void saveMovieToFile(Movie movie, String filePath) {
        try (FileWriter writer = new FileWriter(filePath, true)) { // Append mode
            String movieData = movie.getTitle() + ";" +
                    movie.getYear() + ";" +
                    movie.getCategories() + ";" +
                    movie.getRating() + ";\n";
            writer.write(movieData);
            System.out.println("Movie saved successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the movie: " + e.getMessage());
        }
    }

    public static ArrayList<Movie> readMovieData(String filePath) {
        ArrayList<Movie> movies = new ArrayList<>();
        File file = new File(filePath);

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                // Remove trailing semicolon and replace commas in ratings with periods
                if (line.endsWith(";")) {
                    line = line.substring(0, line.length() - 1);
                }
                line = line.replace(',', '.');

                String[] parts = line.split(";"); // Split by semicolons

                if (parts.length == 4) {
                    try {
                        String title = parts[0].trim();
                        int year = Integer.parseInt(parts[1].trim());
                        String category = parts[2].trim();
                        float rating = Float.parseFloat(parts[3].trim());

                        // Create a Movie object and add it to the list
                        //movies.add(new Movie(title, year, category, rating));
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

}
