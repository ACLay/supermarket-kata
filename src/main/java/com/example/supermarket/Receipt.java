package com.example.supermarket;

import java.util.List;

public class Receipt {

    List<ReceiptEntry> items;
    List<ReceiptEntry> discounts;

    public Receipt(List<ReceiptEntry> items, List<ReceiptEntry> discounts) {
        this.items = items;
        this.discounts = discounts;
    }

    public int totalCost() {
        return items.stream().mapToInt(ReceiptEntry::pennyCost).sum() + discounts.stream().mapToInt(ReceiptEntry::pennyCost).sum();
    }
}
