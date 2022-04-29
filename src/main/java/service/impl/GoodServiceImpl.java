package service.impl;

import dao.GoodDAO;
import dao.impl.GoodDAOImpl;
import model.Good;
import service.GoodService;

import java.util.List;

public class GoodServiceImpl implements GoodService {

    private GoodDAO goodDAO = new GoodDAOImpl();

    @Override
    public List<Good> getListGoods() {
        return goodDAO.getAllGoods();
    }

    @Override
    public void addGood(Good good) {
        goodDAO.addGood(good);
    }
}
