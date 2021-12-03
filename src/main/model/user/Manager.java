package src.main.model.user;

public class Manager implements User {
    private String name;

    public Manager(String name) {
        this.name = name;
    }

    public UserType  getUserType() {
        return UserType.MANAGER;
    }
}
