package com.example.supermarket;

public record Item (String name, int pennyCost, String unit) {
    public boolean hasUnit() {
        return unit != null;
    }
}
