package com.krutov.DAO.Impl;

import com.krutov.romashka.co.dao.DB.ListData;
import com.krutov.romashka.co.dao.DB.SqlFilters;
import com.krutov.romashka.co.dao.ProductDao;
import com.krutov.romashka.co.model.Product;
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
    public List<Product> getAllProducts(ListData listData, SqlFilters sqlFilters) {
        return products.values().stream().toList();
    }
}
