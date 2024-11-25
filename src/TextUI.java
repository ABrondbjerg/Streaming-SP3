import java.util.ArrayList;
import java.util.Scanner;

public class TextUI {
    private Scanner scan = new Scanner(System.in);


    // simple besked
    public void displayMsg(String msg) {
        System.out.println(msg);
    }

    // numeric input
    public int promptNumeric(String msg) {
        System.out.println(msg);
        String input = scan.nextLine();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            displayMsg("Invalid input. Please enter a number.");
            return promptNumeric(msg);
        }

    }

    // text input
    public String promptText(String msg) {
        System.out.println(msg);
        return scan.nextLine();
    }

    // viser en besked
    public void displayList(ArrayList<String> options, String msg) {
        System.out.println("*******");
        System.out.println(msg);
        System.out.println("*******");
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ": " + options.get(i));
        }
    }

    // valgmuligheder med tal
    public int promptNumericChoice(ArrayList<String> options, String msg) {
        displayList(options, msg);
        int choice = promptNumeric("Choose an option (1-" + options.size() + "):");
        if (choice < 1 || choice > options.size()) {
            displayMsg("Invalid choice. Please try again.");
            return promptNumericChoice(options, msg);
        }
        return choice;
    }

    // multiple choices
    public ArrayList<String> promptMultiChoice(ArrayList<String> options, int limit, String msg) {
        displayList(options, msg);
        ArrayList<String> selectedChoices = new ArrayList<>();
        while (selectedChoices.size() < limit) {
            int choice = promptNumeric("Select option " + (selectedChoices.size() + 1) + ":");
            if (choice < 1 || choice > options.size()) {
                displayMsg("Invalid choice. Please select again.");
            } else {
                selectedChoices.add(options.get(choice - 1));
            }
        }
        return selectedChoices;
    }
}
