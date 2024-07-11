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

import static org.assertj.core.api.Assertions.assertThat;

class SellingServiceTest {
    InMemoryProductDao productDao = new InMemoryProductDao();
    InMemorySupplyDao supplyDao = new InMemorySupplyDao();
    InMemorySellingDao sellingDao = new InMemorySellingDao();
    ProductService productService = new ProductService(productDao);

    SellingService sellingService = new SellingService(supplyDao, sellingDao, productService);

    @BeforeEach
    void setup() {
        productDao.clear();
        supplyDao.clear();
        sellingDao.clear();
    }


    @Test
    void create__when_product_exists_and_selling_does_not_exists() {
        // Arrange
        Product product = Instancio.create(Product.class).withAvailable(true);
        long productId = productService.create(product);

        Supply supply = Instancio.create(Supply.class).toBuilder().productId(productId).amount(100L).build();
        supplyDao.create(supply);

        Selling selling = Instancio.create(Selling.class).withProductId(productId).withAmount(10L);

        // Act
        long id = sellingService.create(selling);
        Selling actual = sellingService.findById(id);

        // Assert
        assertThat(actual)
            .isEqualTo(selling.withId(id));
    }

    @Test
    void update__when_selling_exists_and_available_changed() {
        // Arrange
        Product ownOldProduct = Instancio.create(Product.class).withAvailable(true);
        long ownProductId = productService.create(ownOldProduct);

        Supply supply = Instancio.create(Supply.class).toBuilder().productId(ownProductId).amount(5L).build();
        supplyDao.create(supply);

        Selling selling = Instancio.create(Selling.class).toBuilder().productId(ownProductId).amount(3L).build();
        long sellingId = sellingService.create(selling);

        ownOldProduct = productService.findById(ownProductId);


        // Act
        sellingService.update(sellingId, selling.toBuilder()
            .amount(5L)
            .build());
        Selling actualSupply = sellingService.findById(sellingId);
        boolean actualAvailable = productService.findById(ownProductId).getAvailable();

        // Assert
        assertThat(ownOldProduct.getAvailable())
            .isTrue();

        assertThat(actualSupply)
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(selling.withAmount(5L));

        assertThat(actualAvailable)
            .isFalse();
    }

    @Test
    void delete__when_available_changed() {
        // Arrange
        Product product = Instancio.create(Product.class).withAvailable(true);
        long productId = productService.create(product);

        Supply supply = Instancio.create(Supply.class).toBuilder().productId(productId).amount(5L).build();
        supplyDao.create(supply);

        Selling selling = Instancio.create(Selling.class).toBuilder().productId(productId).amount(5L).build();
        long sellingId = sellingDao.create(selling);

        // Act
        sellingService.delete(sellingId);
        Selling actualSelling = sellingService.findById(supply.getId());
        boolean actualAvailable = productService.findById(productId).getAvailable();

        // Assert
        assertThat(actualSelling).isNull();
        assertThat(actualAvailable).isTrue();
    }

    @Test
    void findById() {
        // Arrange
        Product product = Instancio.create(Product.class);
        long productId = productService.create(product);
        Supply supply = Instancio.create(Supply.class).withProductId(productId).withAmount(2L);
        supplyDao.create(supply);

        Selling selling = Instancio.create(Selling.class).withProductId(productId).withAmount(1L);
        long sellingId = sellingService.create(selling);
        // Act
        Selling actual = sellingService.findById(sellingId);

        // Assert
        assertThat(actual)
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(selling);
    }

    @Test
    void findByProductId() {
        // Arrange
        Product product = Instancio.create(Product.class);
        long productId = productService.create(product);
        Supply supply = Instancio.create(Supply.class).withProductId(productId).withAmount(10L);
        supplyDao.create(supply);

        Selling selling1 = Instancio.create(Selling.class).withAmount(1L).withProductId(productId);
        Selling selling2 = Instancio.create(Selling.class).withAmount(2L).withProductId(productId);

        sellingService.create(selling1);
        sellingService.create(selling2);


        // Act
        List<Selling> actual = sellingService.findByProductId(productId);

        // Assert
        assertThat(actual)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .containsOnly(selling1, selling2);
    }
}
