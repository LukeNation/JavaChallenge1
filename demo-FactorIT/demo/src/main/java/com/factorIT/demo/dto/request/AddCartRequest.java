package com.factorIT.demo.dto.request;


import io.micrometer.core.lang.Nullable;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
public class AddCartRequest implements Serializable {

    private Long userId;
    private Integer quantity;

    @Nullable
    private Long itemId;

    @Nullable
    private String itemName;

    @Nullable
    private Double priceUnit;
}
