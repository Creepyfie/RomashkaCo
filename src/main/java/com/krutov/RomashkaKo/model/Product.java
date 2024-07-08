package com.krutov.RomashkaKo.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class Product {

    Long id;
    @NotEmpty(message = "name shoud not be empty")
    @Size(min = 1, max = 255, message = "name should be less the 256 characters")
    String name;
    @Size(max = 4096, message = "description is longer than 4096 characters")
    String description;
    @Min(value = 0)
    Double price;
    Boolean available;
}
