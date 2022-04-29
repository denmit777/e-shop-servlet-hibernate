package controller;

import model.User;
import model.Cart;
import model.Good;
import model.Order;

import service.CartService;
import service.OrderService;
import service.UserService;
import service.impl.CartServiceImpl;
import service.impl.OrderServiceImpl;
import service.impl.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/good")
public class GoodServlet extends HttpServlet {

    private static final String REGEX_ONLY_LETTERS = "[^A-Za-z]";
    private static final String REGEX_ONLY_FIGURES = "[A-Za-z]";
    private static final String REGEX_LETTERS_FIGURES_POINT = "[^A-Za-z0-9.]";

    private CartService cartService;
    private OrderService orderService;
    private UserService userService;
    private Order order;
    private User user;
    private Cart cart;

    public void init() throws ServletException {
        cartService = new CartServiceImpl();
        orderService = new OrderServiceImpl();
        userService = new UserServiceImpl();
        order = new Order();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dispatcherForward(request, response, "WEB-INF/good.jsp");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        createCart(session);

        BigDecimal totalPrice = cartService.getTotalPrice(cart);
        Order newOrder = getNewOrder(session, totalPrice);

        session.setAttribute("cart", cart);
        session.setAttribute("totalPrice", totalPrice);

        String command = request.getParameter("submit");
        if ("Add Good".equals(command)) {
            String option = getStringOfNameAndPriceFromOptionMenu(request.getParameter("goodName"));
            addGoodToCart(option);

            String chosenGoods = cartService.print(cart);
            session.setAttribute("chosenGoods", chosenGoods);

            dispatcherForward(request, response, "WEB-INF/good.jsp");
        } else {
            dispatcherForward(request, response, "WEB-INF/order.jsp");
            session.setAttribute("totalPrice", totalPrice);
            orderService.addOrder(newOrder);
        }
    }

    private void createCart(HttpSession session) {
        if (session.getAttribute("cart") == null) {
            cart = new Cart();
        } else {
            cart = (Cart) session.getAttribute("cart");
        }
    }

    private String getStringOfNameAndPriceFromOptionMenu(String s) {
        return s.replaceAll(REGEX_LETTERS_FIGURES_POINT, "");
    }

    private void addGoodToCart(String option) {
        String name = option.replaceAll(REGEX_ONLY_LETTERS, "");
        String price = option.replaceAll(REGEX_ONLY_FIGURES, "");
        cart.addGood(new Good(name, BigDecimal.valueOf(Double.valueOf(price))));
    }

    private void dispatcherForward(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }

    private Order getNewOrder(HttpSession session, BigDecimal totalPrice) {
        String login = (String) session.getAttribute("login");
        user = userService.getUser(login);
        List<Order> orderList = orderService.getListOrders();
        Long orderId = (long) (orderList.size() + 1);

        order.setId(orderId);
        order.setUserId(user.getId());
        order.setTotalPrice(totalPrice);

        return order;
    }
}
