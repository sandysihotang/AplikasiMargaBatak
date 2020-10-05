package stud11417031.develops.projecttestwithlaravel.model;

import java.util.List;

public class ResponseUser {
    private boolean error;
    private List<User> users;

    public ResponseUser(boolean error, List<User> users) {
        this.error = error;
        this.users = users;
    }

    public boolean isError() {
        return error;
    }

    public List<User> getUsers() {
        return users;
    }
}
