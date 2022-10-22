package com.factorIT.demo.handler;

import com.factorIT.demo.dto.request.AddCartRequest;
import com.factorIT.demo.dto.request.CreateCartRequest;
import com.factorIT.demo.model.ItemCatalog;
import com.factorIT.demo.model.Items;
import com.factorIT.demo.model.Shop;
import com.factorIT.demo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestShoppingHandler {

    @Mock
    UserService userService;

    @InjectMocks
    ShoppingHandler controller;

    /*private List<Shop> makeShoppingList(){
        List<Shop> list = new ArrayList<>();
        list.add(Shop.builder().dateTime(20211221L).special(true).total(125.00).build());
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
        when(userService.getShopsFromUser(any())).thenReturn(makeShoppingList());
        ResponseEntity<List<Shop>> response = controller.getShops(123L);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(1,response.getBody().size());
        assertEquals(Long.valueOf(20211221L),response.getBody().get(0).getDateTime());
        assertEquals(Double.valueOf(125.00),response.getBody().get(0).getTotal());
        assertTrue(response.getBody().get(0).getSpecial());
    }

    @Test
    public void testCreateCart(){
        ResponseEntity<String> response = controller.createCart(CreateCartRequest.builder().userId(14415550L).isSpecial(false).build());
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteCart(){
        ResponseEntity<String> response = controller.deleteCart(14415550L);
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testAddToCartOK(){
        ResponseEntity<Shop> response = controller.addToCart(AddCartRequest.builder().userId(14415550L).quantity(2).build());
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testAddToCartBadRequest(){
        ResponseEntity<Shop> response = controller.addToCart(AddCartRequest.builder().userId(14415550L).build());
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
        when(userService.getCartStatus(any())).thenReturn(null);
        ResponseEntity<Shop> response = controller.getCartStatus(123L);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(0,response.getBody().getItemsList().size());
    }

    @Test
    public void testDoneShop(){
        ResponseEntity<Shop> response = controller.doneShop(14415550L);
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }*/

}
