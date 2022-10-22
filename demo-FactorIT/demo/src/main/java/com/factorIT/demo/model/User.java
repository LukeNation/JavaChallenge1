package com.factorIT.demo.model;

import io.micrometer.core.lang.Nullable;
import lombok.*;

import javax.persistence.*;
import java.util.List;

import static com.factorIT.demo.util.CartFactory.cartFactory;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class User {

    @Id
    private Long dni;
    private String name;
    private String lastName;
    private String email;
    private String address;
    private Boolean vip;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Shop> shoppingList;
    @OneToOne(cascade = CascadeType.ALL)
    @Nullable
    private Shop shoppingCart;

    public Shop getShoppingCart(){
        if(shoppingCart == null){
            shoppingCart = cartFactory(vip);
        }
        return shoppingCart;
    }

}
