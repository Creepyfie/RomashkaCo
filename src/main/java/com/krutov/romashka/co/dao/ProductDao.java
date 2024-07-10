package com.krutov.romashka.co.dao;

import com.krutov.romashka.co.controller.dto.ProductSearchRequest;
import com.krutov.romashka.co.model.Product;
import com.krutov.romashka.co.util.ListData;

import java.util.List;

public interface ProductDao {

    long create(Product product) throws StringIndexOutOfBoundsException;

    void update(long id, Product editProduct);

    void delete(long id);

    Product findById(long id);

    List<Product> search(ProductSearchRequest request, ListData listData);
}
