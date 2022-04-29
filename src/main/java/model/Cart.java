package model;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<Good> goods;

    public Cart() {
        this.goods = new ArrayList();

    }

    public List<Good> getGoods() {
        return goods;
    }

    public void addGood(Good good) {
        goods.add(good);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "goods=" + goods +
                '}';
    }
}

