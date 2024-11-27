import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;
import java.util.ArrayList;


public class Streaming {

    public static void startStream() {
        TextUI textUI = new TextUI();
        textUI.displayMsg("Welcome to MouseGun Streams");
    }
// Lav user/bruger

    public static void loginOrAccount(String msg) {
        Scanner scan = new Scanner(System.in);
        System.out.println(msg);
        String userChoice = scan.nextLine().toLowerCase();

        switch (userChoice) {
            case "login":
                if (!userLogin(scan)) { // Hvis login fejler
                    System.out.println("Redicting to registration");
                    userRegistration(scan); // Gå til registrering
                    userLogin(scan); // Log ind efter registrering
                }
                break;

            case "register":
                userRegistration(scan); // Registrer bruger
                userLogin(scan); // Log ind efter registrering
                break;

            default:
                System.out.println("Invalid option. Please enter 'login' or 'register'.");
                break;
        }
    }

    public static boolean userLogin(Scanner scan) {
        System.out.print("Enter username: ");
        String username = scan.nextLine();
        System.out.print("Enter password: ");
        String password = scan.nextLine();

        File userFile = new File("UserData" + File.separator + username + ".txt");
        if (userFile.exists()) {
            try (Scanner fileScanner = new Scanner(userFile)) {
                String storedUsername = fileScanner.nextLine().replace("Username: ", "").trim();
                String storedPassword = fileScanner.nextLine().replace("Password: ", "").trim();

                if (username.equals(storedUsername) && String.valueOf(password.hashCode()).equals(storedPassword)) {
                    loggedInUsername = username;  // Set the logged-in username
                    return true;  // Login successful
                } else {
                    System.out.println("Invalid username or password.");
                    return false; // Login fejlede
                }
            } catch (IOException e) {
                System.out.println("An error occurred while reading the user file.");
            }
        } else {
            System.out.println("No account found for username: " + username);
        }
        return false; // Login fejlede, da filen ikke fandtes
    }


    private static void userRegistration(Scanner scan) {
        System.out.print("Enter username: ");
        String username = scan.nextLine();
        System.out.print("Enter password: ");
        String password = scan.nextLine();
        try {
            // Opret en ny bruger med validering
            User newUser = new User(username, password);
            // Gem brugerdata i en fil
            saveUserToFile(newUser);
            addToUserList(username, String.valueOf(password.hashCode())); //Tilføjer til fællesliste
            System.out.println("Registration successful! You can now log in.");
        } catch (IllegalArgumentException e) {
            // Håndter valideringsfejl
            System.out.println("Registration failed: " + e.getMessage());
        } catch (IOException e) {
            // Handle file writing errors
            System.out.println("Failed to add user to list: " + e.getMessage());
        }
    }

    private static void saveUserToFile(User user) {

        String directoryPath = "UserData";
        File directory = new File(directoryPath);

        String fileName = directory + File.separator + user.getUsername() + ".txt";
        File userFile = new File(fileName);

        if (userFile.exists()) {
            System.out.println("User already exists. Please choose a different username.");
            return;
        }

        try (Writer writer = new FileWriter(userFile)) {
            writer.write("Username: " + user.getUsername() + "\n");
            writer.write("Password: " + user.getPassword() + "\n");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the user file.");
        }
    }

    public static void addToUserList(String username, String hashedPassword) throws IOException {
        File userListFile = new File("users.txt");

        // Opret filen, hvis den ikke findes
        if (!userListFile.exists()) {
            userListFile.createNewFile();
        }

        // Tilføj brugernavn og hash-adgangskode til filen
        FileWriter writer = new FileWriter(userListFile, true); // 'true' gør, at vi skriver til filen uden at overskrive
        writer.write("Username: " + username + ", Password: " + hashedPassword + System.lineSeparator());
        writer.close(); // Luk filen eksplicit
    }

    // Movie afdeling
    public static String movieDataPath = "C:/Users/khnda/IdeaProjects/Streaming-SP3/ressource/film.txt";
    public static FileIO io = new FileIO();
    public static String myList = "ressource/myList";
    public String watched = "ressource/watchedMovies";


    public static void displayMovies() {


        // Read movie data from the file
        ArrayList<Movie> movies = FileIO.readMovieData("ressource/film.txt");

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


    public static void displaySaved()    {

        // Read movie data from the file
        ArrayList<Movie> movies = FileIO.readMovieData("ressource/saved");

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

    public void displayWatched() {

        // Read movie data from the file
        ArrayList<Movie> movies = FileIO.readMovieData("ressource/watched");

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

    public static void saveMovie(Movie selectedMovie) {
        ArrayList<Movie> myList = new ArrayList<>();
        myList.add(selectedMovie);  // Add selected movie to myList
        System.out.println("Added movie: " + selectedMovie.getTitle());

        // Save the selected movie to a file
        String userFilePath = "UserData/" + loggedInUsername + ".txt";  // File to save movie list
        try (FileWriter writer = new FileWriter(userFilePath, true)) {  // true for appending
            writer.write(selectedMovie.getTitle() + "; " + selectedMovie.getYear() + "; " +
                    String.join(", ", selectedMovie.getCategories()) + "; " + selectedMovie.getRating() + ";\n");
            System.out.println("Movie saved to " + userFilePath);
        } catch (IOException e) {
            System.out.println("Error saving movie to file: " + e.getMessage());
        }
    }

//        public void playMovie() {
//            // Get user input for movie index
//            Scanner scanner = new Scanner(System.in);
//
//            System.out.print("Enter the movie number to add to your list: ");
//            int movieIndex = scanner.nextInt(); // Get the movie index from the user
//
//            // Adjust index since the list is 0-based but the user is selecting 1-based
//            int adjustedIndex = movieIndex - 1; // User selects starting from 1
//
//            // Read movie data from the file
//            ArrayList<Movie> movies = io.readMovieData(movieDataPath);
//
//            if (adjustedIndex >= 0 && adjustedIndex < movies.size()) {
//                Movie selectedMovie = movies.get(adjustedIndex);
//                myList.add(selectedMovie);  // Add to the selected list (myList)
//                System.out.println("You have watched:" + selectedMovie.getTitle());
//                System.out.println("Added movie: " + selectedMovie.getTitle());
//
//                // Save the selected movie to the user's file immediately after adding it
//                try (FileWriter writer = new FileWriter(userFilePath, true)) {  // 'true' to append to the file
//                    writer.write(selectedMovie.getTitle() + "; " + selectedMovie.getYear() + "; " + selectedMovie.getCategories() + "; " + selectedMovie.getRating() + ";\n");
//                    System.out.println("Movie saved to " + userFilePath);
//                } catch (IOException e) {
//                    System.out.println("Error saving movie to file: " + e.getMessage());
//                }
//            } else {
//                System.out.println("Invalid movie index. Please try again.");
//            }
//        }
//
//


    private static String loggedInUsername = null;

    public static void searchTitle() {
        // Check if the user is logged in
        if (loggedInUsername == null) {
            System.out.println("You must log in first.");
            return;  // Exit if no user is logged in
        }


        boolean found = false; // Initially, assume no movie is found
        Scanner scanner = new Scanner(System.in);
        ArrayList<Movie> movies = io.readMovieData(movieDataPath);

        // Ask the user for the title keyword
        System.out.print("Enter the title of the movie: ");
        String keyword = scanner.nextLine();

        // Loop through the movies to check for a match
        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);
            // Check if the movie's title contains the keyword (case-insensitive)
            if (movie.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println((i + 1) + ". " + movie);  // Print the matching movie with an index
                found = true;  // A movie was found, so set found to true
            }
        }

        // If no movies were found
        if (!found) {
            System.out.println("No movies found with the title containing: " + keyword);
        } else {
            System.out.print("Enter the number of the movie you want to save: ");
            int movieNumber = scanner.nextInt();

            if (movieNumber > 0 && movieNumber <= movies.size()) {
                Movie selectedMovie = movies.get(movieNumber - 1);

                // Call saveMovie with the selected movie
                saveMovie(selectedMovie);
            } else {
                System.out.println("Invalid movie number.");
            }
        }
    }

}








