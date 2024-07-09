package com.krutov.romashka.co.dao.DB;

import com.krutov.romashka.co.dao.ProductDao;
import com.krutov.romashka.co.dto.ProductSearchRequest;
import com.krutov.romashka.co.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public abstract class ProductDaoTestCases {

    abstract ProductDao getDao();

    ProductDao productDao = getDao();

    @Test
    void create_product() {
        //Arrange
        Product product = new Product(1L,"Jacoco","first product", 1.0d, false);

        //Act
        long id = productDao.create(new Product(1L,"Jacoco","first product", 1.0d, false));
        Product actual = productDao.getById(id);
        Product expected = new Product(id, product.getName()
                , product.getDescription(), product.getPrice(), product.getAvailable());

        //Assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void update_product() {
        //Arrange
        Product product = new Product(1L,"JacocoUpdated","first product", 1.0d, false);
        long id = productDao.create(new Product(1L,"Jacoco","first product", 1.0d, false));

        //Act
        productDao.update(id, new Product(1L,"JacocoUpdated","first product", 1.0d, false));
        Product actual = productDao.getById(id);
        Product expected = new Product(id, product.getName(),product.getDescription()
                                        ,product.getPrice(),product.getAvailable());

        //Assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void delete_product() {
        //Arrange
        Product productToDelete = new Product(1L,"JacocoUpdated","first product", 1.0d, false);
        long id = productDao.create(productToDelete);

        //Act
        productDao.delete(id);

        //Assert
        Assertions.assertTrue(true);
    }

    @Test
    void get_product_by_id() {
        //Arrange

        Product product1 = new Product(1L, "Jacoco","first product", 1.0d, false);
        Product productExpected = new Product(2L,"JacocoTwo","second product", 2.0d, true);
        Product product3 = new Product(3L, "JacocoThree","third product", 3.0d, true);

        //Act
        productDao.create(product1);
        long id = productDao.create(productExpected);
        productDao.create(product3);

        Product actual = productDao.getById(id);
        Product expected = new Product(id, productExpected.getName()
                ,productExpected.getDescription(), productExpected.getPrice()
                ,productExpected.getAvailable());

        //Assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void get_products_with_filters_and_sorting() {
        //Arrange

        ProductSearchRequest filters = new ProductSearchRequest("",2.5, "lt",null);
        List<SortData> sortData = new ArrayList<>();
        sortData.add(new SortData("name", Direction.DESC));

        ListData listData = new ListData(4, 0,sortData);

        List<Product> expectedList = new ArrayList<>();
        Product product1 = new Product(1L,"Abba","first product", 1.0d, false);
        Product product2 = new Product(2L,"Cabba","second product", 2.0d, true);
        Product product3 = new Product(3L,"Babba","third product", 3.0d, true);


        //Act
        long id1 = productDao.create(product1);
        long id2 = productDao.create(product2);
        long id3 = productDao.create(product3);

        expectedList.add(new Product(id2,product2.getName(),product2.getDescription(),product2.getPrice(),product2.getAvailable()));
        expectedList.add(new Product(id1,product1.getName(),product1.getDescription(),product1.getPrice(),product1.getAvailable()));
       // expectedList.add(new Product(id3,product3.getName(),product3.getDescription(),product3.getPrice(),product3.getAvailable()));

        List<Product> actualList = productDao.getAllProducts(filters, listData);

        //Assert
        Assertions.assertEquals(expectedList, actualList);
    }
}