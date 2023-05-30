package com.example.supermarket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Basket {
    private Map<Item, Double> items;

    public Basket() {
        items = new HashMap<>();
    }

    public Double getQuanity(Item item) {
        if (items.containsKey(item)) {
            return items.get(item);
        }
        return 0d;
    }

    public void addItem(Item item, Double quantity) {
        if (items.containsKey(item)) {
            items.put(item, items.get(item) + quantity);
        } else {
            items.put(item, quantity);
        }
    }

    public Receipt getReceipt() {
        List<ReceiptEntry> entries = new ArrayList<>();
        items.forEach((item, quantity) -> {
            if (item.hasUnit()) {
                entries.add(new ReceiptEntry(
                        String.format("%s \n %f%s @ %d p/%s", item.name(), quantity, item.unit(), item.pennyCost(), item.unit()),
                        (int) Math.round(quantity * item.pennyCost())));
            } else {
                for (int i = 0; i < quantity; i++) {
                    entries.add(new ReceiptEntry(item.name(), item.pennyCost()));
                }
            }
        });
        return new Receipt(entries, new ArrayList<>());
    }
}
