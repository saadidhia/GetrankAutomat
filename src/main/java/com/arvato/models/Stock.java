package com.arvato.models;

import java.util.HashMap;


public class Stock {

    private final HashMap<Product, Integer> products;

    public Stock(HashMap<Product, Integer> products) {
        this.products = new HashMap<>();
    }

    public void addProduct(Product product, int quantity){
        products.put(product,products.getOrDefault(product,0)+quantity);
    }


    public void removeProduct(Product product){
        int currentQuantity = products.getOrDefault(product, 0);
        if (currentQuantity > 0) {
            products.put(product, currentQuantity - 1);
        } else {
            throw new IllegalStateException("Produkt, das nicht auf Lager ist, kann nicht entfernt werden.");
        }
    }
    public boolean checkProductDisponibility(Product product){
        return products.getOrDefault(product, 0) > 0;
    }

    public void emptyStock(){
        products.clear();
    }

    public int getQuantity(Product product){
        return products.getOrDefault(product,0);
    }
}
