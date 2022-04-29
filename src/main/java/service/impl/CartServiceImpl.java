package service.impl;

import model.Cart;
import model.Good;
import service.CartService;

import java.math.BigDecimal;

public class CartServiceImpl implements CartService {

    @Override
    public String print(Cart cart) {
        StringBuilder sb = new StringBuilder();

        int count = 1;

        for (Good good : cart.getGoods()) {
            sb.append(count + ") " + good.getName() + " " + good.getPrice() + " $\n");
            count++;
        }
        return sb.toString();
    }

    @Override
    public BigDecimal getTotalPrice(Cart cart) {
        BigDecimal count = BigDecimal.valueOf(0);

        for (Good good : cart.getGoods()) {
            count = count.add(good.getPrice());
        }
        return count;
    }
}
