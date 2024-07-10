package com.krutov.romashka.co.dao.impl;

import com.krutov.dao.Impl.InMemorySellingDao;
import com.krutov.romashka.co.IntegrationTests;
import com.krutov.romashka.co.dao.SellingDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Тесты прогоняются на две реализации: InMemDao и SqlDao
 */
public class SellingDaoTest extends IntegrationTests {

    @Autowired
    private SellingDao sellingDao;

    private final InMemorySellingDao inMemorySellingDao = new InMemorySellingDao();

    @Nested
    class SqlDaoTest extends SellingDaoTestCases {
        @BeforeEach
        @AfterEach
        void setup() {
            clearTables("supplies");
        }

        @Override
        SellingDao getDao() {
            return sellingDao;
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
        SellingDao getDao() {
            return inMemorySellingDao;
        }
    }
}
