package com.factorIT.demo.handler;

import com.factorIT.demo.dto.request.AddCartRequest;
import com.factorIT.demo.dto.request.CreateCartRequest;
import com.factorIT.demo.model.ItemCatalog;
import com.factorIT.demo.model.Items;
import com.factorIT.demo.model.Shop;
import com.factorIT.demo.service.UserService;
import com.factorIT.demo.util.CartFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestShoppingHandler {

    @Mock
    UserService userService;

    @InjectMocks
    ShoppingHandler controller;

    private List<Items> makeShoppingListComplete(){
        List<Items> list = new ArrayList<>();
        list.add(Items.builder().itemCatalog(ItemCatalog.builder().id(1L).priceUnit(100.0).name("flores").build()).quantity(1).build());
        list.add(Items.builder().itemCatalog(ItemCatalog.builder().id(2L).priceUnit(200.0).name("flores").build()).quantity(1).build());
        list.add(Items.builder().itemCatalog(ItemCatalog.builder().id(3L).priceUnit(300.0).name("flores").build()).quantity(1).build());
        list.add(Items.builder().itemCatalog(ItemCatalog.builder().id(4L).priceUnit(400.0).name("flores").build()).quantity(1).build());
        list.add(Items.builder().itemCatalog(ItemCatalog.builder().id(5L).priceUnit(500.0).name("flores").build()).quantity(1).build());
        return list;
    }

    private List<Items> makeShoppingListNotFull(){
        List<Items> list = new ArrayList<>();
        list.add(Items.builder().itemCatalog(ItemCatalog.builder().id(1L).priceUnit(100.0).name("flores").build()).quantity(1).build());
        list.add(Items.builder().itemCatalog(ItemCatalog.builder().id(2L).priceUnit(200.0).name("flores").build()).quantity(1).build());
        return list;
    }

    private Shop makeItemsList(){
        List<Items> itemsList = new ArrayList<>();
        itemsList.add(Items.builder().itemCatalog(ItemCatalog.builder().name("Prueba").priceUnit(23.22).build()).quantity(5).build());
        Shop shop = new Shop();
        shop.setItemsList(itemsList);
        return shop;
    }

    @Test
    public void testGetShopsOk(){
        when(userService.getShopsFromUser(any())).thenReturn(makeShoppingListComplete());
        ResponseEntity<List<Items>> response = controller.getShops(123L);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(4,response.getBody().size());
    }

    @Test
    public void testGetShopsOKEmpty(){
        when(userService.getShopsFromUser(any())).thenReturn(makeShoppingListNotFull());
        ResponseEntity<List<Items>> response = controller.getShops(123L);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(2,response.getBody().size());
    }

    @Test
    public void testCreateCart(){
        ResponseEntity<Shop> response = controller.createCart(CreateCartRequest.builder().userId(14415550L).isSpecial(false).build());
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteCart(){
        ResponseEntity<String> response = controller.deleteCart(14415550L);
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testAddToCartOK(){
        ResponseEntity<String> response = controller.addToCart(AddCartRequest.builder().userId(14415550L).itemId(101L).quantity(2).build());
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testAddToCartBadRequest(){
        ResponseEntity<String> response = controller.addToCart(AddCartRequest.builder().userId(14415550L).build());
        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
    }

    @Test
    public void testDeleteFromCart(){
        ResponseEntity<Shop> response = controller.deleteFromCart(14415550L,12L);
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testGetCartStatus(){
        when(userService.getCartStatus(any())).thenReturn(makeItemsList());
        ResponseEntity<Shop> response = controller.getCartStatus(123L);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(1,response.getBody().getItemsList().size());
        assertEquals("Prueba",response.getBody().getItemsList().get(0).getItemCatalog().getName());
        assertEquals(Integer.valueOf(5),response.getBody().getItemsList().get(0).getQuantity());
    }

    @Test
    public void testGetCartStatusEmptyList(){
        when(userService.getCartStatus(any())).thenReturn(CartFactory.cartFactory(true));
        ResponseEntity<Shop> response = controller.getCartStatus(123L);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(0,response.getBody().getItemsList().size());
    }

    @Test
    public void testDoneShop(){
        ResponseEntity<Shop> response = controller.doneShop(14415550L);
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

}
