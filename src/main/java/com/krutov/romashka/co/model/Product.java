package com.krutov.romashka.co.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class Product {

    Long id;

    @NotEmpty(message = "name shoud not be empty")
    @Size(min = 1, max = 255, message = "name should be less the 256 characters")
    String name;

    @Size(max = 4096, message = "description is longer than 4096 characters")
    String description;

    @DecimalMin("0")
    BigDecimal price;

    Boolean available;
}
