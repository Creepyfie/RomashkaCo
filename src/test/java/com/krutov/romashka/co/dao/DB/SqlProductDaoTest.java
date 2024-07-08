package com.krutov.romashka.co.dao.DB;

import com.krutov.DAO.Impl.InMemoryProductDao;
import com.krutov.romashka.co.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class SqlProductDaoTest {

    private SqlProductDao productDao;

    @Test
    void create() {
        //Arrange
        Product productExpected = new Product(1L,"Jacoco","first product", 1.0d, false);

        //Act
        long id = productDao.create(new Product(1L,"Jacoco","first product", 1.0d, false));
        Product actual = productDao.getById(id);

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
        Product actual = productDao.getById(id);

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
        Assertions.assertTrue(true);
    }

    @Test
    void getById() {
        //Arrange

        Product product1 = new Product(1L, "Jacoco","first product", 1.0d, false);
        Product productExpected = new Product(2L,"JacocoTwo","second product", 2.0d, true);
        Product product3 = new Product(3L, "JacocoThree","third product", 3.0d, true);

        //Act
        productDao.create(product1);
        long id = productDao.create(productExpected);
        productDao.create(product3);

        Product actual = productDao.getById(id);

        //Assert
        Assertions.assertEquals(productExpected, actual);
    }

    @Test
    void getAllProducts() {
        //Arrange

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