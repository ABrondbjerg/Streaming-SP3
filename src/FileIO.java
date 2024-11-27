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


    public static void saveMovieToFile(Movie movie) {

        String filePath = "UserData" + File.separator + "movies.txt";

        try (FileWriter writer = new FileWriter(filePath, true)) { // Append mode
            // Ensure categories is not null and join them with ". "
            String categoriesString = movie.getCategories() != null ? String.join(". ", movie.getCategories()) : "";

            // Prepare the movie data to be written to the file with proper formatting
            String movieData = String.format("Title: %s, Year: %s, Categories: %s, Rating: %.2f\n",
                    movie.getTitle(), movie.getYear(), categoriesString, movie.getRating());

            // Write the movie data to the file
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
                if (line.endsWith(";")) {
                    line = line.substring(0, line.length() - 1);
                }
                line = line.replace(',', '.');
                String[] parts = line.split(";");
                if (parts.length == 4) {
                    try {
                        String title = parts[0].trim();
                        String year = parts[1].trim();
                        String[] categories = parts[2].trim().split(",");
                        double rating = Float.parseFloat(parts[3].trim());
                        Movie movie = new Movie(title, year, categories, rating);
                        movies.add(movie);

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

        public static void playMovie(Movie movie) {

                ArrayList<Movie> myList = new ArrayList<>();

                // Get user input for movie index
                Scanner scanner = new Scanner(System.in);

                System.out.print("Enter the movie number to add to your list: ");
                int movieIndex = scanner.nextInt(); // Get the movie index from the user

                // Adjust index since the list is 0-based but the user is selecting 1-based
                int adjustedIndex = movieIndex - 1; // User selects starting from 1

                // Read movie data from the file
                ArrayList<Movie> movies = readMovieData("ressource/film.txt");

                if (adjustedIndex >= 0 && adjustedIndex < movies.size()) {
                    Movie selectedMovie = movies.get(adjustedIndex);
                    myList.add(selectedMovie);  // Add to the selected list (myList)
                    System.out.println("You have watched:" + selectedMovie.getTitle());
                    System.out.println("Added movie: " + selectedMovie.getTitle());

                    // Save the selected movie to the user's file immediately after adding it
                    saveMovieToFile(selectedMovie);
                }
            }


        }

