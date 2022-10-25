package com.factorIT.demo.service.impl;

import com.factorIT.demo.dto.response.UsuarioVista;
import com.factorIT.demo.model.ItemCatalog;
import com.factorIT.demo.model.Items;
import com.factorIT.demo.model.Shop;
import com.factorIT.demo.model.User;
import com.factorIT.demo.repository.ItemCatalogRepository;
import com.factorIT.demo.repository.ItemsRepository;
import com.factorIT.demo.repository.ShopRepository;
import com.factorIT.demo.repository.UserRepository;
import com.factorIT.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repositoryUser;

    @Autowired
    ItemCatalogRepository repositoryCatalog;

    @Autowired
    ShopRepository repositoryShop;

    @Autowired
    ItemsRepository repositoryItem;

    @Override
    public List<Items> getShopsFromUser(Long dni) {

        log.info("el dni traido es {}",dni);
        User user = repositoryUser.getById(dni);
        LinkedHashSet<Items> setItems = new LinkedHashSet<>();
        List<Shop> shopList = user.getShoppingList();
        List<Items> temporalList = new ArrayList<>();
        if(shopList != null) {
            shopList = shopList.stream().sorted(Comparator.comparing(Shop::getTotal)).collect(Collectors.toList());
            List<List<Items>> shopItemList = shopList.stream().map(Shop::getItemsList).collect(Collectors.toList());
            shopItemList.forEach(setItems::addAll);
            setItems = setItems.stream().sorted(Comparator.comparing(Items::getPrivateValue)).collect(Collectors.toCollection(LinkedHashSet::new));
            temporalList.addAll(setItems);
            Collections.reverse(temporalList);
        }
        return temporalList;
    }

    @Override
    @Transactional
    public Shop addToCart(Long dni, Long itemId, Integer quantity) {
        //log.info("el id es: {} y el usuario es {}",itemId,dni);
        User user = repositoryUser.getById(dni);
        ItemCatalog itemCatalog = repositoryCatalog.getById(itemId);
        log.info("el nombre del usuario es {} y el precio del item es {}",user.getName(),itemCatalog.getPriceUnit());
        Items item = new Items();
        item.setItemCatalog(itemCatalog);
        item.setQuantity(quantity);
        Shop cart = user.getShoppingCart();
        List<Items> cartItemList = cart.getItemsList();
        if(cartItemList.contains(item)){
            item = cartItemList.get(cartItemList.indexOf(item));
            item.setQuantity(item.getQuantity()+quantity);
        }else{
            cart.getItemsList().add(item);
        }
        cart.setTotalNoDiscount(cart.getTotalNoDiscount() + (item.getItemCatalog().getPriceUnit() * quantity));
        user.setShoppingCart(cart);
        repositoryCatalog.save(itemCatalog);
        repositoryItem.save(item);
        repositoryShop.save(cart);
        repositoryUser.save(user);
        return cart;
    }

    @Override
    public void deleteCart(Long userId) {
        //log.info("se prepara todo para eliminar el carrito");
        User user = repositoryUser.getById(userId);
        user.setShoppingCart(null);  //no se elimina el carrito como tal para poder reutilizar este codigo en caso de ejercer la compra añadiendo el carrito a la lista de elementos ya comprados y eliminandolo como carrito.
        //log.info("se elimino el enlace al shop, se guarda el usuario");
        repositoryUser.save(user);
    }

    @Override
    public Shop getCartStatus(Long userId) {
        User user = repositoryUser.getById(userId);
        Shop cart = user.getShoppingCart();
        repositoryShop.save(cart);
        return cart;
    }

    public Shop createCart(Long userId){
        User user = repositoryUser.getById(userId);
        Shop cart = user.getShoppingCart();
        repositoryShop.save(cart);
        repositoryUser.save(user);
        return cart;
    }

    @Override
    public Shop deleteFromCart(Long userId, Long itemId) {
        User user = repositoryUser.getById(userId);
        Shop cart = user.getShoppingCart();
        if(cart != null ){
            List<Items> cartItemList = cart.getItemsList();
            ItemCatalog itemCatalog = repositoryCatalog.getById(itemId);
            Items itemBuscado = new Items();
            itemBuscado.setItemCatalog(itemCatalog);
            if(cartItemList.contains(itemBuscado)){
                Items itemEncontrado = cartItemList.get(cartItemList.indexOf(itemBuscado));
                Double valorADescontar = itemEncontrado.getItemCatalog().getPriceUnit() * itemEncontrado.getQuantity();
                cartItemList.removeIf(each -> {return each.getItemCatalog().getId().equals(itemId);});
                cart.setTotalNoDiscount(cart.getTotalNoDiscount()-valorADescontar);
            }
            user.setShoppingCart(cart);
            repositoryUser.save(user);
        }
        return cart;
    }

    @Override
    public Shop doneShop(Long userId) {
        User user = repositoryUser.getById(userId);
        Shop cart = user.getShoppingCart();
        cart.calculateTotal();
        user.getShoppingList().add(cart);
        user.setShoppingCart(null);
        repositoryUser.save(user);
        return cart;
    }

    public UsuarioVista getUser(Long id){
        User u = repositoryUser.getById(id);
        UsuarioVista user = new UsuarioVista(u);
        return user;
    }
}
