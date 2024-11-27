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
            for (Movie filteredMovie : filteredMovies) {
                    System.out.println("- " + filteredMovie.getTitle() + " (" + filteredMovie.getYear() + ")");
                }
            }
        }
    }


/*
        switch (choice) {
            case "crime":
                System.out.println("You chose Crime!");
                break;
            case "drama":
                System.out.println("You chose Drama!");
                break;
            case "biography":
                System.out.println("You chose Biography!");
                break;
            case "sport":
                System.out.println("You chose Sport!");
                break;
            case "history":
                System.out.println("You chose History!");
                break;
            case "romance":
                System.out.println("You chose Romance!");
                break;
            case "war":
                System.out.println("You chose War!");
                break;
            case "mystery":
                System.out.println("You chose Mystery!");
                break;
            case "adventure":
                System.out.println("You chose Adventure!");
                break;
            case "family":
                System.out.println("You chose Family!");
                break;
            case "fantasy":
                System.out.println("You chose Fantasy!");
                break;
            case "thriller":
                System.out.println("You chose Thriller!");
                break;
            case "horror":
                System.out.println("You chose Horror!");
                break;
            case "film-noir":
                System.out.println("You chose Film-Noir!");
                break;
            case "action":
                System.out.println("You chose Action!");
                break;
            case "sci-fi":
                System.out.println("You chose Sci-fi!");
                break;
            case "comedy":
                System.out.println("You chose Comedy!");
                break;
            case "musical":
                System.out.println("You chose Musical!");
                break;
            case "western":
                System.out.println("You chose Western!");
                break;
            case "music":
                System.out.println("You chose Music!");
                break;
            default:
                System.out.println("Invalid genre! Please choose a valid movie genre.");
                break;
        }
*/
