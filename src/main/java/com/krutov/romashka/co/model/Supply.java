package com.krutov.romashka.co.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@With
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Supply {

    Long id;
    String name;
    Long productId;
    Long amount;
}
