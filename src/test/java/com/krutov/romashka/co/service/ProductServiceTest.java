package com.krutov.romashka.co.service;

import com.krutov.dao.Impl.InMemoryProductDao;
import com.krutov.romashka.co.controller.dto.ProductSearchRequest;
import com.krutov.romashka.co.model.Product;
import com.krutov.romashka.co.util.ListData;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductServiceTest {

    InMemoryProductDao productDao = new InMemoryProductDao();
    ProductService productService = new ProductService(productDao);
    ProductSearcher productSearcher = new ProductSearcher(productDao);

    @Test
    void create__when_does_not_exist() {
        // Arrange
        Product expectedProduct = Instancio.create(Product.class);


        // Act
        long productId = productService.create(expectedProduct);

        List<Product> actual = productSearcher.search(new ProductSearchRequest(), limitMax());

        // Assert

        assertThat(actual)
            .hasSize(1)
            .first()
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(expectedProduct);
    }

    @Test
    void update() {
        // Arrange
        Product oldProduct = Instancio.create(Product.class);
        long productId = productService.create(oldProduct);

        // Act
        Product newProduct = oldProduct.withName("updatedName");
        productService.update(productId, newProduct);

        Product actualProduct = productService.findById(productId);

        // Assert
        assertThat(actualProduct)
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(newProduct);
    }

    @Test
    void delete() {
        // Arrange
        Product product = Instancio.create(Product.class);
        long productId = productService.create(product);

        // Act
        productService.delete(productId);
        Product actualProduct = productService.findById(productId);

        //Assert
        assertThat(actualProduct).isNull();
    }

    @Test
    void findById() {
        //Arrange
        Product productToFind = Instancio.create(Product.class);
        Product otherProduct = Instancio.create(Product.class);

        long productId = productService.create(productToFind);
        productService.create(otherProduct);

        //Act
        Product actual = productService.findById(productId);

        //Assert
        assertThat(actual)
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(productToFind);
    }
    private static ListData limitMax() {
        return new ListData(Integer.MAX_VALUE, 0, List.of());
    }

}
