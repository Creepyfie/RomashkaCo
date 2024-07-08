package com.krutov.romashkaKo.dao;

import com.krutov.romashkaKo.model.Product;

import java.util.List;

public interface ProductDao {

    long create(Product product) throws StringIndexOutOfBoundsException;

    void update(long id, Product editProduct);

    void delete(long id);

    Product getById(long id);

    List<Product> getAllProducts();
}
