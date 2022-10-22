package com.factorIT.demo.service.impl;

import com.factorIT.demo.model.ItemCatalog;
import com.factorIT.demo.model.Items;
import com.factorIT.demo.model.Shop;
import com.factorIT.demo.model.User;
import com.factorIT.demo.repository.ItemCatalogRepository;
import com.factorIT.demo.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.List;
import static com.factorIT.demo.util.CartFactory.cartFactory;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestUserServiceImpl {

    @Mock
    UserRepository repositoryUser;

    @Mock
    ItemCatalogRepository repositoryCatalog;

    @InjectMocks
    UserServiceImpl service;

  /*  private List<Shop> makeShoppingList(){
        List<Shop> list = new ArrayList<>();
        list.add(Shop.builder().dateTime(20211220L).special(true).total(125.00).build());
        list.add(Shop.builder().dateTime(20211215L).special(false).total(115.00).build());
        list.add(Shop.builder().dateTime(20211225L).special(true).total(100.00).build());
        return list;
    }

    private ItemCatalog makeItemCatalog(){
        return ItemCatalog.builder().id(456L).name("Sopa").priceUnit(100.00).build();

    }
    private List<Items> makeItemsList(){
        List<Items> itemsList = new ArrayList<>();
        itemsList.add(Items.builder().itemCatalog(makeItemCatalog()).quantity(5).build());
        return itemsList;
    }

    private User setUpUser(boolean vip){
        return User.builder().dni(123L).name("name").lastName("lastname").email("email").address("address").shoppingList(makeShoppingList()).shoppingCart(cartFactory(false)).vip(vip).build();
    }

    @Test
    public void testGetShopsFromUserAnyDateDefaultOrder(){
        when(repositoryUser.getById(any())).thenReturn(setUpUser(false));
        List<Shop> response = service.getShopsFromUser(123L);
        assertEquals(3,response.size());
        assertEquals(Long.valueOf(20211215L),response.get(0).getDateTime());
        assertEquals(Double.valueOf(115.00),response.get(0).getTotal());
        assertFalse(response.get(0).getSpecial());
        assertEquals(Long.valueOf(20211220L),response.get(1).getDateTime());
        assertEquals(Double.valueOf(125.00),response.get(1).getTotal());
        assertTrue(response.get(1).getSpecial());
        assertEquals(Long.valueOf(20211225L),response.get(2).getDateTime());
        assertEquals(Double.valueOf(100.00),response.get(2).getTotal());
        assertTrue(response.get(2).getSpecial());
    }

    @Test
    public void testGetShopsFromUserAnyDateOrderedByAmount(){
        when(repositoryUser.getById(any())).thenReturn(setUpUser(false));
        List<Shop> response = service.getShopsFromUser(123L);
        assertEquals(3,response.size());
        assertEquals(Long.valueOf(20211225L),response.get(0).getDateTime());
        assertEquals(Double.valueOf(100.00),response.get(0).getTotal());
        assertTrue(response.get(0).getSpecial());
        assertEquals(Long.valueOf(20211215L),response.get(1).getDateTime());
        assertEquals(Double.valueOf(115.00),response.get(1).getTotal());
        assertFalse(response.get(1).getSpecial());
        assertEquals(Long.valueOf(20211220L),response.get(2).getDateTime());
        assertEquals(Double.valueOf(125.00),response.get(2).getTotal());
        assertTrue(response.get(2).getSpecial());
    }

    @Test
    public void testGetShopsFromUserFromDate(){
        when(repositoryUser.getById(any())).thenReturn(setUpUser(false));
        List<Shop> response = service.getShopsFromUser(123L);
        assertEquals(2,response.size());
        assertEquals(Long.valueOf(20211225L),response.get(0).getDateTime());
        assertEquals(Double.valueOf(100.00),response.get(0).getTotal());
        assertTrue(response.get(0).getSpecial());
        assertEquals(Long.valueOf(20211220L),response.get(1).getDateTime());
        assertEquals(Double.valueOf(125.00),response.get(1).getTotal());
        assertTrue(response.get(1).getSpecial());
        assertFalse(response.stream().anyMatch(each -> each.getDateTime() == 20211215L));

    }

    @Test
    public void testGetShopsFromUserFromDateToDate(){
        when(repositoryUser.getById(any())).thenReturn(setUpUser(false));
        List<Shop> response = service.getShopsFromUser(123L);
        assertEquals(1,response.size());
        assertEquals(Long.valueOf(20211220L),response.get(0).getDateTime());
        assertEquals(Double.valueOf(125.00),response.get(0).getTotal());
        assertTrue(response.get(0).getSpecial());
        assertFalse(response.stream().anyMatch(each -> (each.getDateTime() == 20211215L)||(each.getDateTime() == 20211225L )));
    }

    @Test
    public void testAddToCartById(){
        User userDemo = setUpUser(false);
        when(repositoryUser.getById(any())).thenReturn(userDemo);
        when(repositoryCatalog.getById(any())).thenReturn(makeItemCatalog());
        service.addToCart(123L,456L,1);
        assertEquals(1,userDemo.getShoppingCart().getItemsList().size());
        assertEquals("Sopa",userDemo.getShoppingCart().getItemsList().get(0).getItemCatalog().getName());
        assertEquals(Double.valueOf(100.00),userDemo.getShoppingCart().getTotalNoDiscount());

    }

    @Test
    public void testAddToCartByName(){
        User userDemo = setUpUser(false);
        when(repositoryUser.getById(any())).thenReturn(userDemo);
        service.addToCart(123L,null,1);
        assertEquals(1,userDemo.getShoppingCart().getItemsList().size());
        assertEquals("queso",userDemo.getShoppingCart().getItemsList().get(0).getItemCatalog().getName());
        assertEquals(Double.valueOf(120.00),userDemo.getShoppingCart().getTotalNoDiscount());
    }

    @Test
    public void testAddToCartWithoutCart(){
        User userDemo = setUpUser(false);
        userDemo.setShoppingCart(null);
        when(repositoryUser.getById(any())).thenReturn(userDemo);
        service.addToCart(123L,null,1);
        assertEquals(1,userDemo.getShoppingCart().getItemsList().size());
        assertEquals("queso",userDemo.getShoppingCart().getItemsList().get(0).getItemCatalog().getName());
        assertEquals(Double.valueOf(120.00),userDemo.getShoppingCart().getTotalNoDiscount());
    }

    @Test
    public void testAddToCartWithDiscount(){
        User userDemo = setUpUser(false);
        when(repositoryUser.getById(any())).thenReturn(userDemo);
        service.addToCart(123L,null,4);
        assertEquals(1,userDemo.getShoppingCart().getItemsList().size());
        assertEquals("queso",userDemo.getShoppingCart().getItemsList().get(0).getItemCatalog().getName());
        assertEquals(Double.valueOf(360.00),userDemo.getShoppingCart().getTotalNoDiscount());
        assertEquals(Integer.valueOf(4),userDemo.getShoppingCart().getItemsList().get(0).getQuantity());
    }

    @Test
    public void testDeleteCart(){
        User userDemo = setUpUser(false);
        when(repositoryUser.getById(any())).thenReturn(userDemo);
        assertNotNull(userDemo.getShoppingCart());
        service.deleteCart(123L);
        assertNull(userDemo.getShoppingCart());
    }

    @Test
    public void testGetCartStatusWithList(){
        User userDemo = setUpUser(false);
        Shop cartDemo = cartFactory(false);
        cartDemo.setItemsList(makeItemsList());
        userDemo.setShoppingCart(cartDemo);
        when(repositoryUser.getById(any())).thenReturn(userDemo);
        Shop response = service.getCartStatus(123L);
        assertNotNull(response);
        assertEquals(1,response.getItemsList().size());
        assertEquals("Sopa",response.getItemsList().get(0).getItemCatalog().getName());
        assertEquals(Double.valueOf(100.00),response.getItemsList().get(0).getItemCatalog().getPriceUnit());
    }

    @Test
    public void testGetCartStatusWithNull(){
        User userDemo = setUpUser(false);
        userDemo.setShoppingCart(null);
        when(repositoryUser.getById(any())).thenReturn(userDemo);
        Shop response = service.getCartStatus(123L);
        assertNull(response);
    }

    @Test
    public void testCreateCart(){
        User userDemo = setUpUser(false);
        when(repositoryUser.getById(any())).thenReturn(userDemo);
        service.addToCart(userDemo.getDni(),null,3);
        service.createCart(userDemo.getDni(),true);
        assertNotNull(userDemo.getShoppingCart());
        assertEquals("fideos",userDemo.getShoppingCart().getItemsList().get(0).getItemCatalog().getName());
        assertFalse(userDemo.getShoppingCart().getSpecial());
    }

    @Test
    public void testCreateCartNull(){
        User userDemo = setUpUser(false);
        userDemo.setShoppingCart(null);
        when(repositoryUser.getById(any())).thenReturn(userDemo);
        service.createCart(userDemo.getDni(),true);
        assertNotNull(userDemo.getShoppingCart());
        assertEquals(0,userDemo.getShoppingCart().getItemsList().size());
        assertTrue(userDemo.getShoppingCart().getSpecial());
    }

    @Test
    public void testDeleteItemsFromCart(){
        User userDemo = setUpUser(false);
        userDemo.getShoppingCart().setItemsList(makeItemsList());
        when(repositoryUser.getById(any())).thenReturn(userDemo);
        service.deleteFromCart(userDemo.getDni(),456L);
        assertNotNull(userDemo.getShoppingCart());
        assertEquals(0,userDemo.getShoppingCart().getItemsList().size());
    }

    @Test
    public void testDoneShop(){
        User userDemo = setUpUser(false);
        userDemo.getShoppingCart().setItemsList(makeItemsList());
        userDemo.getShoppingCart().getItemsList().get(0).setQuantity(1);
        when(repositoryUser.getById(any())).thenReturn(userDemo);
        service.doneShop(123L);
        assertNull(userDemo.getShoppingCart());
        assertEquals(4,userDemo.getShoppingList().size());
        assertEquals("Sopa",userDemo.getShoppingList().get(3).getItemsList().get(0).getItemCatalog().getName());
    }

    @Test
    public void testCalculateDiscountNormalDiscount(){
        User userDemo = setUpUser(false);
        userDemo.getShoppingCart().setItemsList(makeItemsList());
        userDemo.getShoppingCart().getItemsList().get(0).setQuantity(4);
        when(repositoryUser.getById(any())).thenReturn(userDemo);
        service.doneShop(123L);
        assertEquals(Double.valueOf(100.00),userDemo.getShoppingList().get(3).getDiscount());
    }

    @Test
    public void testCalculateDiscountSpecialDiscount(){
        User userDemo = setUpUser(false);
        userDemo.getShoppingCart().setItemsList(makeItemsList());
        userDemo.getShoppingCart().setSpecial(true);
        userDemo.getShoppingCart().getItemsList().get(0).setQuantity(4);
        when(repositoryUser.getById(any())).thenReturn(userDemo);
        service.doneShop(123L);
        assertEquals(Double.valueOf(150.00),userDemo.getShoppingList().get(3).getDiscount());
    }

    @Test
    public void testCalculateDiscountVip(){
        User userDemo = setUpUser(true);
        userDemo.getShoppingCart().setItemsList(makeItemsList());
        userDemo.getShoppingCart().getItemsList().get(0).setQuantity(21);
        userDemo.getShoppingCart().setTotalNoDiscount(2100.0);
        when(repositoryUser.getById(any())).thenReturn(userDemo);
        service.doneShop(123L);
        assertEquals(Double.valueOf(600.00),userDemo.getShoppingList().get(3).getDiscount());
    }

    @Test
    public void testCalculateVip(){
        User userDemo = setUpUser(false);
        userDemo.getShoppingCart().setItemsList(makeItemsList());
        userDemo.getShoppingCart().getItemsList().get(0).setQuantity(21);
        userDemo.getShoppingCart().setTotalNoDiscount(8000.0);
        when(repositoryUser.getById(any())).thenReturn(userDemo);
        assertFalse(userDemo.getVip());
        service.doneShop(123L);
        assertTrue(userDemo.getVip());
    }*/

}
