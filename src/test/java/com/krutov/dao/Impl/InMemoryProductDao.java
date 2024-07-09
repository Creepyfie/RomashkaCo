package com.krutov.dao.Impl;

import com.krutov.romashka.co.dao.DB.Direction;
import com.krutov.romashka.co.dao.DB.ListData;
import com.krutov.romashka.co.dao.DB.SortData;
import com.krutov.romashka.co.dao.ProductDao;
import com.krutov.romashka.co.dto.ProductSearchRequest;
import com.krutov.romashka.co.model.Product;
import lombok.RequiredArgsConstructor;
import org.instancio.Instancio;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparingDouble;

@Repository
@RequiredArgsConstructor
public class InMemoryProductDao implements ProductDao {

    public Map<Long, Product> products = new HashMap<>();

    @Override
    public long create(Product productToAdd){

        Long id = Instancio.create(Long.class);
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
    public List<Product> getAllProducts(ProductSearchRequest request, ListData listData) {
        List<Product> filteredProducts = products.values().stream()
                .filter(row -> (request.getAvailable() == null || (row.getAvailable() == request.getAvailable())))
                .filter(row -> (request.getName() == null || row.getName().equals(request.getName())))
                .filter(row -> (request.getPriceSign() == null
                        || switch (request.getPriceSign()) {
                    case "lt" -> row.getPrice() < request.getPrice();
                    case "gt" -> row.getPrice() > request.getPrice();
                    case "eq" -> row.getPrice().equals(request.getPrice());
                    default -> throw new IllegalArgumentException("некорректно указан фильтр цены: %s".formatted(request.getPriceSign()));
                }))
                .toList();
        for (SortData data: listData.getSortData()) {
            if (data.getField().equals("name")) {
                filteredProducts = filteredProducts.stream()
                        .sorted(row -> data.getDirection().equals(Direction.ASC) ? : .reversed())
                        .toList();
            } else {
                filteredProducts = filteredProducts.stream()
                        .sorted(Comparator.comparingDouble(Product::getPrice))
                        .toList();
            }
        }
        return null;
    }

    public void clear() {
        products.clear();
    }
}
