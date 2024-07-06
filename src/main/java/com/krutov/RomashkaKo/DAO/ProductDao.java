package com.krutov.RomashkaKo.DAO;

import com.krutov.RomashkaKo.Model.Product;

import java.util.List;

public interface ProductDao {

    long create(Product product) throws StringIndexOutOfBoundsException;

    void update(long id, Product editProduct);

    void delete(long id);

    Product getById(long id);

    List<Product> getAllProducts();
}
