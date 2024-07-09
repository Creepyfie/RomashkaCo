package com.krutov.romashka.co.dao.impl;

import com.krutov.romashka.co.dao.ProductDao;
import com.krutov.romashka.co.dao.util.SqlFilters;
import com.krutov.romashka.co.dto.ProductSearchRequest;
import com.krutov.romashka.co.model.Product;
import com.krutov.romashka.co.util.ListData;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static java.util.function.Predicate.not;
import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Repository
@RequiredArgsConstructor
public class SqlProductDao implements ProductDao {

    private final NamedParameterJdbcOperations jdbc;

    private final RowMapper<Product> rowMapper = new ProductRowMapper();

    @Override
    public long create(Product product) throws StringIndexOutOfBoundsException {

        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("name", product.getName())
            .addValue("description", product.getDescription())
            .addValue("price", product.getPrice())
            .addValue("available", product.getAvailable());

        String sql = """
            INSERT INTO products (name, description, price, available)
            VALUES (:name,:description,:price, :available)
            RETURNING id
            """;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(sql, params, keyHolder);

        return keyHolder.getKeyAs(Long.class);
    }

    @Override
    public void update(long id, Product editProduct) {

        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("id", id)
            .addValue("name", editProduct.getName())
            .addValue("description", editProduct.getDescription())
            .addValue("price", editProduct.getPrice())
            .addValue("available", editProduct.getAvailable());

        String sql = """
            UPDATE products
            SET name = :name, description = :description, price = :price, available = :available
            WHERE id = id
            """;
        jdbc.update(sql, params);
    }

    @Override
    public void delete(long id) {

        SqlParameterSource param = new MapSqlParameterSource()
            .addValue("id", id);

        String sql = """
            DELETE FROM products
            WHERE id = :id
            """;
        jdbc.update(sql, param);
    }


    @Override
    public Product getById(long id) {

        SqlParameterSource param = new MapSqlParameterSource()
            .addValue("id", id);

        String sql = """
            SELECT * FROM products
            WHERE id = :id
            """;

        List<Product> result = jdbc.query(sql, param, rowMapper);

        return !result.isEmpty() ? result.get(0) : null;
    }

    @Override
    public List<Product> searchProduct(ProductSearchRequest request, ListData listData) {

        SqlFilters.Builder filtersBuilder = SqlFilters.builder()
            .like("name", request.getName())
            .eq("available", request.getAvailable());

        if (isNotEmpty(request.getPriceSign())) {
            switch (request.getPriceSign()) {
                case "lt" -> filtersBuilder.lt("price", request.getPrice());
                case "gt" -> filtersBuilder.gt("price", request.getPrice());
                case "eq" -> filtersBuilder.eq("price", request.getPrice());
                default -> throw new IllegalArgumentException("некорректно указан фильтр цены: %s".formatted(request.getPriceSign()));
            }
        }

        SqlFilters filters = filtersBuilder.build();

        String sql = """
            SELECT * FROM products
            """ + filters.makeWhereClause()
            + getOrderByClause(listData)
            + " LIMIT " + listData.getLimit() + " OFFSET " + listData.getOffset();

        return jdbc.query(sql, filters.getParams(), rowMapper);
    }

    public String getOrderByClause(ListData listData) {
        return emptyIfNull(listData.getSortData())
            .stream()
            .map(sort -> sort.getField() + " " + sort.getDirection().name())
            .filter(not(String::isBlank))
            .reduce((orders, columnOrder) -> orders + ", " + columnOrder)
            .filter(not(String::isBlank))
            .map(orders -> " ORDER BY " + orders)
            .orElse("");
    }


    static class ProductRowMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Product(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getBigDecimal("price"),
                rs.getBoolean("available")
            );
        }
    }
}
