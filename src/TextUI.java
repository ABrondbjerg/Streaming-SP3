import java.util.ArrayList;
import java.util.Scanner;

    public class TextUI {
        private Scanner scan = new Scanner(System.in);

        public void displayMsg(String msg){
            System.out.println(msg);
        }


        public int promptNumeric(String msg) {
            System.out.println(msg);              // Stille brugeren et spørgsmål
            String input = scan.nextLine();
            int number;
            // Give brugere et sted at placere sit svar og vente på svaret
            try {
                number = Integer.parseInt(input);
            }
            catch(NumberFormatException e){
                displayMsg("Please type a number");
                number = promptNumeric(msg);
            }
            return number;
        }

        public String promptText(String msg){
            System.out.println(msg);//Stille brugeren et spørgsmål
            String input = scan.nextLine();
            return input;
        }

        public int promptNumericChoice(ArrayList<String> options, String msg){
            displayList(options, msg);
            int choice = promptNumeric("Choose an option (1-" + options.size() + "):");
            if (choice < 1 || choice > options.size()) {
                displayMsg("Invalid choice. Please try again.");
                return promptNumericChoice(options, msg);
            }
            return choice;
        }

        public void displayList(ArrayList<String> options, String msg){
            System.out.println("*******");
            System.out.println(msg);
            System.out.println("*******");

            int i = 1;

            for (String option : options) {
                System.out.println(i+": "+option);
                i++;
            }
        }
    }
