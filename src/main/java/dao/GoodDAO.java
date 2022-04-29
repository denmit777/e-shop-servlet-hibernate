package dao;

import model.Good;

import java.util.List;

public interface GoodDAO {

    List<Good> getAllGoods();

    void updateGood(Good good);

    void deleteGood(Good good);

    void addGood(Good good);
}
