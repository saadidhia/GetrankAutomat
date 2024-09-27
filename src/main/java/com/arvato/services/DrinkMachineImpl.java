package com.arvato.services;

import com.arvato.dtos.DrinkMachineResponse;
import com.arvato.models.Coin;
import com.arvato.models.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DrinkMachineImpl implements DrinkMachine {

    private final HashMap<Product, Integer> products = new HashMap<>();

    @Override
    public DrinkMachineResponse buy(Product product, Coin... coins) {
        if (!products.containsKey(product) || products.get(product) == 0) {
            return new DrinkMachineResponse(null, Arrays.asList(coins), "Produkt ist nicht auf Lager.");
        }

        int totalInserted = 0;
        for (Coin coin : coins) {
            totalInserted += coin.getValue();
        }
        if (totalInserted < product.price())
            return new DrinkMachineResponse(null, Arrays.asList(coins), "Nicht genug Geld.");
        products.put(product, products.get(product) - 1);
        int difference = totalInserted - product.price();
        List<Coin> returnCoins = calculateChange(difference);
        return new DrinkMachineResponse(product, returnCoins, "Erfolgreicher Kauf.");

    }

    private List<Coin> calculateChange(int difference) {
        List<Coin> availableCoins = new ArrayList<>();

        if (difference == 0) return List.of();

        for (Coin coin : Coin.values()) {
            while (difference >= coin.getValue()) {
                availableCoins.add(coin);
                difference -= coin.getValue();
            }
        }
        return availableCoins;
    }

    @Override
    public void fill(Product product, int quantity) {
        products.put(product, products.getOrDefault(product, 0) + quantity);

    }
}
