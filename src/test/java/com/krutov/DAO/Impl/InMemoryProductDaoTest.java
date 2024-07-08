package com.krutov.DAO.Impl;

import com.krutov.romashka.co.dao.DB.ListData;
import com.krutov.romashka.co.dao.DB.SortData;
import com.krutov.romashka.co.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class InMemoryProductDaoTest {

    @Test
    void create() {
        //Arrange
        InMemoryProductDao productDao = new InMemoryProductDao();

        Product productExpected = new Product(1L,"Jacoco","first product", 1.0d, false);

        //Act
        long id = productDao.create(new Product(1L,"Jacoco","first product", 1.0d, false));
        Product actual = productDao.products.get(id);

        //Assert
        Assertions.assertEquals(productExpected, actual);
    }

    @Test
    void update() {
        //Arrange
        InMemoryProductDao productDao = new InMemoryProductDao();
        Product productExpected = new Product(1L,"JacocoUpdated","first product", 1.0d, false);
        long id = productDao.create(new Product(1L,"Jacoco","first product", 1.0d, false));

        //Act
        productDao.update(id, new Product(1L,"JacocoUpdated","first product", 1.0d, false));
        Product actual = productDao.products.get(1L);

        //Assert
        Assertions.assertEquals(productExpected, actual);
    }

    @Test
    void delete() {
        //Arrange
        InMemoryProductDao productDao = new InMemoryProductDao();
        Product productToDelete = new Product(1L,"JacocoUpdated","first product", 1.0d, false);
        long id = productDao.create(productToDelete);

        //Act
        productDao.delete(id);

        //Assert
        Assertions.assertTrue(productDao.products.isEmpty());
    }

    @Test
    void getById() {
        //Arrange
        InMemoryProductDao productDao = new InMemoryProductDao();

        Product product1 = new Product(1L, "Jacoco","first product", 1.0d, false);
        Product productExpected = new Product(2L,"JacocoTwo","second product", 2.0d, true);
        Product product3 = new Product(3L, "JacocoThree","third product", 3.0d, true);

        //Act
        productDao.create(product1);
        long id =productDao.create(productExpected);
        productDao.create(product3);

        Product actual = productDao.getById(id);

        //Assert
        Assertions.assertEquals(productExpected, actual);
    }

    @Test
    void getAllProducts() {
        //Arrange
        InMemoryProductDao productDao = new InMemoryProductDao();

        List<SortData> sortData = new ArrayList<>();
        sortData.add(new SortData("name", "ASC"));

        ListData listData = new ListData(4, 1,sortData);

        List<Product> expectedList = new ArrayList<>();
        Product product1 = new Product(1L,"Jacoco","first product", 1.0d, false);
        Product product2 = new Product(2L,"JacocoTwo","second product", 2.0d, true);
        Product product3 = new Product(3L,"JacocoThree","third product", 3.0d, true);

        expectedList.add(product1);
        expectedList.add(product2);
        expectedList.add(product3);

        //Act
        productDao.create(product1);
        productDao.create(product2);
        productDao.create(product3);

        List<Product> actualList = productDao.getAllProducts(listData,"");

        //Assert
        Assertions.assertEquals(expectedList, actualList);

    }
}