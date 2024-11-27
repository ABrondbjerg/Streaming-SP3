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

    public static String movieDataPath = "ressource/film.txt";
    public static FileIO io = new FileIO();

    private static String loggedInUsername;

    public static void searchTitle() {


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
            // Ask the user to choose a movie
            System.out.print("Enter the number of the movie you want to save: ");
            int movieNumber = scanner.nextInt();

            // Ensure the number is valid and corresponds to a movie
            if (movieNumber > 0 && movieNumber <= movies.size()) {
                Movie selectedMovie = movies.get(movieNumber - 1);  // Select the movie

                // Call saveMovieToFile with the selected movie
                io.saveMovieToFile(selectedMovie);  // Save the selected movie to file
                System.out.println("Movie saved: " + selectedMovie.getTitle());
            } else {
                System.out.println("Invalid movie number.");
            }
        }
    }
}








