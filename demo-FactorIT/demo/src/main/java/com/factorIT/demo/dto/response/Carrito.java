package com.factorIT.demo.dto.response;

import com.factorIT.demo.model.Items;
import com.factorIT.demo.model.Shop;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Carrito {

    private Integer cartType;
    private Long dateTime;
    private List<Items> itemsList;
    private Double totalNoDiscount;
    private Double discount;
    private Double total;

    public Carrito(Shop cart){
        setDateTime(cart.getDateTime());
        setItemsList(new ArrayList<>(cart.getItemsList()));
        setTotalNoDiscount(cart.getTotalNoDiscount());
        setDiscount(cart.getDiscount());
        setTotal(cart.getTotal());
        setCartType(cart.getCartType());
    }
}
