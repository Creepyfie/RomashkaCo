package com.krutov.romashka.co.dao.impl;

import com.krutov.dao.Impl.InMemoryProductDao;
import com.krutov.dao.Impl.InMemorySellingDao;
import com.krutov.romashka.co.IntegrationTests;
import com.krutov.romashka.co.dao.DocumentDao;
import com.krutov.romashka.co.dao.ProductDao;
import com.krutov.romashka.co.model.Selling;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Map;

import static com.krutov.romashka.co.dao.impl.SqlSellingDao.*;

/**
 * Тесты прогоняются на две реализации: InMemDao и SqlDao
 */
public class SellingDaoTest extends IntegrationTests {

    @Autowired
    private DocumentDao<Selling> sellingDao;

    @Autowired
    private ProductDao productDao;

    private final InMemorySellingDao inMemorySellingDao = new InMemorySellingDao();
    private final InMemoryProductDao inMemoryProductDao = new InMemoryProductDao();
    private final RowMapper<Selling> rowMapper = new SellingRowMapper();

    @Nested
    class SqlDaoTest extends SellingDaoTestCases {
        @BeforeEach
        @AfterEach
        void setup() {
            clearTables("supplies", "products");
        }

        @Override
        DocumentDao<Selling> getDao() {
            return sellingDao;
        }

        @Override
        ProductDao getProductDao() {
            return productDao;
        }

        @Override
        List<Selling> findAll() {
            String sql = """
                SELECT * FROM sellings""";

            return jdbcOperations.query(sql, Map.of(), rowMapper);
        }
    }

    @Nested
    class InMemDaoTest extends SellingDaoTestCases {
        @BeforeEach
        @AfterEach
        void setup() {
            inMemorySellingDao.clear();
        }

        @Override
        DocumentDao<Selling> getDao() {
            return inMemorySellingDao;
        }

        @Override
        ProductDao getProductDao() {
            return inMemoryProductDao;
        }

        @Override
        List<Selling> findAll() {
            return inMemorySellingDao.findAll();
        }
    }
}
