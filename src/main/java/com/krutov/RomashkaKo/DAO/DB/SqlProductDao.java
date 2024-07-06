package com.krutov.RomashkaKo.DAO.DB;

import com.krutov.RomashkaKo.DAO.ProductDao;
import com.krutov.RomashkaKo.Model.Product;
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
        jdbc.update(sql,param);
    }


    @Override
    public Product getById(long id) {

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", id);

        String sql = """
                SELECT * FROM products
                WHERE id = :id
                """;

        List<Product> result = jdbc.query(sql,param, rowMapper);

        return !result.isEmpty() ? result.get(0) : null;
    }

    @Override
    public List<Product> getAllProducts() {

        String sql = """
                SELECT * FROM products
                """;

        return jdbc.query(sql, rowMapper);
    }


    static class ProductRowMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Product(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getBoolean("available")
            );
        }
    }
}
