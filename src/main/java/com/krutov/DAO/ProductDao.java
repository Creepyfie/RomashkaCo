package com.krutov.DAO;

import com.krutov.Model.Product;

import java.util.List;

public interface ProductDao {

    long create(String productName, String description, Double price, Boolean available) throws StringIndexOutOfBoundsException;

    void update(long id, Product editProduct);

    void delete(long id);

    Product getById(long id);

    List<Product> getAllProducts();
}
