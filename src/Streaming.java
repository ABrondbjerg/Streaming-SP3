import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Streaming {

    public static void startStream() {
        TextUI textUI = new TextUI();
        textUI.displayMsg("Welcome to MouseGun Streams");
    }

    public static void loginOrAccount(String msg) {
        Scanner scan = new Scanner(System.in);

        switch (msg.toLowerCase()) {
            case "login":
                userLogin(scan);
                break;

            case "register":
                userRegistration(scan);
                break;

            default:
                System.out.println("Invalid option. Please enter 'login' or 'register'.");
                break;
        }
    }
    private static void userLogin(Scanner scan) {
            System.out.print("Enter username: ");
            String username = scan.nextLine();
            System.out.print("Enter password: ");
            String password = scan.nextLine();
            //Brug en brugerfil til at validere login
            File userFile = new File(username + ".txt");
            if (!userFile.exists()) {
                System.out.println("User not found. Please register first.");
                return;
            }
            // Tjekker brugernavn og kodeord
            try (Scanner fileScanner = new Scanner(userFile)) {
                String storedUsername = fileScanner.nextLine().replace("Username: ", "").trim();
                String storedPassword = fileScanner.nextLine().replace("Password: ", "").trim();

                if (username.equals(storedUsername) && String.valueOf(password.hashCode()).equals(storedPassword)) {
                    System.out.println("Login successful! Welcome, " + username);
                } else {
                    System.out.println("Invalid username or password.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred while reading the user file.");
            }
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
            System.out.println("Registration successful! You can now log in.");
        } catch (IllegalArgumentException e) {
            // Håndter valideringsfejl
            System.out.println("Registration failed: " + e.getMessage());
        }
    }

    private static void saveUserToFile(User newUser) {
    }

}



