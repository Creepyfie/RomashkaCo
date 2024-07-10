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
    }
}
