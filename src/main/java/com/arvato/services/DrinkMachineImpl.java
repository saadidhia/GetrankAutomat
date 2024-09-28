package com.arvato.services;

import com.arvato.dtos.DrinkMachineResponse;
import com.arvato.models.Coin;
import com.arvato.models.Product;
import com.arvato.models.Stock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DrinkMachineImpl implements DrinkMachine {


    private final Stock stock;

    public DrinkMachineImpl(Stock stock) {
        this.stock = stock;
    }

    /**
     * This method allows a user to buy a drink from the drink machine by inserting coins.
     * It checks the availability of the product, validates if the inserted coins cover the product price,
     * and calculates the change.
     *
     * @param product the product that the user wants to buy.
     * @param coins a variable number of {@link Coin} objects representing the coins inserted by the user.
     * @return a {@link DrinkMachineResponse} object indicating the result of the transaction.
     *         - If the product is out of stock, it returns a response indicating that the product is unavailable and returns the inserted coins.
     *         - If the inserted coins are not enough, it returns a response indicating insufficient funds and returns the coins.
     *         - If the purchase is successful, it deducts the product from stock, calculates the change based on the total inserted coins,
     *           and returns a response indicating success with the appropriate change.
     */
    @Override
    public DrinkMachineResponse buy(Product product, Coin... coins) {
        if (!stock.checkProductDisponibility(product)) {
            return new DrinkMachineResponse(null, Arrays.asList(coins), "Produkt ist nicht auf Lager.");
        }

        int totalInserted = 0;
        for (Coin coin : coins) {
            totalInserted += coin.getValue();
        }
        if (totalInserted < product.price())
            return new DrinkMachineResponse(null, Arrays.asList(coins), "Nicht genug Geld.");

        stock.removeProduct(product);
        int difference = totalInserted - product.price();
        List<Coin> returnCoins = calculateChange(difference);
        return new DrinkMachineResponse(product, returnCoins, "Erfolgreicher Kauf.");

    }

    /**
     * Calculates the list of coins needed to make up the given difference.
     * It returns a list of coins, starting from the highest denomination
     * available and working its way down until the exact change is reached.
     *
     * @param difference the amount of change to be calculated, in the smallest unit of currency
     * @return a list of {@link  Coin} representing the coins required to make up the given difference;
     *         if the difference is 0, an empty list is returned
     */
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

    /**
     * Adds the specified quantity of the given product to the drink machine.
     * If the product already exists in the drink machine, the quantity is increased by the specified amount;
     * otherwise, the product is added with the specified quantity.
     *
     * @param product the {@code Product} to be added or updated in the inventory
     * @param quantity the amount of the product to be added
     */
    @Override
    public void fill(Product product, int quantity) {
        stock.addProduct(product,quantity);

    }

    /**
     * Empties the drink machine by removing all products from the inventory.
     */
    @Override
    public void empty() {
        stock.emptyStock();
    }
}
