package com.krutov.romashka.co.dao.impl;

import com.krutov.dao.Impl.InMemoryProductDao;
import com.krutov.romashka.co.IntegrationTests;
import com.krutov.romashka.co.dao.ProductDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Тесты прогоняются на две реализации: InMemDao и SqlDao
 */
public class ProductDaoTest extends IntegrationTests {

    @Autowired
    private ProductDao productDao;

    private final InMemoryProductDao inMemoryProductDao = new InMemoryProductDao();

    @Nested
    class SqlDaoTest extends ProductDaoTestCases {
        @BeforeEach
        @AfterEach
        void setup() {
            clearTables("products");
        }

        @Override
        ProductDao getDao() {
            return productDao;
        }
    }

    @Nested
    class InMemDaoTest extends ProductDaoTestCases {
        @BeforeEach
        @AfterEach
        void setup() {
            inMemoryProductDao.clear();
        }

        @Override
        ProductDao getDao() {
            return inMemoryProductDao;
        }
    }
}
