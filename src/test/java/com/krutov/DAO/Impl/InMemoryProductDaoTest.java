package com.krutov.DAO.Impl;

import com.krutov.Model.Product;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

class InMemoryProductDaoTest {

    @Test
    void create() {
        //Arrange
        InMemoryProductDao productDao = new InMemoryProductDao();

        Product productExpected = new Product(1L,"Jacoco","first product", 1.0d, false);

        //Act
        long id = productDao.create("Jacoco","first product", 1.0d, false);
        Product actual = productDao.products.get(id);

        //Assert
        Assertions.assertEquals(productExpected, actual);
    }

    @Test
    void update() {
        //Arrange
        InMemoryProductDao productDao = new InMemoryProductDao();
        Product productExpected = new Product(1L,"JacocoUpdated","first product", 1.0d, false);
        long id = productDao.create("Jacoco","first product", 1.0d, false);

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
        long id = productDao.create("Jacoco","first product", 1.0d, false);

        //Act
        productDao.delete(id);

        //Assert
        Assertions.assertTrue(productDao.products.isEmpty());
    }

    @Test
    void getById() {
        //Arrange
        InMemoryProductDao productDao = new InMemoryProductDao();

        Product productExpected = new Product(2L,"JacocoTwo","second product", 2.0d, true);

        //Act
        productDao.create("Jacoco","first product", 1.0d, false);
        long id =productDao.create("JacocoTwo","second product", 2.0d, true);
        productDao.create("JacocoThree","third product", 3.0d, true);

        Product actual = productDao.getById(id);

        //Assert
        Assertions.assertEquals(productExpected, actual);
    }

    @Test
    void getAllProducts() {
        //Arrange
        InMemoryProductDao productDao = new InMemoryProductDao();

        List<Product> expectedList = new ArrayList<>();
        Product product1 = new Product(1L,"Jacoco","first product", 1.0d, false);
        Product product2 = new Product(2L,"JacocoTwo","second product", 2.0d, true);
        Product product3 = new Product(3L,"JacocoThree","third product", 3.0d, true);

        expectedList.add(product1);
        expectedList.add(product2);
        expectedList.add(product3);

        //Act
        productDao.create("Jacoco","first product", 1.0d, false);
        productDao.create("JacocoTwo","second product", 2.0d, true);
        productDao.create("JacocoThree","third product", 3.0d, true);

        List<Product> actualList = productDao.getAllProducts();

        //Assert
        Assertions.assertEquals(expectedList, actualList);

    }
}