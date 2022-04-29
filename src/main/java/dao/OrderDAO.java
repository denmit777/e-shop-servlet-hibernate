package dao;

import model.Order;

import java.util.List;

public interface OrderDAO {

    List<Order> getAllOrders();

    void updateOrder(Order order);

    void deleteOrder(Order order);

    void addOrder(Order order);

    Order getOrder(Long userId);
}
