package com.example.supermarket;

import com.example.supermarket.offers.Discount;
import com.example.supermarket.offers.Offer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class Basket {
    private List<Item> items;

    public Basket() {
        items = new ArrayList<>();
    }

    private Basket(List<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public long itemsMatching(Collection<Item> filterItems) {
        return items.stream().filter(filterItems::contains).count();
    }

    public List<Item> getItems() {
        return List.copyOf(items);
    }

    public void addItem(UnitPricedItem item, Double quantity) {
        items.add(new Item(
                String.format("%s %f %s @ %d p/%s", item.name(), quantity, item.unit(), item.pennyCostPerUnit(), item.unit()),
                (int) Math.round(quantity * item.pennyCostPerUnit())));
    }

    public Receipt getReceipt(List<Offer> offers) {
        List<ReceiptEntry> entries = new ArrayList<>();
        items.forEach(item -> entries.add(new ReceiptEntry(item.name(), item.pennyCost())));
        List<ReceiptEntry> discounts = new ArrayList<>();

        Basket remainder = this;
        boolean discountFound;
        do {
            // find an applicable offer
            Optional<Discount> discount = Optional.empty();
            for (Offer offer : offers) {
                discount = offer.getDiscount(remainder);
                if (discount.isPresent()) {
                    break;
                }
            }
            discountFound = discount.isPresent();
            // apply it
            if (discountFound) {
                discounts.add(new ReceiptEntry(discount.get().description(), -discount.get().pennySaving()));
                List<Item> prunedItems = new ArrayList<>(remainder.getItems());
                for (Item discounted : discount.get().items()) {
                    prunedItems.remove(discounted);
                }
                remainder = new Basket(prunedItems);
            }
        } while (discountFound);

        return new Receipt(entries, discounts);
    }
}
