package com.example.supermarket;

import com.example.supermarket.offers.BuySomeGetSomeFree;
import com.example.supermarket.offers.Offer;
import com.example.supermarket.offers.SetPriceForMultiple;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasketTest {

    private final Item BEANS = new Item("Beans", 50);
    private final Item COKE = new Item("Coke", 70);
    private final UnitPricedItem ORANGES = new UnitPricedItem("Oranges", 199, "kg");
    private final Item ALE_1 = new Item("Ale 1", 250);
    private final Item ALE_2 = new Item("Ale 2", 250);
    private final Item ALE_3 = new Item("Ale 3", 250);


    private final Offer THREE_FOR_TWO_BEANS = new BuySomeGetSomeFree("Beans 3 for 2", BEANS, 3, 1);
    private final Offer TWO_COKES_FOR_ONE_POUND = new SetPriceForMultiple("2 cokes for £1", 2, 100, COKE);
    private final Offer THREE_ALES_FOR_SIX_POUNDS = new SetPriceForMultiple("3 Ales for £6", 3, 600, ALE_1, ALE_2, ALE_3);
    private final List<Offer> NO_OFFERS = new ArrayList<>();
    private final List<Offer> ALL_OFFERS = List.of(THREE_FOR_TWO_BEANS, TWO_COKES_FOR_ONE_POUND, THREE_ALES_FOR_SIX_POUNDS);


    @Test
    void OneBeanTinBasket_NoDiscounts_GeneratesCorrectSingleItemReceipt() {
        Basket basket = new Basket();
        basket.addItem(BEANS);
        Receipt receipt = basket.getReceipt(NO_OFFERS);

        assertEquals(1, receipt.items.size());
        assertEquals(50, receipt.totalCost());
    }
    @Test
    void TwoBeanTinBasket_NoDiscounts_GeneratesCorrectReceipt() {
        Basket basket = new Basket();
        basket.addItem(BEANS);
        basket.addItem(BEANS);
        Receipt receipt = basket.getReceipt(NO_OFFERS);

        assertEquals(2, receipt.items.size());
        assertEquals(100, receipt.totalCost());
    }

    @Test
    void MultiItemBasket_NoDiscounts_GeneratesCorrectReceipt() {
        Basket basket = new Basket();
        basket.addItem(BEANS);
        basket.addItem(BEANS);
        basket.addItem(COKE);
        basket.addItem(COKE);
        basket.addItem(ORANGES, 0.2);
        Receipt receipt = basket.getReceipt(NO_OFFERS);

        assertEquals(5, receipt.items.size());
        assertEquals(280, receipt.totalCost());
    }

    @Test
    void ThreeBeanBasket_ThreeForTwoOffer_GeneratesCorrectReceipt() {
        Basket basket = new Basket();
        basket.addItem(BEANS);
        basket.addItem(BEANS);
        basket.addItem(BEANS);
        Receipt receipt = basket.getReceipt(Collections.singletonList(THREE_FOR_TWO_BEANS));

        assertEquals(3, receipt.items.size());
        assertEquals(1, receipt.discounts.size());
        assertEquals(100, receipt.totalCost());
        assertEquals("Beans 3 for 2", receipt.discounts.get(0).name());
    }

    @Test
    void SampleBasket_MultipleOffers_GeneratesCorrectReceipt() {
        Basket basket = new Basket();
        basket.addItem(BEANS);
        basket.addItem(BEANS);
        basket.addItem(BEANS);
        basket.addItem(COKE);
        basket.addItem(COKE);
        basket.addItem(ORANGES, 0.2);
        Receipt receipt = basket.getReceipt(ALL_OFFERS);

        assertEquals(6, receipt.items.size());
        assertEquals(2, receipt.discounts.size());
        assertEquals(240, receipt.totalCost());
    }

    @Test
    void SevenAles_AleOffer_AppliesOfferTwice() {
        Basket basket = new Basket();
        basket.addItem(ALE_1);
        basket.addItem(ALE_1);
        basket.addItem(ALE_2);
        basket.addItem(ALE_2);
        basket.addItem(ALE_3);
        basket.addItem(ALE_3);
        basket.addItem(ALE_3);
        Receipt receipt = basket.getReceipt(List.of(THREE_ALES_FOR_SIX_POUNDS));

        assertEquals(7, receipt.items.size());
        assertEquals(2, receipt.discounts.size());
        assertEquals(1450, receipt.totalCost());
    }
}
