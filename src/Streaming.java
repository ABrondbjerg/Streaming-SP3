import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;
import java.util.ArrayList;


public class Streaming {

    public static User loggedInUser;
    public static String movieDataPath = "ressource/film.txt";
    public static FileIO io;
    public static ArrayList<Movie> myList = new ArrayList<>();
    public ArrayList<Movie> watchedList;


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

        File userFile = new File("UserData" +File.separator + username + ".txt");
        if (userFile.exists()) {
            try (Scanner fileScanner = new Scanner(userFile)) {
                String storedUsername = fileScanner.nextLine().replace("Username: ", "").trim();
                String storedPassword = fileScanner.nextLine().replace("Password: ", "").trim();

                if (username.equals(storedUsername) && String.valueOf(password.hashCode()).equals(storedPassword)) {
                    System.out.println("Login successful! Welcome, " + username);
                    loggedInUser = new User(username, password);
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
               writer.write("Password: " + user.getPassword() + "\n");
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



        public static void displayMovies() {
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

    public static void saveMovie() {
        ArrayList<Movie> movies = io.readMovieData("ressource/film.txt"); // Read available movies
        Scanner scanner = new Scanner(System.in);

        // Display all available movies with an index
        System.out.println("Available movies:");
        for (int i = 0; i < movies.size(); i++) {
            System.out.println((i + 1) + ": " + movies.get(i)); // Using toString() for formatted output
        }

        System.out.print("Enter the movie number to add to your list: ");
        int movieIndex = scanner.nextInt(); // User input
        int adjustedIndex = movieIndex - 1; // Adjust for 0-based indexing

        if (adjustedIndex < 0 || adjustedIndex >= movies.size()) {
            System.out.println("Invalid movie index. Please choose a number between 1 and " + movies.size());
            return;
        }

        Movie selectedMovie = movies.get(adjustedIndex); // Get selected movie

        // Load the current user's movie list from the file

        // Add the movie to myList
        myList.add(selectedMovie);

        // Reindex the entire movie list (to ensure proper indexing)
        String userFilePath = "UserMovies" + File.separator + loggedInUser.getUsername() + ".txt";
        io.saveMovieToFile(myList, userFilePath);  // Save the entire updated list to the file
        System.out.println("Added movie: " + selectedMovie.getTitle());
    }


    public static void deleteMovie() {
        // Load the movie list from the user's file
        ArrayList<Movie> movies = FileIO.readMovieData("UserMovies" + File.separator + loggedInUser.getUsername() + ".txt");

        // Check if the data was read successfully
        if (movies != null && !movies.isEmpty()) {
            System.out.println("Saved movie list:");
            for (int i = 0; i < movies.size(); i++) {
                Movie movie = movies.get(i);
                System.out.println(movie); // Call toString() of Movie
            }
        } else {
            System.out.println("No movies found or error reading file.");
            return;
        }

        // Prompt user for input
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the movie number to delete from your list: ");
        int movieIndex = scanner.nextInt(); // User input
        int adjustedIndex = movieIndex - 1; // Convert to 0-based index

        // Check if the index is valid
        if (adjustedIndex < 0 || adjustedIndex >= movies.size()) {
            System.out.println("Invalid movie index. Please choose a number between 1 and " + movies.size());
            return;
        }

        // Remove the selected movie from the list
        Movie selectedMovie = movies.get(adjustedIndex); // Get the selected movie to delete
        movies.remove(adjustedIndex); // Remove movie from the list
        System.out.println("Deleted movie: " + selectedMovie.getTitle());

        // Debugging: Print the updated list after deletion
        System.out.println("Movies after deletion:");
        for (Movie movie : movies) {
            System.out.println(movie); // Print updated list
        }

        // Define the user's file path
        String userFilePath = "UserMovies" + File.separator + loggedInUser.getUsername() + ".txt";

        // Update the user's file by removing the deleted movie
        FileIO.updateUserFile(movies, userFilePath); // Now it calls the method in FileIO
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
//                    writer.write(selectedMovie.getTitle() + "; " + selectedMovie.getYear() + "; " + selectedMovie.getCategory() + "; " + selectedMovie.getRating() + ";\n");
//                    System.out.println("Movie saved to " + userFilePath);
//                } catch (IOException e) {
//                    System.out.println("Error saving movie to file: " + e.getMessage());
//                }
//            } else {
//                System.out.println("Invalid movie index. Please try again.");
//            }
//        }
//        public void searchTitle() {
//
//            boolean found = true;
//            boolean running = true;
//
//            Scanner scanner = new Scanner(System.in);
//            ArrayList<Movie> movies = io.readMovieData(movieDataPath);
//
//            System.out.print("Enter the title of the movie: ");
//            String keyword = scanner.nextLine();
//            for (Movie movie : movies) {
//                // Check if the movie's title contains the keyword (case-insensitive)
//                if (movie.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
//                    System.out.println("Found: " + movie);
//                    found = true;
//                }
//            }
//            if (!found) {
//                System.out.println("No movies found with the title containing: " + keyword);
//            }
//
//        }






