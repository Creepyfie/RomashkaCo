package com.krutov.romashka.co.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Supply extends Document {

    @Builder(toBuilder = true)
    public Supply(Long id, @NotEmpty(message = "name should not be empty") @Size(max = 255) String name, @NotNull Long productId, @Min(0) Long amount) {
        super(id, name, productId, amount);
    }
}
