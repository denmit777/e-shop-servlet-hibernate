package controller;

import model.User;
import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/e-shop")
public class LoginServlet extends HttpServlet {

    private UserService service;
    private User user;

    public void init() throws ServletException {
        service = new UserServiceImpl();
        user = new User();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dispatcherForward(request, response, "/login.jsp");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User newUser = getNewUser(request);
        HttpSession session = request.getSession();
        session.setAttribute("user", newUser);
        session.setAttribute("login", newUser.getLogin());

        if (request.getParameter("submit").equals("Enter")) {
            if (request.getParameter("checkbox") != null) {
                dispatcherForward(request, response, "WEB-INF/good.jsp");
                service.addUser(newUser);
            } else {
                dispatcherForward(request, response, "WEB-INF/error.jsp");
            }
        }
    }

    private User getNewUser(HttpServletRequest request) {
        String login = request.getParameter("login");
        user.setLogin(login);
        List<User> usersList = service.getAllUsers();
        Long userId = Long.valueOf(usersList.size() + 1);
        user.setId(userId);
        return user;
    }

    private void dispatcherForward(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }
}




