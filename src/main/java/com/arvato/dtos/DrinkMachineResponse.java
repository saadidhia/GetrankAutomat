package com.arvato.dtos;

import com.arvato.models.Coin;
import com.arvato.models.Product;

import java.util.List;

public record DrinkMachineResponse(Product product, List<Coin> coins, String message) {

}
