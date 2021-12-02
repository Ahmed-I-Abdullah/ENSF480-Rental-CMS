package src.main.controller;

import src.main.model.user.User;

public class UserController {
    private User user;

    public UserController(User u) {
        this.user = u;
    }

    public void setUser(User u) {
        this.user = u;
    }

    public User getUser() {
        return user;
    }
}
