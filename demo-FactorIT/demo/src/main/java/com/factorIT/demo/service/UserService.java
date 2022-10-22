package com.factorIT.demo.service;

import com.factorIT.demo.model.Items;
import com.factorIT.demo.model.Shop;

import java.util.List;

public interface UserService {

    List<Shop> getShopsFromUser(Long dni, Long filterFrom, Long filterTo,String orderType);

    void addToCart(Long dni, Long itemId, Integer quantity, String itemName, Double PriceUnit);

    void deleteCart(Long userId);

    List<Items> getCartStatus(Long userId);

    Shop createCart(Long userId);

    void deleteFromCart(Long userId, Long itemId);

    Double doneShop(Long userId);
}
