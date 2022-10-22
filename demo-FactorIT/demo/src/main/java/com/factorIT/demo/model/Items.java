package com.factorIT.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Items {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private ItemCatalog itemCatalog;
    private Integer quantity;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Items items = (Items) o;
        return Objects.equals(itemCatalog.getId(), items.getItemCatalog().getId());
    }

    @Override
    public int hashCode() {
        return Integer.parseInt(itemCatalog.getId().toString());
    }

    @JsonIgnore
    public double getPrivateValue() {
        return itemCatalog.getPriceUnit();
    }

    public double getValue(){
        return itemCatalog.getPriceUnit() * quantity;
    }
}
