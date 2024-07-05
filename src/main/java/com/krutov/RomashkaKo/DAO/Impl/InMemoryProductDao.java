package com.krutov.RomashkaKo.DAO.Impl;

import com.krutov.RomashkaKo.DAO.ProductDao;
import com.krutov.RomashkaKo.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@RequiredArgsConstructor
public class InMemoryProductDao implements ProductDao {

    Map<Long, Product> products = new HashMap<>();
    private final AtomicLong counter = new AtomicLong();

    @Override
    public long create(String productName, String description, Double price, Boolean available){

        if (productName.length() < 256 && description.length() < 4097) {

            long id = counter.incrementAndGet();
            Product productToAdd = new Product(id, productName, description, price, available);

            products.put(id, productToAdd);
            return id;
        } else {
            throw new StringIndexOutOfBoundsException();
        }
    }

    @Override
    public void update(long id, Product editProduct) {
        if (editProduct.getName().length() < 256 && editProduct.getDescription().length() < 4097) {
            products.put(id, editProduct);
        } else {
            throw new StringIndexOutOfBoundsException();
        }
    }

    @Override
    public void delete(long id) {
        products.remove(id);
    }

    @Override
    public Product getById(long id) {
        return products.get(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return products.values().stream().toList();
    }
}
