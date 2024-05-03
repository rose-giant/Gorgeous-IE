package Mizdooni.Model.User;

import java.util.ArrayList;
import java.util.Objects;

public class UserRepository {

    private static UserRepository instance;
    private ArrayList<User> users = new ArrayList<>();

    public UserRepository() throws Exception {
        UserDAO dao = new UserDAO();
        users = dao.getFromAPI();
    }

    public static UserRepository getInstance() throws Exception {
        if(instance == null)
            return new UserRepository();
        else return instance;
    }

    public ArrayList<User> getAll() {
        return users;
    }

    public User findUserByUserName(String userName) {
        for(User user: users) {
            if(Objects.equals(user.username, userName)) {
                return user;
            }
        }
        return null;
    }
}
