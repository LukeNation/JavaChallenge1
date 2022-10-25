package com.factorIT.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Shop {

    @Id
    @GeneratedValue
    private Long id;
    private Long dateTime;
    private Integer cartType;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Items> itemsList;
    private Double totalNoDiscount;
    private Double discount=0d;
    private Double total;

    @Transient
    @JsonIgnore
    private PromotionStrategy promotion;

    public void calculateTotal() {
        if(promotion != null || !cartType.equals(0)){
            if(promotion == null){
                switch (cartType){
                    case 1: promotion = new SpecialDatePromotion(); break;
                    case 2: promotion = new VipPromotion(); break;
                }
            }
            if(promotion != null) {
                discount = promotion.calculateDiscount(this);
            }
            if(itemsList.size()==10){
                discount += totalNoDiscount*0.1;
            }
            total = totalNoDiscount - discount;
        }
    }
}
