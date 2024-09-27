package com.arvato.dtos;

import com.arvato.models.Coin;
import com.arvato.models.Product;

import java.util.List;

public class DrinkMachineResponse {

    private final Product product;

    private final List<Coin> coins;

    private final String message;


    public DrinkMachineResponse(Product product, List<Coin> coins, String message) {
        this.product = product;
        this.coins = coins;
        this.message = message;
    }

    public Product getProduct() {
        return product;
    }

    public List<Coin> getCoins() {
        return coins;
    }

    public String getMessage() {
        return message;
    }
}
