package com.arvato.services;

import com.arvato.dtos.DrinkMachineResponse;
import com.arvato.models.Coin;
import com.arvato.models.Product;

import java.util.List;

public interface DrinkMachine {

    DrinkMachineResponse buy(Product product, Coin... coins);
    void fill(Product product, int quantity);
}
