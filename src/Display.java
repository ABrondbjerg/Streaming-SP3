import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Display {

    public static void displayMenu() throws FileNotFoundException {
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. My List");
            System.out.println("2. Watched Movies");
            System.out.println("3. Show Categories");
            System.out.println("4. Top 5 Movies");
            System.out.println("5. Exit");

            String choice = scan.nextLine();

            switch (choice) {
                case "1" -> displayMyListMenu();
                case "2" -> displayWatchedMoviesMenu();
                case "3" -> displayCategories();
                case "4" -> displayTop5Movies();
                case "5" -> {
                    System.out.println("Exiting the program. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option. Please enter a valid option.");
            }
        }

    }

    private static void displayMyListMenu() {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- My List Menu ---");
            System.out.println("1. View Saved Movies");
            System.out.println("2. Remove a Movie");
            System.out.println("3. Back to Main Menu");

            String choice = scan.nextLine();
            switch (choice) {
                case "1" -> System.out.println("Feature to view saved movies coming soon!");
                case "2" -> System.out.println("Feature to Remove a movie coming soon!");
                case "3" -> {
                    System.out.println("Returning to Main Menu");
                    return;
                }
                default -> System.out.println("Invalid option. Please enter a valid option.");
            }
        }
    }

    private static void displayWatchedMoviesMenu() {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Watched Movie Menu ---");
            System.out.println("1. Watch Movies");
            System.out.println("2. Remove a Movie");
            System.out.println("3. Back to Main Menu");

            String choice = scan.nextLine();
            switch (choice) {
                case "1" -> System.out.println("Feature to watch movies");
                case "2" -> System.out.println("Feature to Remove a movie");
                case "3" -> {
                    System.out.println("Returning to Main Menu");
                    return;
                }
                default -> System.out.println("Invalid option. Please enter a valid option.");
            }
        }
    }

    private static void displayCategories() throws FileNotFoundException {
        TextUI ui = new TextUI();
        List<Movie> movies = Movie.createMovies("ressource" + File.separator + "film.txt");

        // Find unikke kategorier
        List<String> categories = Movie.getAllUniqueCategories(movies);

        ui.displayMsg("Available categories:");
        for (String category : categories) {
            System.out.println("- " + category);
        }

        // Brugeren vælger en kategori
        System.out.print("Choose a category: ");
        Scanner scan = new Scanner(System.in);
        String choice = scan.nextLine().trim();

        // Filtrer og vis filmene i den valgte kategori
        List<Movie> filteredMovies = Movie.getMoviesByCategory(movies, choice);

        if (filteredMovies.isEmpty()) {
            System.out.println("No movies found in the \"" + choice + "\" category.");
        } else {
            System.out.println("Movies in the \"" + choice + "\" category:");
            for (int i = 0; i < filteredMovies.size(); i++) {
                Movie filteredMovie = filteredMovies.get(i);
                System.out.println((i + 1) + ". " + filteredMovie.getTitle() + " (" + filteredMovie.getYear() + ")");
            }
        }
        Scanner scanner = new Scanner(System.in);

        // Lad brugeren vælge en film
        System.out.println("Enter the number of the movie you want to choose:");
        int movieChoice = scanner.nextInt();

        // Tjek for gyldig input
        if (movieChoice < 1 || movieChoice > filteredMovies.size()) {
            System.out.println("Invalid choice. Returning to main menu.");
        } else {
            // Vælg filmen
            Movie selectedMovie = filteredMovies.get(movieChoice - 1);
            System.out.println(selectedMovie.getTitle() + " (" + selectedMovie.getYear() + ")");
            boolean stayInMenu = true;

            // Filmvalg-menu
            while (stayInMenu) {
                System.out.println("Choose an option:");
                System.out.println("1: Watch movie");
                System.out.println("2: Save movie");
                System.out.println("3: Exit to main menu");
                FileIO io = new FileIO();
                int actionChoice = scanner.nextInt();

                switch (actionChoice) {
                    case 1:
                        // Se filmen (forudsat at metoden allerede findes)
                        //selectedMovie.watch(); // Metoden skal eksistere i Movie-klassen
                        break;
                    case 2:
                        // Gem filmen (forudsat at metoden allerede findes)
                        io.saveMovieToFile(selectedMovie);
                        System.out.println(selectedMovie.getTitle() + " has been saved!");
                        break;
                    case 3:
                        // Exit til hovedmenu
                        stayInMenu = false;
                        System.out.println("Returning to main menu.");
                        displayMenu();
                        break;
                    default:
                        System.out.println("Invalid option. Please choose again.");
                }
            }
        }
    }
    private static void displayTop5Movies() throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        List<Movie> movies = Movie.createMovies("ressource" + File.separator + "film.txt");
        // Udskriv top 5 film
        System.out.println("\n--- Top 5 Movies ---");
        for (int i = 0; i < 5; i++) {
            Movie movie = movies.get(i); // Hent filmen baseret på dens indeks
            System.out.println((i + 1) + ". " + movie.getTitle() + " (" + movie.getYear() + ")");
        }

        System.out.println("Enter the number of the movie you want to choose:");
        int movieChoice = scanner.nextInt();

        // Tjek for gyldig input
        if (movieChoice < 1 || movieChoice > movies.size()) {
            System.out.println("Invalid choice. Returning to main menu.");
        } else {
            Movie selectedMovie = movies.get(movieChoice - 1);
            System.out.println(selectedMovie.getTitle() + " (" + selectedMovie.getYear() + ")");
        }
        boolean stayInMenu = true;
        while (stayInMenu) {
            System.out.println("Choose an option:");
            System.out.println("1: Watch movie");
            System.out.println("2: Save movie");
            System.out.println("3: Exit to main menu");
            FileIO io = new FileIO();
            Scanner scan = new Scanner(System.in);
            int actionChoice = scan.nextInt();
            Movie selectedMovie = movies.getLast();

            switch (actionChoice) {
                case 1:
                    // Se filmen (forudsat at metoden allerede findes)
                    //selectedMovie.watch(); // Metoden skal eksistere i Movie-klassen
                    break;
                case 2:
                    // Gem filmen (forudsat at metoden allerede findes)
                    io.saveMovieToFile(selectedMovie);
                    System.out.println(selectedMovie.getTitle() + " has been saved!");
                    break;
                case 3:
                    // Exit til hovedmenu
                    stayInMenu = false;
                    System.out.println("Returning to main menu.");
                    displayMenu();
                    break;
                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        }
    }
}