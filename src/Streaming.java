import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;
import java.util.ArrayList;



public class Streaming {
    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;  // Hent den aktuelle bruger
    }

    public static void setCurrentUser(User user) {
        currentUser = user;  // Sæt den aktuelle bruger
    }

    public static ArrayList<Movie> myList = new ArrayList<>();

    public static void startStream() {
        TextUI textUI = new TextUI();
        textUI.displayMsg("Welcome to MouseGun Streams");
    }

    public static void loginOrAccount(String msg) {
        Scanner scan = new Scanner(System.in);
        System.out.println(msg);
        String userChoice = scan.nextLine().toLowerCase();

        switch (userChoice) {
            case "login":
                if (userLogin(scan)) {
                    // Hvis login lykkes, stop her
                    break;
                } else {
                    // Hvis login fejler, redirect til registrering
                    System.out.println("Redirecting to registration...");
                    userRegistration(scan);
                    userLogin(scan); // Forsøg login efter registrering
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
                    currentUser = new User(username, password);  // Set currentUser to the logged-in user
                    System.out.println("Login successful! Welcome, " + username);
                    return true; // Login successful
                } else {
                    System.out.println("Invalid username or password.");
                    return false; // Login failed
                }
            } catch (IOException e) {
                System.out.println("An error occurred while reading the user file.");
            }
        } else {
            System.out.println("No account found for username: " + username);
        }
        return false; // Login failed, file not found
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
            userFileWatched(newUser);
            userFileSaved(newUser);
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
            writer.write("Password: " + user.getPassword() + "\n");
            //System.out.println("User file created: " + userFile.getAbsolutePath()); //Debug sti.

        } catch (IOException e) {
            System.out.println("An error occurred while saving the user file.");
        }
    }

    private static void userFileWatched(User user) throws IOException {

        String directoryPath = "UserData";
        File directory = new File(directoryPath);

        String fileName = directory + File.separator + user.getUsername() + "_watched.txt";
        File userWatchedFile = new File(fileName);

        Writer writer = new FileWriter(userWatchedFile);

    }

    private static void userFileSaved(User user) throws IOException {

        String directoryPath = "UserData";
        File directory = new File(directoryPath);

        String fileName = directory + File.separator + user.getUsername() + "_saved.txt";
        File userWatchedFile = new File(fileName);

        Writer writer = new FileWriter(userWatchedFile);

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


    public static void searchTitle() throws IOException {

        boolean found = true;
        boolean running = true;

        Scanner scanner = new Scanner(System.in);
        ArrayList<Movie> movies = FileIO.readMovieData(movieDataPath);

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
    }

    public static void playMovie(String selectedMovie, User currentUser) throws IOException {
        TextUI textUI = new TextUI();
        textUI.displayMsg("You are now watching: " + selectedMovie);

        // Sørg for at bruge brugerens rigtige filnavn (brug currentUser.getUsername())
        String fileName = "UserData" + File.separator + Streaming.currentUser.getUsername() + "_watched.txt";
        File userWatchedFile = new File(fileName);

        // Sørg for at oprette filen, hvis den ikke allerede findes
        if (!userWatchedFile.exists()) {
            userWatchedFile.createNewFile();
        }

        try (FileWriter writer = new FileWriter(userWatchedFile, true)) { // Appender til filen
            writer.write(selectedMovie + "\n");
            System.out.println("Movie saved to your watched list: " + selectedMovie);
        } catch (IOException e) {
            System.out.println("An error occurred while saving the movie: " + e.getMessage());
        }
    }

    public static void displaySavedMovie() throws IOException {
        // Construct the file path
        String userFilePath = "UserData" + File.separator + currentUser.getUsername() + "_saved.txt";

        // Read movies from the file
        ArrayList<Movie> movies = FileIO.readMovieData(userFilePath);
        if (movies.isEmpty()) {
            System.out.println("No movies saved yet.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        // Display all available movies
        System.out.println("Available movies:");
        for (int i = 0; i < movies.size(); i++) {
            System.out.println((i + 1) + ": " + movies.get(i)); // Using toString() for formatted output
        }

        System.out.print("Select a movie by number: ");
        int movieIndex = scanner.nextInt(); // User input
        int adjustedIndex = movieIndex - 1; // Adjust for 0-based indexing

        if (adjustedIndex < 0 || adjustedIndex >= movies.size()) {
            System.out.println("Invalid movie index. Please choose a number between 1 and " + movies.size());
            return;
        }

        Movie selectedMovie = movies.get(adjustedIndex); // Get selected movie
        Streaming.playMovie(String.valueOf(selectedMovie), currentUser);
        /*// Add the movie to myList
        myList.add(selectedMovie);
        // Save the updated list to the user's file
        FileIO.saveMovieToFile(selectedMovie); // Uses dynamic file path internally
        System.out.println("Added movie: " + selectedMovie.getTitle());
    }

         */
    }
    public static void displayWatchedMovie() throws IOException {
        // Construct the file path
        String userFilePath = "UserData" + File.separator + currentUser.getUsername() + "_watched.txt";

        // Read movies from the file
        ArrayList<Movie> movies = FileIO.readMovieData(userFilePath);
        if (movies.isEmpty()) {
            System.out.println("No movies saved yet.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Available movies:");
        for (int i = 0; i < movies.size(); i++) {
            System.out.println((i + 1) + ": " + movies.get(i)); // Using toString() for formatted output
        }

        // System.out.print("Enter the movie number to add to your list: ");
        int movieIndex = scanner.nextInt(); // User input
        int adjustedIndex = movieIndex - 1; // Adjust for 0-based indexing

        if (adjustedIndex < 0 || adjustedIndex >= movies.size()) {
            System.out.println("Invalid movie index. Please choose a number between 1 and " + movies.size());
            return;
        }

        Movie selectedMovie = movies.get(movieIndex - 1);
        System.out.println("You selected: " + selectedMovie.getTitle() + " (" + selectedMovie.getYear() + ")");

        // Menu for handling af den valgte film
        boolean stayInMenu = true;
        while (stayInMenu) {
            System.out.println("\nWhat would you like to do?");
            System.out.println("1. Play movie");
            System.out.println("2. delete movie from your list");
            System.out.println("3. Return to main menu");
            System.out.print("Enter your choice: ");
            int actionChoice = scanner.nextInt();

            switch (actionChoice) {
                case 1:
                    // Afspil filmen
                    Streaming streaming = new Streaming();
                    User currentUser = Streaming.getCurrentUser(); // Antag, at denne metode findes
                    streaming.playMovie(String.valueOf(selectedMovie), currentUser);
                    break;

                case 2:
                    // slet filmen i brugerens liste
                    Streaming streaming2 = new Streaming();
                    User currentUser2 = Streaming.getCurrentUser();
                    streaming2.movieDeletion();
                    System.out.println(selectedMovie.getTitle() + " has been saved!");
                    break;

                case 3:
                    // Exit til hovedmenu
                    stayInMenu = false;
                    System.out.println("Returning to main menu.");
                    Display.displayMenu(); // Antager, at displayMenu() findes
                    break;

                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        }
    }

    public static void movieDeletion() throws IOException {
        String userFilePath = "UserData" + File.separator + currentUser.getUsername() + "_saved.txt";
        Scanner scanner = new Scanner(System.in);

        while (true) {
            
            ArrayList<Movie> movies = FileIO.readMovieData(userFilePath);

            if (movies == null || movies.isEmpty()) {
                System.out.println("No movies found in the list.");
                return;
            }

            System.out.println("Movies you have added to myList:");
            for (int i = 0; i < movies.size(); i++) {
                System.out.println((i + 1) + ". " + movies.get(i));
            }

            System.out.println("Enter the number of the movie to delete, or type 'exit' to return:");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            try {
                int movieIndex = Integer.parseInt(input) - 1;
                FileIO.deleteMovie("UserData" + File.separator + currentUser.getUsername() + "_saved.txt", movieIndex);

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number or 'exit'.");
            }
        }
    }
}






