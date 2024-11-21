import java.util.LinkedList;



public class User {
    //LinkedList should refer to Class "Movies", so that the users can save the Movies in there list.
    private LinkedList<Movies> movieList;
    private String password;
    private String username;


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        //Returns the stored password (hashed as a String)
        return password;
    }

    public void setPassword(String password) {
        //Password conditions
        if (password.length() < 6 || password.length() > 12) {
            //If password does not meet the requirements, send a message displaying what is wrong.
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }
        //Converting the int to a String, so the method is happy.
        this.password = String.valueOf(hashPassword(password));
    }

    private int hashPassword(String password) {
        //Returning the hash as an int
        return password.hashCode();
    }

    public void setUsername(String username) {
        //Username conditions, can't have a username that is "empty"
        if (username.length() < 6 || username.length() > 16) {
            //If username does not meet the requirements, send a message displaying what is wrong.
            throw new IllegalArgumentException("Username must be at least 6 characters");
        }

        this.username = username;

    }

    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
        this.movieList = new LinkedList<>();
    }


    //Method that allows users to save movies of their liking.
    public void saveMovie(Movies movie) {
        //Adding the movie to the movieList
        movieList.add(movie);
        //Printing out message of which movie was saved.
        System.out.println("Movie saved: " + movie);
    }

}