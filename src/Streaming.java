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
    public static String movieDataPath = "C:\\Users\\Ayman\\OneDrive\\Dokumenter\\SEMESTER1DATAMATIKER\\Streaming-SP3\\Streamings-SP3\\ressource\\film.txt";
    public static FileIO io = new FileIO();
    public static String myList = "ressource/saved";
    public static String watched = "ressource/watched";


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


    public static void displaySaved() {
        // Read movie data from the file
        ArrayList<Movie> movies = FileIO.readMovieData("ressource/movie_list.txt");

        if (movies == null || movies.isEmpty()) {
            System.out.println("No movies found or error reading file.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        boolean continueLoop = true;

        while (continueLoop) {
            displayMovies(movies);
            System.out.println("What would you like to do?");
            System.out.println("1. Delete a movie");
            System.out.println("2. Play a movie");
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1-3): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    MovieDeletion("ressource/movie_list.txt", scanner);
                    break;
                case 2:
                    MoviePlay(movies, scanner);

                    break;
                case 3:
                    continueLoop = false;
                    System.out.println("Exit program.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMovies(ArrayList<Movie> movies) {
        System.out.println("Movies you have added to myList:" + "\n");
        for (int i = 0; i < movies.size(); i++) {
            System.out.println((i + 1) + ". " + movies.get(i));
        }
    }

    public static void MovieDeletion(String filePath, Scanner scanner) {
        while (true) {
            ArrayList<Movie> movies = FileIO.readMovieData(filePath);

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
                io.deleteMovie(filePath, movieIndex);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number or 'exit'.");
            }
        }
    }

    private static void MoviePlay(ArrayList<Movie> movies, Scanner scanner) {
        displayMovies(movies);
        System.out.print("Enter the movie number to watch: ");
        int movieIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // Consume newline

        if (movieIndex >= 0 && movieIndex < movies.size()) {
            Movie selectedMovie = movies.get(movieIndex);
            System.out.println("You are watching: " + selectedMovie.getTitle());
            System.out.println("You have watched: " + selectedMovie.getTitle());

            saveMovieToFile(selectedMovie, "ressource/watched");
           io.deleteMovie("ressource/movie_list.txt", movieIndex);
           io.updateMovieList("ressource/movie_list.txt");
            System.out.print("the movie has been added to watched List and deleted from myList ");

        } else {
            System.out.println("Invalid movie number. Please try again.");
        }
    }



    private static void saveMovieToFile(Movie movie, String filePath) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(movie.getTitle() + "; " + movie.getYear() + "; " +
                    String.join(", ", movie.getCategories()) + "; " + movie.getRating() + ";\n");
            System.out.println("Movie saved to " + filePath);
        } catch (IOException e) {
            System.out.println("Error saving movie: " + e.getMessage());
        }
    }


                    public void displayWatched () {

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




            public static void saveMovie () {
                // Sample movies list from file reading
                ArrayList<Movie> movies = io.readMovieData(movieDataPath); // Read movies from file
                Scanner scanner = new Scanner(System.in);
                ArrayList<Movie> myList = new ArrayList<>();

                boolean addMovie = true; // Control variable for the loop

                while (addMovie) {
                    // Ask for the movie index
                    System.out.print("Enter the movie number to add to my List: ");
                    int movieIndex = scanner.nextInt();
                    scanner.nextLine(); // Consume the leftover newline character
                    int adjustedIndex = movieIndex - 1; // Adjust index for 1-based input

                    if (adjustedIndex >= 0 && adjustedIndex < movies.size()) {
                        Movie selectedMovie = movies.get(adjustedIndex);
                        myList.add(selectedMovie); // Add selected movie to myList
                        System.out.println("Added movie: " + selectedMovie.getTitle());

                        // Save the selected movie to a file
                        String userFilePath = "ressource/movie_list.txt"; // File to save movie list
                        try (FileWriter writer = new FileWriter(userFilePath, true)) { // true for appending
                            writer.write(selectedMovie.getTitle() + "; " + selectedMovie.getYear() + "; " +
                                    String.join(", ", selectedMovie.getCategories()) + "; " + selectedMovie.getRating() + ";\n");
                        } catch (IOException e) {
                            System.out.println("Error saving movie to file: " + e.getMessage());
                        }

                        // Ask if the user wants to add another movie
                        System.out.println("Do you want to add another movie to my List? (YES/NO)");
                        String response = scanner.nextLine();

                        if (response.equalsIgnoreCase("NO")) {
                            addMovie = false; // Exit the loop
                            System.out.println("Exiting");
                        }
                    } else {
                        System.out.println("Invalid movie index. Please try again.");
                    }
                }
            }





                public static void playMovie() {
                    // Get user input for movie index
                    Scanner scanner = new Scanner(System.in);

                    System.out.print("Enter the movie number to add to your list: ");
                    int movieIndex = scanner.nextInt(); // Get the movie index from the user

                    // Adjust index since the list is 0-based but the user is selecting 1-based
                    int adjustedIndex = movieIndex - 1; // User selects starting from 1

                    // Read movie data from the file and read watched
                    ArrayList<Movie> movies = io.readMovieData(movieDataPath);
                    ArrayList<Movie> watchedList = new ArrayList<>();
                    if (adjustedIndex >= 0 && adjustedIndex < movies.size()) {
                        Movie selectedMovie = movies.get(adjustedIndex);
                        watchedList.add(selectedMovie);  // Add to the selected list (myList)
                        System.out.println("You have watched:" + selectedMovie.getTitle());
                        System.out.println("Added movie: " + selectedMovie.getTitle());

                        // Save the selected movie to the user's file immediately after adding it
                        String userFilePath = "ressource/watched";
                        try (FileWriter writer = new FileWriter(userFilePath, true)) {  // 'true' to append to the file
                            writer.write(selectedMovie.getTitle() + "; " + selectedMovie.getYear() + "; " + selectedMovie.getCategories() + "; " + selectedMovie.getRating() + ";\n");
                            System.out.println("Movie saved to " + userFilePath);
                        } catch (IOException e) {
                            System.out.println("Error saving movie to file: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Invalid movie index. Please try again.");
                    }
                }


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
            // Ask if the user wants to save a movie
            System.out.print("Do you want to save any of these movies? (yes/no): ");
            String response = scanner.nextLine();

            if (response.equalsIgnoreCase("yes")) {
                System.out.print("Enter the number of the movie you want to save: ");
                int movieNumber = scanner.nextInt();

                if (movieNumber > 0 && movieNumber <= movies.size()) {
                    Movie selectedMovie = movies.get(movieNumber - 1);
                    FileIO.saveMovieToFile(selectedMovie, myList);
                    System.out.println("Movie saved: " + selectedMovie.getTitle());
                } else {
                    System.out.println("Invalid movie number.");
                }
            }
        }
    }
    }



