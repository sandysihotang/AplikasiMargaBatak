package stud11417031.develops.projecttestwithlaravel.model;

public class User {
    private int id;
    private String username,firstname,lastname,image;

    public User(int id, String username, String firstname, String lastname, String image) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getImage() {
        return image;
    }
}
