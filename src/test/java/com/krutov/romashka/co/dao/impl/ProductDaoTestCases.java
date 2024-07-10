package com.krutov.romashka.co.dao.impl;

import com.krutov.romashka.co.dao.ProductDao;
import com.krutov.romashka.co.dto.ProductSearchRequest;
import com.krutov.romashka.co.model.Product;
import com.krutov.romashka.co.util.Direction;
import com.krutov.romashka.co.util.ListData;
import com.krutov.romashka.co.util.SortData;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.apache.commons.collections4.ListUtils.union;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class ProductDaoTestCases {

    abstract ProductDao getDao();

    private ProductDao productDao = getDao();

    @Test
    void create_product() {
        //Arrange
        Product expected = Instancio.create(Product.class);

        //Act
        productDao.create(expected);
        List<Product> actual = productDao.searchProduct(new ProductSearchRequest(), limitMax());

        //Assert
        assertThat(actual)
            .hasSize(1)
            .first()
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(expected);
    }

    @Test
    void update_product() {
        //Arrange
        Product oldProduct = Instancio.create(Product.class);
        long id = productDao.create(oldProduct);

        //Act
        Product updatedProduct = oldProduct.toBuilder()
            .name("Jacoca")
            .description("second product")
            .price(BigDecimal.valueOf(1000.0))
            .available(true)
            .build();

        productDao.update(id, updatedProduct);
        List<Product> actual = productDao.searchProduct(new ProductSearchRequest(), limitMax());

        //Assert
        assertThat(actual)
            .hasSize(1)
            .first()
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(updatedProduct);
    }

    @Test
    void delete_product() {
        //Arrange
        Product productToDelete = Instancio.create(Product.class);
        Product remainingProduct = Instancio.create(Product.class);
        long idToDelete = productDao.create(productToDelete);
        productDao.create(remainingProduct);

        //Act
        productDao.delete(idToDelete);
        List<Product> actual = productDao.searchProduct(new ProductSearchRequest(), limitMax());

        //Assert
        assertThat(actual)
            .hasSize(1)
            .first()
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(remainingProduct);
    }

    @Test
    void get_product_by_id() {
        //Arrange
        Product ownProduct = Instancio.create(Product.class);
        Product otherProduct = Instancio.create(Product.class);
        long ownId = productDao.create(ownProduct);
        productDao.create(otherProduct);

        //Act
        Product actual = productDao.getById(ownId);

        //Assert
        assertThat(actual)
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(ownProduct);
    }

    @Test
    void get_products_with_filters() {
        //Arrange
        Product ownProductWithHighPrice = Instancio.create(Product.class).toBuilder().name("ownName").price(BigDecimal.valueOf(3000.0)).available(true).build();
        Product ownProductWithLowPrice = Instancio.create(Product.class).toBuilder().name("ownName").price(BigDecimal.valueOf(500.0)).available(true).build();
        Product otherProductByPrice = Instancio.create(Product.class).toBuilder().name("ownName").price(BigDecimal.valueOf(1500.0)).available(true).build();
        Product otherProductByName = Instancio.create(Product.class).toBuilder().name("otherName").price(BigDecimal.valueOf(3000.0)).available(true).build();
        Product otherProductByAvailable = Instancio.create(Product.class).toBuilder().name("ownName").price(BigDecimal.valueOf(3000.0)).available(false).build();

        productDao.create(ownProductWithHighPrice);
        productDao.create(ownProductWithLowPrice);
        productDao.create(otherProductByPrice);
        productDao.create(otherProductByName);
        productDao.create(otherProductByAvailable);

        //Act
        ProductSearchRequest requestWithPriceGreaterThan = ProductSearchRequest.builder()
            .name("ownName")
            .price(BigDecimal.valueOf(2000.0))
            .priceSign("gt")
            .available(true)
            .build();

        ProductSearchRequest requestWithPriceLessThan = ProductSearchRequest.builder()
            .name("ownName")
            .price(BigDecimal.valueOf(1000.0))
            .priceSign("lt")
            .available(true)
            .build();

        List<Product> actual1 = productDao.searchProduct(requestWithPriceGreaterThan, limitMax());
        List<Product> actual2 = productDao.searchProduct(requestWithPriceLessThan, limitMax());

        //Assert
        assertThat(union(actual1, actual2))
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .containsOnly(ownProductWithHighPrice, ownProductWithLowPrice);
    }

    @Test
    void get_products_with_sorting_by_name_ASC() {
        //Arrange
        Product productA = Instancio.create(Product.class).toBuilder().name("AProduct").build();
        Product productB = Instancio.create(Product.class).toBuilder().name("BProduct").build();
        Product productC = Instancio.create(Product.class).toBuilder().name("CProduct").build();
        Product productD = Instancio.create(Product.class).toBuilder().name("DProduct").build();

        productDao.create(productB);
        productDao.create(productA);
        productDao.create(productD);
        productDao.create(productC);


        //Act
        ListData sortProductsByNameAsc = new ListData(Integer.MAX_VALUE
            , 0
            , List.of(new SortData("name", Direction.ASC)));

        List<Product> actual = productDao.searchProduct(new ProductSearchRequest(), sortProductsByNameAsc);

        //Assert
        assertThat(actual)
            .hasSize(4)
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(List.of(productA, productB, productC, productD));
    }

    @Test
    void get_products_with_sorting_by_price_DESC() {
        //Arrange
        Product productA = Instancio.create(Product.class).toBuilder().price(BigDecimal.valueOf(1000)).build();
        Product productB = Instancio.create(Product.class).toBuilder().price(BigDecimal.valueOf(100)).build();
        Product productC = Instancio.create(Product.class).toBuilder().price(BigDecimal.valueOf(10)).build();
        Product productD = Instancio.create(Product.class).toBuilder().price(BigDecimal.valueOf(1)).build();

        productDao.create(productD);
        productDao.create(productB);
        productDao.create(productA);
        productDao.create(productC);

        //Act
        ListData sortProductsByPriceDesc = new ListData(Integer.MAX_VALUE
            , 0
            , List.of(new SortData("price", Direction.DESC)));

        List<Product> actual = productDao.searchProduct(new ProductSearchRequest(), sortProductsByPriceDesc);

        //Assert
        assertThat(actual)
            .hasSize(4)
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(List.of(productA, productB, productC, productD));
    }

    @Test
    void get_products_with_paging() {
        //Arrange
        Product productA = Instancio.create(Product.class);
        Product productB = Instancio.create(Product.class);
        Product productC = Instancio.create(Product.class);
        Product productD = Instancio.create(Product.class);

        productDao.create(productA);
        productDao.create(productB);
        productDao.create(productC);
        productDao.create(productD);


        //Act
        int limit = 2;
        int offset = 1;

        ListData sortPaging = new ListData(limit
            , offset
            , List.of());

        List<Product> actual = productDao.searchProduct(new ProductSearchRequest(), sortPaging);

        //Assert
        assertThat(actual)
            .hasSize(2);
    }

    private static ListData limitMax() {
        return new ListData(Integer.MAX_VALUE, 0, List.of());
    }
}
