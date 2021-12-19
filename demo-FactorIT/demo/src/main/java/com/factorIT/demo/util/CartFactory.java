package com.factorIT.demo.util;

import com.factorIT.demo.model.Items;
import com.factorIT.demo.model.Shop;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CartFactory {

    public static Shop cartFactory(Boolean isSpecial){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDateTime now = LocalDateTime.now();
        String fechaStr = dtf.format(now);
        Long fecha = Long.parseLong(fechaStr);
        //log.info("la fecha es: {} creada a partir del String {}",fecha,fechaStr);
        Shop cart = new Shop();
        List<Items> items = new ArrayList<Items>();
        cart.setItemsList(items);
        cart.setDateTime(fecha);
        cart.setDiscount(0.0);
        cart.setTotal(0.0);
        cart.setTotalNoDiscount(0.0);
        cart.setSpecial(isSpecial);
        return cart;
    }

}
