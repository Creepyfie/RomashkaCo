package com.krutov.romashka.co.model;

import com.krutov.romashka.co.model.documents.Document;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
public class Supply extends Document {
    public Supply(long id, String name, long product_id, long amount) {
        super(id, name, product_id, amount);
    }
}
