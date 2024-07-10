package com.krutov.romashka.co.dao.impl;

import com.krutov.dao.Impl.InMemorySupplyDao;
import com.krutov.romashka.co.IntegrationTests;
import com.krutov.romashka.co.dao.SupplyDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Тесты прогоняются на две реализации: InMemDao и SqlDao
 */
public class SupplyDaoTest extends IntegrationTests {

    @Autowired
    private SupplyDao supplyDao;

    private final InMemorySupplyDao inMemorySuppleDao = new InMemorySupplyDao();

    @Nested
    class SqlDaoTest extends SupplyDaoTestCases {
        @BeforeEach
        @AfterEach
        void setup() {
            clearTables("sellings");
        }

        @Override
        SupplyDao getDao() {
            return supplyDao;
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
        SupplyDao getDao() {
            return inMemorySuppleDao;
        }
    }
}
