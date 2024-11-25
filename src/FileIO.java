

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

    public static ArrayList<Movie> readMovieData(String filePath) {
        ArrayList<Movie> movies = new ArrayList<>();
        File file = new File(filePath);

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(";"); // Assuming data is semicolon-separated
                if (parts.length == 4) {
                    String title = parts[0].trim();
                    int year = Integer.parseInt(parts[1].trim()); // Parse year to int
                    String category = parts[2].trim();
                    float rating = Float.parseFloat(parts[3].trim()); // Parse rating to float

                    movies.add(new Movie(title, year, category, rating));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        } catch (NumberFormatException e) {
            System.out.println("Invalid format for year or rating. Please check the file.");
        }

        return movies;
    }
}
