package service.impl;

import dao.OrderDAO;
import dao.impl.OrderDAOImpl;
import model.Order;
import service.OrderService;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    private OrderDAO orderDAO = new OrderDAOImpl();

    @Override
    public void addOrder(Order order) {
        orderDAO.addOrder(order);
    }

    @Override
    public List<Order> getListOrders() {
        return orderDAO.getAllOrders();
    }

    @Override
    public Order getOrder(Long userId) {
        return orderDAO.getOrder(userId);
    }
}
