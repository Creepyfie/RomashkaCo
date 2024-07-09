package com.krutov.romashka.co.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchRequest {

    private @Size(max = 255) String name;
    private @Min(0) Double price;
    private String priceSign;
    private Boolean available;
}
