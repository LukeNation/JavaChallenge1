package com.factorIT.demo.model;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class VipPromotion implements PromotionStrategy{

    @Override
    public Double calculateDiscount(Shop cart) {
        if(cart.getItemsList().size() >5) {
            List<Items> auxList = new ArrayList<>(cart.getItemsList());
            auxList = auxList.stream().sorted(Comparator.comparing(Items::getPrivateValue)).collect(Collectors.toList());
            log.info(auxList.get(0).getItemCatalog().getName());
            return 700d + auxList.get(0).getPrivateValue();
        }
        return 0d;
    }
}
