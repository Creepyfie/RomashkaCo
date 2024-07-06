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

    public Map<Long, Product> products = new HashMap<>();
    private final AtomicLong counter = new AtomicLong();

    @Override
    public long create(Product productToAdd){

            long id = counter.incrementAndGet();

            products.put(id, productToAdd);
            return id;
    }

    @Override
    public void update(long id, Product editProduct) {
            products.put(id, editProduct);
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
