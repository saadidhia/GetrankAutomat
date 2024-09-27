package com.arvato.models;

public enum Coin {
    COIN_200(200),
    COIN_100(100),
    COIN_50(50),
    COIN_20(20),
    COIN_10(10);


    private final int value;

    Coin(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}