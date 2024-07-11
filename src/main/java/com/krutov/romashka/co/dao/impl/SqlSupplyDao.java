package com.krutov.romashka.co.dao.impl;

import com.krutov.romashka.co.dao.DocumentDao;
import com.krutov.romashka.co.model.Supply;
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
public class SqlSupplyDao implements DocumentDao <Supply> {

    private final NamedParameterJdbcOperations jdbc;
    private final RowMapper<com.krutov.romashka.co.model.Supply> rowMapper = new SupplyRowMapper();

    @Override
    public long create(Supply supply) {
        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("name", supply.getName())
            .addValue("product_id", supply.getProductId())
            .addValue("amount", supply.getAmount());

        String sql = """
            INSERT INTO supplies (name, product_id, amount)
            VALUES (:name,:product_id,:amount)
            RETURNING id
            """;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(sql, params, keyHolder);

        return keyHolder.getKeyAs(Long.class);
    }

    @Override
    public void update(long id, Supply updateSupply) {

        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("id", id)
            .addValue("name", updateSupply.getName())
            .addValue("product_id", updateSupply.getProductId())
            .addValue("amount", updateSupply.getAmount());

        String sql = """
            UPDATE supplies
            SET name = :name, product_id = :product_id, amount = :amount
            WHERE id = :id
            """;

        jdbc.update(sql, params);
    }

    @Override
    public void delete(long id) {

        SqlParameterSource param = new MapSqlParameterSource()
            .addValue("id", id);

        String sql = """
            DELETE FROM supplies
            WHERE id = :id
            """;
        jdbc.update(sql, param);
    }

    @Override
    public Supply findById(long id) {

        SqlParameterSource param = new MapSqlParameterSource()
            .addValue("id", id);

        String sql = """
            SELECT * FROM supplies
            WHERE id = :id
            """;

        List<com.krutov.romashka.co.model.Supply> result = jdbc.query(sql, param, rowMapper);

        return !result.isEmpty() ? result.get(0) : null;
    }

    @Override
    public List<Supply> findByProductId(long productId) {

        Map<String, Long> params = Map.of("productId", productId);

        String sql = """
            SELECT * FROM supplies WHERE product_id = :productId""";

        return jdbc.query(sql, params, rowMapper);
    }

    public static class SupplyRowMapper implements RowMapper<com.krutov.romashka.co.model.Supply> {
        @Override
        public Supply mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Supply(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getLong("product_id"),
                rs.getLong("amount")
            );
        }
    }
}
