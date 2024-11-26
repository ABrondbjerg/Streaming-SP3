import java.util.Scanner;

public class Display {

    public static void displayMenu() {
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
                case "3" -> System.out.println("Showing categories...");
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
        while (true){
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
        while (true){
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
}