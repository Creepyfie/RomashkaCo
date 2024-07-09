package com.krutov.romashka.co.dao.DB;

import com.krutov.dao.Impl.InMemoryProductDao;
import com.krutov.romashka.co.IntegrationTests;
import com.krutov.romashka.co.dao.ProductDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;

public class SqlProductDaoTest extends IntegrationTests {

        @Autowired
        private ProductDao productDao;

        private InMemoryProductDao inMemoryProductDao;

    @Nested
    class SqlDaoTest extends ProductDaoTestCases {
        @BeforeEach
        @AfterEach
        void setup() {
            clearTables("products");
        }
        @Override
        ProductDao getDao() {
            return SqlProductDaoTest.this.productDao;
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
            return SqlProductDaoTest.this.inMemoryProductDao;
        }
    }
}
