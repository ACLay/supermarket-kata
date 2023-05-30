package com.example.supermarket.offers;

import com.example.supermarket.Basket;
import com.example.supermarket.Item;

import java.util.*;

public class SetPriceForMultiple implements Offer {

    private final String description;
    private final List<Item> items;
    private final int requiredQuantity;
    private final int pennyCost;

    public SetPriceForMultiple(String description, int requiredQuantity, int pennyCost, Item... items) {
        this.description = description;
        this.items = List.of(items);
        this.requiredQuantity = requiredQuantity;
        this.pennyCost = pennyCost;
    }

    @Override
    public Optional<Discount> getDiscount(Basket basket) {
        List<Item> discountedItems = new ArrayList<>();
        int regularCost = 0;
        for (Item basketItem : basket.getItems()) {
            if (items.contains(basketItem)) {
                discountedItems.add(basketItem);
                regularCost += basketItem.pennyCost();
                if (discountedItems.size() == requiredQuantity) {
                    return Optional.of(new Discount(discountedItems, description, regularCost - pennyCost));
                }
            }
        }
        return Optional.empty();
    }
}
