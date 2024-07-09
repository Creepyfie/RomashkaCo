package com.krutov.dao.Impl;

import com.krutov.romashka.co.dao.ProductDao;
import com.krutov.romashka.co.dto.ProductSearchRequest;
import com.krutov.romashka.co.model.Product;
import com.krutov.romashka.co.util.Direction;
import com.krutov.romashka.co.util.ListData;
import com.krutov.romashka.co.util.SortData;
import org.instancio.Instancio;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryProductDao implements ProductDao {

    public Map<Long, Product> products = new HashMap<>();

    @Override
    public long create(Product product) {

        Long id = Instancio.create(Long.class);
        products.put(id, product.toBuilder().id(id).build());
        return id;
    }

    @Override
    public void update(long id, Product editProduct) {
        if (!products.containsKey(id)) {
            return;
        }
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
    public List<Product> searchProduct(ProductSearchRequest request, ListData listData) {
        return products.values().stream()
            .filter(row -> (request.getAvailable() == null || (row.getAvailable() == request.getAvailable())))
            .filter(row -> (request.getName() == null || row.getName().equals(request.getName())))
            .filter(row -> (request.getPriceSign() == null
                || switch (request.getPriceSign()) {
                case "lt" -> row.getPrice().compareTo(request.getPrice()) < 0;
                case "gt" -> row.getPrice().compareTo(request.getPrice()) > 0;
                case "eq" -> row.getPrice().equals(request.getPrice());
                default -> throw new IllegalArgumentException("����������� ������ ������ ����: %s".formatted(request.getPriceSign()));
            }))
            .sorted(buildComparator(listData.getSortData()))
            .skip(listData.getOffset())
            .limit(listData.getLimit())
            .toList();
    }

    public void clear() {
        products.clear();
    }

    /**
     * Конструирует компаратор для переданныз сортировок.
     */
    private static Comparator<Product> buildComparator(List<SortData> sorts) {

        if (sorts.isEmpty())
            return Comparator.comparing(Product::getId);

        SortData firstSort = sorts.get(0);
        Comparator<Product> result = switch (firstSort.getField()) {
            case "name" -> direction(firstSort.getDirection(), Comparator.comparing(Product::getName));
            case "price" -> direction(firstSort.getDirection(), Comparator.comparing(Product::getPrice));
            default -> throw new IllegalArgumentException("Некорректно указана сортировка: %s".formatted(firstSort.getField()));
        };

        for (int i = 1; i < sorts.size(); i++) {
            SortData sort = sorts.get(i);
            result = switch (sort.getField()) {
                case "name" -> direction(sort.getDirection(), result.thenComparing(Product::getName));
                case "price" -> direction(sort.getDirection(), result.thenComparing(Product::getPrice));
                default -> result;
            };
        }
        return result;
    }

    private static <T> Comparator<T> direction(Direction direction, Comparator<T> comparator) {
        return direction == Direction.DESC
            ? comparator.reversed()
            : comparator;
    }
}
