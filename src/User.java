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
        //Username conditions, can't have a username that is "empty"
        if (username.length() < 6 || username.length() > 16) {
            //If username does not meet the requirements, send a message displaying what is wrong.
            throw new IllegalArgumentException("Username must be at least 6 characters");
        }

        this.username = username;

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


    //Method that allows users to save movies of their liking.
    public void saveMovie(Movie movie) {
        if (movie == null) {
            throw new IllegalArgumentException("Movie cannot be null");
        }
        //Adding the movie to the movieList
        movieList.add(movie);
        //Printing out message of which movie was saved.
        System.out.println("Movie saved: " + movie);
    }

    public boolean deleteMovie(Movie movie){
        if (movie == null) {
            //Movie cannot be null, so we communicate this to  the user
            throw new IllegalArgumentException("Movie cannot be null");
        }
        if(movieList.isEmpty()){
            //If they do not have any movies saved, we say we cant find any to delete.
            System.out.println("No movies to delete");
            return false;
        }
        if (movieList.remove(movie)){
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