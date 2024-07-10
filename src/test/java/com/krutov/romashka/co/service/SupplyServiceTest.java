package com.krutov.romashka.co.service;

import com.krutov.dao.Impl.InMemoryProductDao;
import com.krutov.dao.Impl.InMemorySellingDao;
import com.krutov.dao.Impl.InMemorySupplyDao;
import com.krutov.romashka.co.model.Product;
import com.krutov.romashka.co.model.Selling;
import com.krutov.romashka.co.model.Supply;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class SupplyServiceTest {

    InMemoryProductDao productDao = new InMemoryProductDao();
    InMemorySupplyDao supplyDao = new InMemorySupplyDao();
    InMemorySellingDao sellingDao = new InMemorySellingDao();
    ProductService productService = new ProductService(productDao);

    SupplyService supplyService = new SupplyService(supplyDao, sellingDao, productService);

    @BeforeEach
    void setup() {
        productDao.clear();
        supplyDao.clear();
        sellingDao.clear();
    }

    @Test
    void create__when_product_exists_and_supply_does_not_exists() {
       // Arrange
        Product product = Instancio.create(Product.class);
        long productId = productService.create(product);
        Supply supply = Instancio.create(Supply.class).withProductId(productId);

        // Act
        long id = supplyService.create(supply);
        Supply actual = supplyService.findById(id);

        // Assert
        assertThat(actual)
            .isEqualTo(supply.withId(id));
    }

    @Test
    void update__when_supply_exists_and_available_changed() {
        // Arrange
        Product ownOldProduct = Instancio.create(Product.class).withAvailable(false);
        long ownProductId = productService.create(ownOldProduct);

        Supply supply = Instancio.create(Supply.class).toBuilder().productId(ownProductId).amount(10L).build();
        long supplyId = supplyService.create(supply);

        ownOldProduct = productService.findById(ownProductId);

        Selling selling = Instancio.create(Selling.class).toBuilder().productId(ownProductId).amount(5L).build();
        sellingDao.create(selling);

        // Act
        supplyService.update(supplyId, supply.toBuilder()
            .amount(5L)
            .build());
        Supply actualSupply = supplyService.findById(supplyId);
        boolean actualAvailable = productService.findById(ownProductId).getAvailable();

        // Assert
        assertThat(ownOldProduct.getAvailable())
            .isTrue();

        assertThat(actualSupply)
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(supply.withAmount(5L));

        assertThat(actualAvailable)
            .isFalse();
    }

    @Test
    void delete__when_available_changed() {
        // Arrange
        Product product = Instancio.create(Product.class).withAvailable(true);
        long productId = productService.create(product);

        Supply supply = Instancio.create(Supply.class).toBuilder().productId(productId).amount(10L).build();
        long supplyId = supplyService.create(supply);

        Selling selling = Instancio.create(Selling.class).toBuilder().productId(productId).amount(5L).build();
        sellingDao.create(selling);

        // Act
        supplyService.delete(supplyId);
        Supply actualSupply = supplyService.findById(supply.getId());
        boolean actualAvailable = productService.findById(productId).getAvailable();

        // Assert
        assertThat(actualSupply).isNull();
        assertThat(actualAvailable).isFalse();
    }

    @Test
    void findById() {
        // Arrange
        Product product = Instancio.create(Product.class);
        long productId = productService.create(product);
        Supply supply = Instancio.create(Supply.class).withProductId(productId);
        long supplyId = supplyService.create(supply);

        // Act
        Supply actual = supplyService.findById(supplyId);

        // Assert
        assertThat(actual)
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(supply);
    }

    @Test
    void findByProductId() {
        // Arrange
        Product product = Instancio.create(Product.class);
        long productId = productService.create(product);
        Supply supply1 = Instancio.create(Supply.class).withProductId(productId);
        Supply supply2 = Instancio.create(Supply.class).withProductId(productId);
        supplyService.create(supply1);
        supplyService.create(supply2);

        // Act
        List<Supply> actual = supplyService.findByProductId(productId);

        // Assert
        assertThat(actual)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .containsOnly(supply1, supply2);
    }
}
