package com.factorIT.demo.handler;

import com.factorIT.demo.dto.request.AddCartRequest;
import com.factorIT.demo.dto.request.CreateCartRequest;
import com.factorIT.demo.model.Items;
import com.factorIT.demo.model.Shop;
import com.factorIT.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class ShoppingHandler {

    @Autowired
    UserService service;


    @GetMapping(path = "/getShops")
    public ResponseEntity<List<Items>> getShops (@RequestParam Long userId){

        log.info("se listan las compras del usuario {}",userId);
        return ResponseEntity.ok(service.getShopsFromUser(userId).subList(0,4));
    };

    @PostMapping(path = "/createCart")
    public ResponseEntity<Shop> createCart(@RequestBody CreateCartRequest request){
        Shop cart = service.createCart(request.getUserId());
        log.info("se ha solicitado crear un carrito para el usuario: {} de categoria especial: {}",request.getUserId(), request.getIsSpecial());
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping(path="/deleteCart")
    public ResponseEntity<String> deleteCart(@RequestParam Long userId){
        service.deleteCart(userId);
        log.info("se ha eliminado al carrito del usuario {}",userId);
        return ResponseEntity.ok("se ha eliminado el carrito satisfactoriamente");
    }

    @PostMapping(path="/addToCart")
    public ResponseEntity<String> addToCart(@RequestBody AddCartRequest request){
        if(request.getItemId() == null && (request.getItemName() ==null || request.getPriceUnit() == null)){
            return ResponseEntity.badRequest().body("error, debe ingresar el id de un item valido o su nombre y precio correspondientes");
        }

        service.addToCart(request.getUserId(), request.getItemId(), request.getQuantity());
        log.info("el item {} se ha agregado al carrito del usuario {}", request.getItemName(),request.getUserId());
        //Shop cart = service.getCartStatus(request.getUserId());
        return ResponseEntity.ok("se añadio el item al carrito");
    }

    @DeleteMapping(path="/deleteFromCart")
    public ResponseEntity<Shop> deleteFromCart(@RequestParam Long userId, @RequestParam Long itemId){
        Shop cart = service.deleteFromCart(userId,itemId);
        log.info("se ha eliminado del carrito del usuario {} el item con id {}",userId,itemId);
        return ResponseEntity.ok(cart);
    }

    @GetMapping(path = "/getCartStatus")
    public ResponseEntity<Shop> getCartStatus(@RequestParam Long userId){
        Shop items =  service.getCartStatus(userId);

        return ResponseEntity.ok(items);
    }

    @PostMapping(path = "/doneShop")
    public ResponseEntity<String> doneShop(@RequestParam Long userId){
        return ResponseEntity.ok("se finalizo la compra del usuario por un valor de: $" + service.doneShop(userId));
    }

}
