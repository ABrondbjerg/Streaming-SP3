import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;



public class Main {

    private String movieDataPath;
    private FileIO io;
    private String movieSavePath = "ressource/myList";
    private String userFilePath = "ressource/watchedMovies";
    private ArrayList<Movie> myList;
    private ArrayList<Movie> watchedList;


    public Main() {
        this.io = new FileIO();
        this.myList = new ArrayList<>();
        this.watchedList = new ArrayList<>();
        this.movieDataPath = "ressource/film.txt"; // Path to the movies file

    }

    public void displayMovies() {
        // Read movie data from the file
        ArrayList<Movie> movies = FileIO.readMovieData(movieDataPath);

        // Check if the data was read successfully
        if (movies != null && !movies.isEmpty()) {
            System.out.println("Movies:");
            for (int i = 0; i < movies.size(); i++) {
                Movie movie = movies.get(i);
                System.out.println((i + 1) + ". " + movie); // Call toString() of Movie
            }
        } else {
            System.out.println("No movies found or error reading file.");
        }
    }

    public void saveMovie() {

        // Get user input for movie index
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();  // Get the username from the user

        System.out.print("Enter the movie number to add to your list: ");
        int movieIndex = scanner.nextInt(); // Get the movie index from the user

        // Adjust index since the list is 0-based but the user is selecting 1-based
        int adjustedIndex = movieIndex - 1; // User selects starting from 1

        // Read movie data from the file
        ArrayList<Movie> movies = io.readMovieData(movieDataPath);

        // Check if the index is valid and add the movie to myList
        if (adjustedIndex >= 0 && adjustedIndex < movies.size()) {
            Movie selectedMovie = movies.get(adjustedIndex);
            myList.add(selectedMovie);  // Add to the selected list (myList)
            System.out.println("Added movie: " + selectedMovie.getTitle());

            // Create a new text file for the user (e.g., "Username.txt")
            String userFilePath = "ressource/" + username + "_movie_list.txt"; // Create a path based on username

            // Save the selected movie to the user's file immediately after adding it
            try (FileWriter writer = new FileWriter(userFilePath, true)) {  // 'true' to append to the file
                writer.write(selectedMovie.getTitle() + "; " + selectedMovie.getYear() + "; " + selectedMovie.getCategory() + "; " + selectedMovie.getRating() + ";\n");
                System.out.println("Movie saved to " + userFilePath);
            } catch (IOException e) {
                System.out.println("Error saving movie to file: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid movie index. Please try again.");
        }
    }

    public void playMovie() {
        // Get user input for movie index
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the movie number to add to your list: ");
        int movieIndex = scanner.nextInt(); // Get the movie index from the user

        // Adjust index since the list is 0-based but the user is selecting 1-based
        int adjustedIndex = movieIndex - 1; // User selects starting from 1

        // Read movie data from the file
        ArrayList<Movie> movies = io.readMovieData(movieDataPath);

        if (adjustedIndex >= 0 && adjustedIndex < movies.size()) {
            Movie selectedMovie = movies.get(adjustedIndex);
            myList.add(selectedMovie);  // Add to the selected list (myList)
            System.out.println("You have watched:" + selectedMovie.getTitle());
            System.out.println("Added movie: " + selectedMovie.getTitle());

            // Save the selected movie to the user's file immediately after adding it
            try (FileWriter writer = new FileWriter(userFilePath, true)) {  // 'true' to append to the file
                writer.write(selectedMovie.getTitle() + "; " + selectedMovie.getYear() + "; " + selectedMovie.getCategory() + "; " + selectedMovie.getRating() + ";\n");
                System.out.println("Movie saved to " + userFilePath);
            } catch (IOException e) {
                System.out.println("Error saving movie to file: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid movie index. Please try again.");
        }
    }

    public void searchTitle() {

        displayMovies();

        boolean found = true;
        boolean running = true;

        Scanner scanner = new Scanner(System.in);
        ArrayList<Movie> movies = io.readMovieData(movieDataPath);

        System.out.print("Enter the title of the movie: ");
        String keyword = scanner.nextLine();
        for (Movie movie : movies) {
            // Check if the movie's title contains the keyword (case-insensitive)
            if (movie.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println("Found: " + movie);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No movies found with the title containing: " + keyword);
        }

        while (running) {
            System.out.println("\n--- Movie Menu ---");
            System.out.println("1. Watch Movie");
            System.out.println("2. Play Movie");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1: // Watch Movie
                    playMovie();
                    break;
                case 2: // Save Movie
                    saveMovie();
                    break;
                case 3: // Exit
                    System.out.println("Exiting... Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}