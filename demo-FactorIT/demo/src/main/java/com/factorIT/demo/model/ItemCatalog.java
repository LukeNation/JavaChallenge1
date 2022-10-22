package com.factorIT.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ItemCatalog {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
    private String name;
    private Double priceUnit;

}
