package com.factorIT.demo.model;

public class SpecialDatePromotion implements PromotionStrategy{

    @Override
    public Double calculateDiscount(Shop cart) {
        if(cart.getItemsList().size() >5) {
            return 500d;
        }
        return 0d;
    }
}
