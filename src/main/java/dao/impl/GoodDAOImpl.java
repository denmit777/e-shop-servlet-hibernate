package dao.impl;

import utils.HibernateUtil;
import dao.GoodDAO;
import model.Good;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;


public class GoodDAOImpl implements GoodDAO {

    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private static final Logger LOGGER = LogManager.getLogger(GoodDAOImpl.class.getName());
    private static final String QUERY_SELECT_FROM_GOOD = "from Good";
    private static final String QUERY_SELECT_FROM_GOOD_BY_ID = "from Good g where g.id = :id";

    public GoodDAOImpl() {
    }

    @Override
    public List<Good> getAllGoods() {
        List<Good> goods = new ArrayList<>();

        try (Session session = sessionFactory.openSession()) {
            goods = session.createQuery(QUERY_SELECT_FROM_GOOD).list();
        } catch (HibernateException e) {
            LOGGER.error(e);
        }
        return goods;
    }

    @Override
    public void addGood(Good good) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.save(good);

            transaction.commit();
        } catch (HibernateException e) {
            LOGGER.error(e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void updateGood(Good good) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            final Good newGood = getGood(good.getId());
            newGood.setName(good.getName());
            newGood.setPrice(good.getPrice());

            session.update(newGood);

            transaction.commit();
        } catch (HibernateException e) {
            LOGGER.error(e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void deleteGood(Good good) {
        try (Session session = sessionFactory.openSession()) {
            session.delete(getGood(good.getId()));
        } catch (HibernateException e) {
            LOGGER.error(e);
        }
    }

    private Good getGood(Long id) {
        try (Session session = sessionFactory.openSession()) {
            final Good good = session.createQuery(QUERY_SELECT_FROM_GOOD_BY_ID, Good.class)
                    .setParameter("id", id).uniqueResult();
            return good;
        } catch (HibernateException e) {
            LOGGER.error(e);
        }
        return new Good();
    }
}
