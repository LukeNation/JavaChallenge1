package com.factorIT.demo.service;

import com.factorIT.demo.dto.response.UsuarioVista;
import com.factorIT.demo.model.Items;
import com.factorIT.demo.model.Shop;

import java.util.List;

public interface UserService {

    List<Items> getShopsFromUser(Long dni);

    Shop addToCart(Long dni, Long itemId, Integer quantity);

    void deleteCart(Long userId);

    Shop getCartStatus(Long userId);

    Shop createCart(Long userId);

    Shop deleteFromCart(Long userId, Long itemId);

    Shop doneShop(Long userId);

    UsuarioVista getUser(Long userId);
}
