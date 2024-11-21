import java.util.LinkedList;



public class User {

    private LinkedList<Movies> movieList;
    private String password;
    private String username;


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        //Password conditions
        if (password.length() < 6) {
            //If password
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }
        //Converting the int to a String, so the method is happy.
        this.password = String.valueOf(hashPassword(password));
    }

    private int hashPassword(String password) {
        return password.hashCode();
    }

    public void setUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null");
        }

        this.username = username;

    }

    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
        this.movieList = new LinkedList<>();
    }

}