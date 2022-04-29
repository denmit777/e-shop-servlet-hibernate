package service.impl;

import dao.UserDAO;
import dao.impl.UserDAOImpl;
import model.User;
import service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDAO userDAO = new UserDAOImpl();

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public void addUser(User user) {
        userDAO.addUser(user);
    }

    @Override
    public User getUser(String login) {
        return userDAO.getUser(login);
    }

}
