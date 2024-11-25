import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;



public class User {
    //LinkedList should refer to Class "Movies", so that the users can save the Movies in there list.
    private LinkedList<Movie> movieList;
    private String password;
    private String username;


    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
        //Initializing the movie list for this user
        this.movieList = new LinkedList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        //Username conditions, can't have a username that is null or empty
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        //Username conditions, can't have a username that is "under 6 characters and over 16 characters"
        if (username.length() < 6 || username.length() > 16) {
            //If username does not meet the requirements, send a message displaying what is wrong.
            throw new IllegalArgumentException("Username must be at least 6 characters and under 16 characters");
        }

        this.username = username;

    }

    public void createUserFile() throws IOException {
        String fileName = username + ".txt";
        File userFile = new File(fileName);
        Writer writer = new FileWriter(userFile);

        if (!userFile.exists() && username == null || username.trim().isEmpty()) {
            writer.write("Username: " + getUsername());
            writer.write("\n");
            writer.write("Password: " + setPassword());;
            writer.close();
        } else {
            System.out.println("This user already exists: " + username);
        }
    }

    public String getPassword() {
        //Returns the stored password (hashed as a String)
        return password;
    }


    public void setPassword(String password) {
        //Password conditions, can't have a password that is null or empty
        //The password.trim() makes it so that there is no white space in the String like, space, tab, new line...."
        //And then the isEmpty() method checks for any characters in the String
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        //Password conditions, can't have a password that is under 6 characters and over 12 characters.
        if (password.length() < 6 || password.length() > 12) {
            //If password does not meet the requirements, send a message displaying what is wrong.
            throw new IllegalArgumentException("Password must be at least 6 characters and maximum 12 characters");
        }
        //Converting the int to a String, so the method is happy.
        this.password = String.valueOf(hashPassword(password));
    }

    private int hashPassword(String password) {
        //Returning the hash as an int
        return password.hashCode();
    }


    //Method that allows users to save movies of their liking.
    public boolean saveMovie(Movie movie) {
        if (movie == null) {
            throw new IllegalArgumentException("Movie cannot be null");
        }
        //Adding the movie to the movieList
        movieList.add(movie);
        System.out.println("Movie added successfully: " + movie);
        return true;
    }



    public boolean deleteMovie(Movie movie){
        if (movie == null) {
            //Movie cannot be null, so we communicate this to  the user
            throw new IllegalArgumentException("Movie cannot be null");
        }
        if(movieList.isEmpty()){
            //If they do not have any movies saved, we say we cant find any to delete.
            System.out.println("There are no movies in your saved list to delete");
            return false;
        }
        if (movieList.remove(movie)){
            //If the movie is succesfully deleted, we give the user a message saying what movie was deleted.
            System.out.println("Movie deleted: " + movie);
            return true;
        }else{
            System.out.println("Movie not found in your saved list " + movie);
            return false;
        }
    }

    //I DON'T KNOW IF THIS IS SUPPOSED TO BE HERE (BUT IM DOING IT ANYWAY!)
      public void displaySavedMovies(){
        if (movieList.isEmpty()) {
            System.out.println("No saved movies");
        }else{
            System.out.println("Saved Movies");
            //Looping through the Movie objects in the movieList
            //Movies =
            for (Movie movie : movieList){
                System.out.println(movie);
            }
        }
      }

      public String toString(){
        return "Username: " + username + " \nPassword (hashed): " + password + " \nMovies Saved: " + movieList.size();
      }
}