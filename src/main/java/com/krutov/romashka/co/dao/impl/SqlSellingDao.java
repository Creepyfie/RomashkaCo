package com.krutov.romashka.co.dao.impl;

import com.krutov.romashka.co.dao.DocumentDao;
import com.krutov.romashka.co.model.Selling;
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
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class SqlSellingDao implements DocumentDao <Selling> {

    private final RowMapper<Selling> rowMapper = new SellingRowMapper();
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public long create(Selling selling) {

        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("name", selling.getName())
            .addValue("product_id", selling.getProductId())
            .addValue("amount", selling.getAmount())
            .addValue("price", selling.getTotalPrice());

        String sql = """
            INSERT INTO sellings (name, product_id, amount, price)
            VALUES (:name,:product_id,:amount, :price)
            RETURNING id
            """;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(sql, params, keyHolder);

        return keyHolder.getKeyAs(Long.class);
    }

    @Override
    public void update(long id, Selling updateSelling) {

        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("id", id)
            .addValue("name", updateSelling.getName())
            .addValue("product_id", updateSelling.getProductId())
            .addValue("amount", updateSelling.getAmount())
            .addValue("price", updateSelling.getTotalPrice());

        String sql = """
            UPDATE sellings
            SET name = :name, product_id = :product_id, amount = :amount, price = :price
            WHERE id = :id
            """;

        jdbc.update(sql, params);

    }

    @Override
    public void delete(long id) {

        SqlParameterSource param = new MapSqlParameterSource()
            .addValue("id", id);

        String sql = """
            DELETE FROM sellings
            WHERE id = :id
            """;
        jdbc.update(sql, param);
    }

    @Override
    public Selling findById(long id) {

        SqlParameterSource param = new MapSqlParameterSource()
            .addValue("id", id);

        String sql = """
            SELECT * FROM sellings
            WHERE id = :id
            """;

        List<Selling> result = jdbc.query(sql, param, rowMapper);

        return !result.isEmpty() ? result.get(0) : null;
    }

    @Override
    public List<Selling> findByProductId(long productId) {

        Map<String, Long> params = Map.of("productId", productId);

        String sql = """
            SELECT * FROM sellings WHERE product_id = :productId""";

        return jdbc.query(sql, params, rowMapper);
    }

    public static class SellingRowMapper implements RowMapper<Selling> {
        @Override
        public Selling mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Selling(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getLong("product_id"),
                rs.getLong("amount"),
                rs.getBigDecimal("price")
            );
        }
    }
}

