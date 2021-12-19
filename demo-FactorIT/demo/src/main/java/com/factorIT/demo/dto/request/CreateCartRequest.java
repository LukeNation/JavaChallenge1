package com.factorIT.demo.dto.request;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
public class CreateCartRequest implements Serializable {

    private Long userId;
    private Boolean isSpecial;

}
