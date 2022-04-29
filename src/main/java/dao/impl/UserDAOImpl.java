package dao.impl;

import utils.HibernateUtil;
import dao.UserDAO;
import model.User;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UserDAOImpl implements UserDAO {

    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private static final Logger LOGGER = LogManager.getLogger(UserDAOImpl.class.getName());
    private static final String QUERY_SELECT_FROM_USER = "from User";
    private static final String QUERY_SELECT_FROM_USER_BY_LOGIN = "from User u where u.login = :login";
    private static final String QUERY_SELECT_FROM_USER_BY_ID = "from User u where u.id = :id";

    public UserDAOImpl() {
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Session session = sessionFactory.openSession()) {
            users = session.createQuery(QUERY_SELECT_FROM_USER).list();
        } catch (HibernateException e) {
            LOGGER.error(e);
        }
        return users;
    }

    @Override
    public User getUser(String login) {
        try (Session session = sessionFactory.openSession()) {
            final User user = session.createQuery(QUERY_SELECT_FROM_USER_BY_LOGIN, User.class)
                    .setParameter("login", login).uniqueResult();
            LOGGER.info("User by login " + user.getLogin() + ": " + user);
            return user;
        } catch (HibernateException e) {
            LOGGER.error(e);
        }
        return new User();
    }

    @Override
    public void addUser(User user) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.save(user);

            transaction.commit();
        } catch (HibernateException e) {
            LOGGER.error(e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void updateUser(User user) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            User newUser = getUser(user.getLogin());
            newUser.setLogin(user.getLogin());

            session.update(newUser);

            transaction.commit();
        } catch (HibernateException e) {
            LOGGER.error(e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void deleteUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.delete(getUser(user.getId()));
        } catch (HibernateException e) {
            LOGGER.error(e);
        }
    }

    private User getUser(Long id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.createQuery(QUERY_SELECT_FROM_USER_BY_ID, User.class)
                    .setParameter("id", id).uniqueResult();
            return user;
        } catch (HibernateException e) {
            LOGGER.error(e);
        }
        return new User();
    }

}
