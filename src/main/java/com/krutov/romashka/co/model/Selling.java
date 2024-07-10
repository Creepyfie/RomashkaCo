package com.krutov.romashka.co.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Selling extends Document {

    @DecimalMin("0")
    BigDecimal price;

    @Builder(toBuilder = true)
    public Selling(Long id, @NotEmpty(message = "name should not be empty") @Size(max = 255) String name, @NotNull Long productId, @Min(0) Long amount, BigDecimal price) {
        super(id, name, productId, amount);
        this.price = price;
    }
}
