package com.krutov.romashka.co.model;

import com.krutov.romashka.co.model.documents.Document;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor
public class Supply extends Document {
    Long id;

    @NotEmpty(message = "name should not be empty")
    @Size(max = 255)
    String name;

    @NotNull
    Long productId;

    @Min(0)
    Long amount;
}
