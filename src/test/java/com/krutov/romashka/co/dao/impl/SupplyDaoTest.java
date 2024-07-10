package com.krutov.romashka.co.dao.impl;

import com.krutov.dao.Impl.InMemoryProductDao;
import com.krutov.dao.Impl.InMemorySupplyDao;
import com.krutov.romashka.co.IntegrationTests;
import com.krutov.romashka.co.dao.DocumentDao;
import com.krutov.romashka.co.dao.ProductDao;
import com.krutov.romashka.co.model.Supply;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Тесты прогоняются на две реализации: InMemDao и SqlDao
 */
public class SupplyDaoTest extends IntegrationTests {

    @Autowired
    private DocumentDao<Supply> supplyDao;

    @Autowired
    private ProductDao productDao;

    private final InMemorySupplyDao inMemorySuppleDao = new InMemorySupplyDao();
    private final InMemoryProductDao inMemoryProductDao = new InMemoryProductDao();

    @Nested
    class SqlDaoTest extends SupplyDaoTestCases {
        @BeforeEach
        @AfterEach
        void setup() {
            clearTables("sellings", "products");
        }

        @Override
        DocumentDao<Supply> getDao() {
            return supplyDao;
        }

        @Override
        ProductDao getProductDao() {
            return productDao;
        }
    }

    @Nested
    class InMemDaoTest extends SupplyDaoTestCases {
        @BeforeEach
        @AfterEach
        void setup() {
            inMemorySuppleDao.clear();
        }

        @Override
        DocumentDao<Supply> getDao() {
            return inMemorySuppleDao;
        }

        @Override
        ProductDao getProductDao() {
            return inMemoryProductDao;
        }
    }
}
