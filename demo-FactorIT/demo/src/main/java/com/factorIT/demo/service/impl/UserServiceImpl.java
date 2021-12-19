package com.factorIT.demo.service.impl;

import com.factorIT.demo.model.ItemCatalog;
import com.factorIT.demo.model.Items;
import com.factorIT.demo.model.Shop;
import com.factorIT.demo.model.User;
import com.factorIT.demo.repository.ItemCatalogRepository;
import com.factorIT.demo.repository.UserRepository;
import com.factorIT.demo.service.UserService;
import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import static com.factorIT.demo.util.CartFactory.cartFactory;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repositoryUser;

    @Autowired
    ItemCatalogRepository repositoryCatalog;


    @Override
    public List<Shop> getShopsFromUser(Long dni, Long filterFrom, Long filterTo, String orderType) {

        log.info("el dni traido es {}",dni);
        User user = repositoryUser.getById(dni);
        List<Shop> shopList = null;
        if(user != null) {
            shopList = user.getShoppingList();
            if(shopList != null) {
                if (orderType != null && orderType.toLowerCase().equals("amount")) {
                    shopList = shopList.stream().sorted(Comparator.comparing(Shop::getTotal)).collect(Collectors.toList());
                }
                else{
                    shopList = shopList.stream().sorted(Comparator.comparing(Shop::getDateTime)).collect(Collectors.toList());
                }
                if (filterFrom != null) {
                    shopList = shopList.stream().filter(each -> {
                        return each.getDateTime() >= filterFrom;
                    }).collect(Collectors.toList());
                    log.info("la cantidad de compras a partir de la fecha solicitada es {}", shopList.size());
                    if (filterTo != null) {
                        shopList = shopList.stream().filter(each -> {
                            return each.getDateTime() <= filterTo;
                        }).collect(Collectors.toList());
                        log.info("la cantidad de compras entre las fechas solicitadas es {}", shopList.size());
                    }
                }
            }
        }
        return shopList;
    }

    @Override
    public void addToCart(Long dni, Long itemId, Integer quantity, String itemName, Double priceUnit) {
        //log.info("el id es: {} y el usuario es {}",itemId,dni);
        User user = repositoryUser.getById(dni);
        ItemCatalog itemCatalog;
        if(itemId != null) {
             itemCatalog = repositoryCatalog.getById(itemId);
        }else{
            itemCatalog = new ItemCatalog();
            itemCatalog.setName(itemName);
            itemCatalog.setPriceUnit(priceUnit);
        }
        log.info("el nombre del usuario es {} y el precio del item es {}",user.getName(),itemCatalog.getPriceUnit());
        Items item = new Items();
        item.setItemCatalog(itemCatalog);
        item.setQuantity(quantity);
        Shop cart = user.getShoppingCart();
        if(cart == null){
            cart = cartFactory(false);
        }
        cart.getItemsList().add(item);
        int discountQuantity=0;
        if(quantity>=4){
            discountQuantity = quantity/4;
            log.info("se esta aplicando promocion 4x3 por la cantidad de {} ofertas",discountQuantity);
        }
        cart.setTotalNoDiscount(cart.getTotalNoDiscount() +(item.getItemCatalog().getPriceUnit()*(quantity-discountQuantity)));
        user.setShoppingCart(cart);
        repositoryUser.save(user);
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
    public List<Items> getCartStatus(Long userId) {
        User user = repositoryUser.getById(userId);
        Shop cart = user.getShoppingCart();
        if(cart != null){
            return cart.getItemsList();
        } else {
            return null;
        }
    }

    public void createCart(Long userId, Boolean isSpecial){
        User user = repositoryUser.getById(userId);
        if(user.getShoppingCart() == null) {
            Shop cart = cartFactory(isSpecial);
            user.setShoppingCart(cart);
            repositoryUser.save(user);
        }
    }

    @Override
    public void deleteFromCart(Long userId, Long itemId) {
        User user = repositoryUser.getById(userId);
        Shop cart = user.getShoppingCart();
        if(cart != null ){
            cart.getItemsList().removeIf(each -> {return each.getItemCatalog().getId().equals(itemId);});
            user.setShoppingCart(cart);
            repositoryUser.save(user);
        }
    }

    @Override
    public Double doneShop(Long userId) {
        User user = repositoryUser.getById(userId);
        Shop cart = user.getShoppingCart();
        Double total = 0.0;
        if(cart != null) {
            Double discountAmount = calculateDiscount(user);
            cart.setDiscount(discountAmount);
            cart.setTotal(cart.getTotalNoDiscount() - discountAmount);
            user.getShoppingList().add(cart);
            calculateVIP(user);
            deleteCart(userId);
            total = cart.getTotal();
        }
        repositoryUser.save(user);
        return total;
    }

    @NotNull
    private Double calculateDiscount(User user){
        Double discountAmount = 0.0;
        Shop cart = user.getShoppingCart();
        Integer cantidadItems = cart.getItemsList().stream().mapToInt(Items::getQuantity).sum();
        log.info("la cantidad de items en el carrito es {}",cantidadItems);
        if(cantidadItems > 3){
            log.info("se suma descuento por cantidad de items");
            discountAmount += 100.0;
            if(cart.getSpecial()){
                log.info("se suma descuento extra a la cantidad por ser carrito especial");
                discountAmount +=50.0;
            }
        }
        if(user.getVip()){
            user.setVip(false);
            log.info("el monto total sin descuento es {}",cart.getTotalNoDiscount());
            if(cart.getTotalNoDiscount()>2000){
                log.info("se suma descuento por ser VIP y comprar mas de 2000");
                discountAmount += 500;
            }
        }
        log.info("el descuento total es: {}",discountAmount);
        return discountAmount;
    }

    private void calculateVIP(User user){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMM");
        LocalDateTime now = LocalDateTime.now();
        String fechaInicioStr = dtf.format(now)+"01";
        String fechaFinStr = dtf.format(now)+"31";
        Long fechaStart = Long.parseLong(fechaInicioStr);
        Long fechaEnd = Long.parseLong(fechaFinStr);
        log.info("para calcular el vip se veran entre las fechas {} y {}",fechaStart,fechaEnd);
        List<Shop> compraDelMes = getShopsFromUser(user.getDni(),fechaStart,fechaEnd,null);
        Double total = compraDelMes.stream().mapToDouble(Shop::getTotal).sum();
        log.info("el total a comparar para el VIP es: {}",total);
        if(total >= 5000){
            user.setVip(true);
        }
    }
}
