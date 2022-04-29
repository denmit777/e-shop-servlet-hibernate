package dao.impl;

import utils.HibernateUtil;
import dao.OrderDAO;
import model.Order;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private static final Logger LOGGER = LogManager.getLogger(OrderDAOImpl.class.getName());
    private static final String QUERY_SELECT_FROM_ORDER = "from Order";
    private static final String QUERY_SELECT_FROM_ORDER_BY_USER_ID = "from Order o where o.userId = :userId";

    public OrderDAOImpl() {
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();

        try (Session session = sessionFactory.openSession()) {
            orders = session.createQuery(QUERY_SELECT_FROM_ORDER).list();
        } catch (HibernateException e) {
            LOGGER.error(e);
        }
        return orders;
    }

    @Override
    public void addOrder(Order order) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(order);
            transaction.commit();
        } catch (HibernateException e) {
            LOGGER.error(e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void updateOrder(Order order) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            Order newOrder = getOrder(order.getId());
            newOrder.setUserId(order.getUserId());
            newOrder.setTotalPrice(order.getTotalPrice());

            session.update(newOrder);

            transaction.commit();
        } catch (HibernateException e) {
            LOGGER.error(e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void deleteOrder(Order order) {
        try (Session session = sessionFactory.openSession()) {
            session.delete(getOrder(order.getId()));
        } catch (HibernateException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public Order getOrder(Long userId) {
        try (Session session = sessionFactory.openSession()) {
            Order order = session.createQuery(QUERY_SELECT_FROM_ORDER_BY_USER_ID, Order.class)
                    .setParameter("userId", userId).uniqueResult();
            return order;
        } catch (HibernateException e) {
            LOGGER.error(e);
        }
        return new Order();
    }
}
