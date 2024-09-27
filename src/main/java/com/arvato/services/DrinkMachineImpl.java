package com.arvato.services;

import com.arvato.dtos.DrinkMachineResponse;
import com.arvato.models.Coin;
import com.arvato.models.Product;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DrinkMachineImpl implements DrinkMachine{

    private final HashMap<Product, Integer> products=new HashMap<>();

    @Override
    public DrinkMachineResponse buy(Product product, Coin... coins) {
        return null;
    }

    @Override
    public void fill(Product product, int quantity) {
        products.put(product, products.getOrDefault(product, 0)+quantity);

    }
}
