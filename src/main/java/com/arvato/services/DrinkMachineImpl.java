package com.arvato.services;

import com.arvato.dtos.DrinkMachineResponse;
import com.arvato.models.Coin;
import com.arvato.models.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DrinkMachineImpl implements DrinkMachine{

    private final HashMap<Product, Integer> products=new HashMap<>();

    @Override
    public DrinkMachineResponse buy(Product product, Coin... coins) {
        int totalInserted=0;
        for (Coin coin: coins ){
            totalInserted+=coin.getValue();
        }
        if (totalInserted< product.getPrice()) return new DrinkMachineResponse(null,  Arrays.asList(coins), "Not Enough Money");
        products.put(product,products.get(product)-1);
        int difference=totalInserted- product.getPrice();
        List<Coin> returnChange= calculateChange(difference);
        return new DrinkMachineResponse(product, returnChange, "Successful Purchase");

    }

    private List<Coin> calculateChange(int difference) {
        List<Coin> list=new ArrayList<>();
        list.add(Coin.COIN_50);
        list.add(Coin.COIN_20);
        list.add(Coin.COIN_10);
        return list;
    }

    @Override
    public void fill(Product product, int quantity) {
        products.put(product, products.getOrDefault(product, 0)+quantity);

    }
}
