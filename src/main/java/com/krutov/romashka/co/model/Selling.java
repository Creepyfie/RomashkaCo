package com.krutov.romashka.co.model;

import com.krutov.romashka.co.model.documents.Document;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class Selling extends Document {
    public Selling(long id, String name, long product_id, long amount, BigDecimal price) {
        super(id, name, product_id, amount);
        this.price = price;
    }

    @DecimalMin("0")
    BigDecimal price;
}
