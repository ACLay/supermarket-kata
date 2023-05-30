package com.example.supermarket.offers;

import com.example.supermarket.Basket;
import com.example.supermarket.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BuySomeGetSomeFree implements Offer {

    private String description;
    private Item item;
    private int requiredQuantity;
    private int free;

    public BuySomeGetSomeFree(String description, Item item, int requiredQuantity, int free) {
        this.description = description;
        this.item = item;
        this.requiredQuantity = requiredQuantity;
        this.free = free;
    }

    @Override
    public Optional<Discount> getDiscount(Basket basket) {
        List<Item> discountedItems = new ArrayList<>();
        for (Item basketItem : basket.getItems()) {
            if (item.equals(basketItem)) {
                discountedItems.add(basketItem);
                if (discountedItems.size() == requiredQuantity) {
                    return Optional.of(new Discount(discountedItems, description, item.pennyCost() * free));
                }
            }
        }
        return Optional.empty();
    }
}
