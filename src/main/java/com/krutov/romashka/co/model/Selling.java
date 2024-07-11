package com.krutov.romashka.co.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.math.BigDecimal;

@Data
@With
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Selling {

    Long id;
    String name;
    Long productId;
    Long amount;
    /**
     * Total price for this amount of products
     */
    BigDecimal totalPrice;
}
