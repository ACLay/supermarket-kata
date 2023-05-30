package com.example.supermarket;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BasketTest {

    private Item BEANS = new Item("Beans", 50, null);
    private Item COKE = new Item("Coke", 70, null);
    private Item ORANGES = new Item("Oranges", 199, "kg");

    @Test
    void OneBeanTinBasket_NoDiscounts_GeneratesCorrectSingleItemReceipt() {
        Basket basket = new Basket();
        basket.addItem(BEANS, 1d);
        Receipt receipt = basket.getReceipt();

        assertEquals(receipt.items.size(), 1);
        assertEquals(receipt.totalCost(), 50);
    }
    @Test
    void TwoBeanTinBasket_NoDiscounts_GeneratesCorrectSingleItemReceipt() {
        Basket basket = new Basket();
        basket.addItem(BEANS, 2d);
        Receipt receipt = basket.getReceipt();

        assertEquals(receipt.items.size(), 2);
        assertEquals(receipt.totalCost(), 100);
    }

    @Test
    void MultiItemBasket_NoDiscounts_GeneratesCorrectSingleItemReceipt() {
        Basket basket = new Basket();
        basket.addItem(BEANS, 1d);
        basket.addItem(BEANS, 1d);
        basket.addItem(COKE, 2d);
        basket.addItem(ORANGES, 0.2);
        Receipt receipt = basket.getReceipt();

        assertEquals(receipt.items.size(), 5);
        assertEquals(receipt.totalCost(), 280);
    }


}
