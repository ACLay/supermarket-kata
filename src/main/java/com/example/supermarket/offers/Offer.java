package com.example.supermarket.offers;

import com.example.supermarket.Basket;
import com.example.supermarket.Item;

import java.util.Optional;

public interface Offer {
    Optional<Discount> getDiscount(Basket basket);
}
