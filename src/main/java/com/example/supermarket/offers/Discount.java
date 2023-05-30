package com.example.supermarket.offers;

import com.example.supermarket.Item;

import java.util.List;

public record Discount(List<Item> items, String description, int pennySaving) {}
