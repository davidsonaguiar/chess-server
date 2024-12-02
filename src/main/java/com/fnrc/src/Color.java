package com.fnrc.src;

public enum Color {
    BLACK("PRETO"),
    WHITE("BRANCO");

    private final String color;

    Color(String color) {
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }
}