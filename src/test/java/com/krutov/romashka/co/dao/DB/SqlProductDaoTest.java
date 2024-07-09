package com.krutov.romashka.co.dao.DB;

import com.krutov.romashka.co.RomashkaCoApplicationTests;
import com.krutov.romashka.co.dao.ProductDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;

public class SqlProductDaoTest extends RomashkaCoApplicationTests {

        @Autowired
        private ProductDao productDao;

    @Nested
    class SqlDaoTest extends SqlProductDaoTestCases{
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
}
