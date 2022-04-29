package dao;

import model.User;

import java.util.List;

public interface UserDAO {

    List<User> getAllUsers();

    User getUser(String login);

    void addUser(User user);

    void updateUser(User user);

    void deleteUser(User user);
}
