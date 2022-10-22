package com.factorIT.demo.model;


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
    private Double discount;
    private Double total;
}
