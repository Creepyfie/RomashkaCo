package com.krutov.dao.Impl;

import com.krutov.romashka.co.dao.DB.Direction;
import com.krutov.romashka.co.dao.DB.ListData;
import com.krutov.romashka.co.dao.DB.SortData;
import com.krutov.romashka.co.dao.ProductDao;
import com.krutov.romashka.co.dto.ProductSearchRequest;
import com.krutov.romashka.co.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class InMemoryProductDaoTest {

    abstract ProductDao getDao();

    InMemoryProductDao productDao = (InMemoryProductDao) getDao();

    @Test
    void create() {

        //Arrange
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
        ProductSearchRequest filters = new ProductSearchRequest("",2.5, "lt",true);

        List<SortData> sortData = new ArrayList<>();
        sortData.add(new SortData("name", Direction.DESC));

        ListData listData = new ListData(4, 0,sortData);

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

        List<Product> actualList = productDao.getAllProducts(filters, listData);

        //Assert
        Assertions.assertEquals(expectedList, actualList);
    }
}