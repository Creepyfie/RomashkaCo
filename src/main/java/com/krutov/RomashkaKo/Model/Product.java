package com.krutov.RomashkaKo.Model;

import lombok.Value;

@Value
public class Product {

    Long id;
    String name;
    String description;
    Double price;
    Boolean available;
}
