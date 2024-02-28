package com.example.unibot.model;

public enum Direction {
    CLOCKWISE(0),
    ANTICLOCKWISE(1),
    ;

    private final int value;

    Direction(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
