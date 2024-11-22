

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileIO {

    public static ArrayList<String> readUserData(String path) {
        ArrayList<String> data = new ArrayList();
        File file = new File(path);
        try {
            Scanner scan = new Scanner(file);
            scan.nextLine();//skip header

            while (scan.hasNextLine()) {
                String line = scan.nextLine(); // "tess, 40000"
                data.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File was not found");
        }
        return data;
    }

    public static void saveData(List<String> movies, String path, String header) {
        try {
            FileWriter writer = new FileWriter(path);
            writer.write(header + "\n"); //Giv csv filen en header
            for (String s : movies) {
                writer.write(s + "\n"); //"Tess, 40000";
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("something went wrong when writing to file");
        }
    }

    public String[] readMovieData(String path) {
        ArrayList<String> movies = new ArrayList<>();
        File file = new File(path);

        try (Scanner scan = new Scanner(file)) {
            if (scan.hasNextLine()) {
                scan.nextLine(); // Skip header if present
            }

            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                movies.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File was not found: " + path);
        }

        return movies.toArray(new String[0]); // Convert ArrayList to array
    }
}
