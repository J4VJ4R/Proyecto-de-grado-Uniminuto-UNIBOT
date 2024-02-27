package com.example.unibot.model;

public enum Axis {
    X(0),
    Y(1),
    Z(2),
    ;

    private final int value;

    Axis(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
