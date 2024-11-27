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
            System.out.println("\nPlease choose an option, by pressing the correlating number: ");
            String choice = scan.nextLine();


            switch (choice) {
                case "1" -> displayMyListMenu();
                case "2" -> displayWatchedMoviesMenu();
                case "3" -> displayCategories();
                case "4" -> System.out.println("Showing top 5 movies...");
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

            System.out.println("\nPlease choose an option, by pressing the correlating number: ");

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

            System.out.println("\nPlease choose an option, by pressing the correlating number: ");

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
        System.out.print("Choose a category by typing it in: ");
        Scanner scan = new Scanner(System.in);
        String choice = scan.nextLine().trim();

        // Filtrer og vis filmene i den valgte kategori
        List<Movie> filteredMovies = Movie.getMoviesByCategory(movies, choice);

        if (filteredMovies.isEmpty()) {
            System.out.println("No movies found in the \"" + choice + "\" category.");
        } else {
            System.out.println("Movies in the \"" + choice + "\" category:");
            for (Movie filteredMovie : filteredMovies) {
                    System.out.println("- " + filteredMovie.getTitle() + " (" + filteredMovie.getYear() + ")");
                }
            }
        }
    }
