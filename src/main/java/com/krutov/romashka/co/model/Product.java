package com.krutov.romashka.co.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.With;

import java.math.BigDecimal;

@With
@Value
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class Product {

    Long id;
    String name;
    String description;
    BigDecimal price;
    Boolean available;
}
