package com.factorIT.demo.handler;

import com.factorIT.demo.dto.request.AddCartRequest;
import com.factorIT.demo.dto.request.CreateCartRequest;
import com.factorIT.demo.model.Items;
import com.factorIT.demo.model.Shop;
import com.factorIT.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class ShoppingHandler {

    @Autowired
    UserService service;


    @GetMapping(path = "/getShops")
    public ResponseEntity<List<Shop>> getShops (@RequestParam Long userId,
                                    @RequestParam(required = false) Long fromDate,
                                    @RequestParam(required = false) Long toDate,
                                    @RequestParam(required = false) String orderType){











        
        log.info("se listan las compras del usuario {}",userId);
        return ResponseEntity.ok(service.getShopsFromUser(userId,fromDate,toDate,orderType));
    };

    @PostMapping(path = "/createCart")
    public ResponseEntity<String> createCart(@RequestBody CreateCartRequest request){
        service.createCart(request.getUserId(),request.getIsSpecial());
        log.info("se ha solicitado crear un carrito para el usuario: {} de categoria especial: {}",request.getUserId(), request.getIsSpecial());
        return ResponseEntity.ok("se ha creado un carrito para el usuario solicitado");
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

        service.addToCart(request.getUserId(), request.getItemId(), request.getQuantity(),request.getItemName(),request.getPriceUnit());
        log.info("el item {} se ha agregado al carrito del usuario {}", request.getItemName(),request.getUserId());
        return ResponseEntity.ok("El item ha sido añadido correctamente al carrito del usuario");
    }

    @DeleteMapping(path="/deleteFromCart")
    public ResponseEntity<String> deleteFromCart(@RequestParam Long userId, @RequestParam Long itemId){
        service.deleteFromCart(userId,itemId);
        log.info("se ha eliminado del carrito del usuario {} el item con id {}",userId,itemId);
        return ResponseEntity.ok("el item se ha eliminado del carrito satisfactoriamente");
    }

    @GetMapping(path = "/getCartStatus")
    public ResponseEntity<List<Items>> getCartStatus(@RequestParam Long userId){
        List<Items>items =  service.getCartStatus(userId);

        if(items == null ){
            items = new ArrayList<>();
        }
        return ResponseEntity.ok(items);
    }

    @PostMapping(path = "/doneShop")
    public ResponseEntity<String> doneShop(@RequestParam Long userId){
        return ResponseEntity.ok("se finalizo la compra del usuario por un valor de: $" + service.doneShop(userId));
    }

}
