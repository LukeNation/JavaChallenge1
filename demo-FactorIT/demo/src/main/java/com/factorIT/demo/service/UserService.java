package com.factorIT.demo.service;

import com.factorIT.demo.model.Items;
import com.factorIT.demo.model.Shop;

import java.util.List;

public interface UserService {

    List<Items> getShopsFromUser(Long dni);

    void addToCart(Long dni, Long itemId, Integer quantity);

    void deleteCart(Long userId);

    Shop getCartStatus(Long userId);

    Shop createCart(Long userId);

    Shop deleteFromCart(Long userId, Long itemId);

    Double doneShop(Long userId);
}
