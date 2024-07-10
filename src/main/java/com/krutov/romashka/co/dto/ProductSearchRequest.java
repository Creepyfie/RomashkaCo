package com.krutov.romashka.co.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchRequest {

    @Size(max = 255)
    private String name;

    @DecimalMin("0")
    private BigDecimal price;

    private String priceSign;

    private Boolean available;
}
