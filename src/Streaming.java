import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

public class Streaming {

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
            System.out.println("Registration successful! You can now log in.");
        } catch (IllegalArgumentException e) {
            // Håndter valideringsfejl
            System.out.println("Registration failed: " + e.getMessage());
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

    }




