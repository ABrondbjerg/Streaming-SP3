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
                    System.out.println("Login successful! Welcome, " + username);
                    return true; // Login succesfuldt
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
            addToUserList(newUser); //Tilføjer til fællesliste
            System.out.println("Registration successful! You can now log in.");
        } catch (IllegalArgumentException e) {
            // Håndter valideringsfejl
            System.out.println("Registration failed: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e); //SKAL HAVE KOMMENTAR TIL BRUGER
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
               writer.write("Saved Movies: ");
               System.out.println("User file created: " + userFile.getAbsolutePath());

            } catch (IOException e) {
                System.out.println("An error occurred while saving the user file.");
            }
        }

    public static void addToUserList(User user) throws IOException {
        File userListFile = new File("UserData" + File.separator + "users.txt");

        // Opret filen, hvis den ikke findes
        if (!userListFile.exists()) {
            userListFile.createNewFile();
        }

        // Tilføj brugernavn og hash-adgangskode til filen
        FileWriter writer = new FileWriter(userListFile, true); // 'true' gør, at vi skriver til filen uden at overskrive
        writer.write("Username: " + user.getUsername() + ", Password: " + user.getPassword() + System.lineSeparator());
        writer.close(); // Luk filen eksplicit
    }

    // Movie afdeling
    public static String movieDataPath = "ressource" + File.separator + "film.txt";
    public static FileIO io = new FileIO();


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





