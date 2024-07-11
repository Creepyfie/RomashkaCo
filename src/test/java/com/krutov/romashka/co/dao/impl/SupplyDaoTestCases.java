package com.krutov.romashka.co.dao.impl;

import com.krutov.romashka.co.dao.DocumentDao;
import com.krutov.romashka.co.dao.ProductDao;
import com.krutov.romashka.co.model.Product;
import com.krutov.romashka.co.model.Supply;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class SupplyDaoTestCases {

    abstract DocumentDao<Supply> getDao();

    abstract ProductDao getProductDao();

    abstract List<Supply> findAll();

    private final DocumentDao<Supply> supplyDao = getDao();
    private final ProductDao productDao = getProductDao();

    @Test
    void create_supply() {
        //Arrange
        long productId = productDao.create(Instancio.create(Product.class));
        Supply expected = Instancio.create(Supply.class).toBuilder().productId(productId).build();

        //Act
        supplyDao.create(expected);
        List<Supply> actual = findAll();

        //Assert
        assertThat(actual)
            .hasSize(1)
            .first()
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(expected);
    }

    @Test
    void update_supply() {
        //Arrange
        long productId = productDao.create(Instancio.create(Product.class));
        Supply oldProduct = Instancio.create(Supply.class).toBuilder().productId(productId).build();
        long id = supplyDao.create(oldProduct);

        //Act
        Supply updatedSupply = oldProduct.toBuilder()
            .name("updatedName")
            .build();

        supplyDao.update(id, updatedSupply);
        List<Supply> actual = findAll();

        //Assert
        assertThat(actual)
            .hasSize(1)
            .first()
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(updatedSupply);
    }

    @Test
    void delete_product() {
        //Arrange
        long productId = productDao.create(Instancio.create(Product.class));


        Supply supplyToDelete = Instancio.create(Supply.class).toBuilder().productId(productId).build();
        Supply remainingSuply = Instancio.create(Supply.class).toBuilder().productId(productId).build();
        long idToDelete = supplyDao.create(supplyToDelete);
        supplyDao.create(remainingSuply);

        //Act
        supplyDao.delete(idToDelete);
        List<Supply> actual = findAll();

        //Assert
        assertThat(actual)
            .hasSize(1)
            .first()
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(remainingSuply);
    }

    @Test
    void get_supply_by_id() {
        //Arrange
        long productId = productDao.create(Instancio.create(Product.class));

        Supply ownSupply = Instancio.create(Supply.class).toBuilder().productId(productId).build();
        Supply otherSupply = Instancio.create(Supply.class).toBuilder().productId(productId).build();
        long ownId = supplyDao.create(ownSupply);
        supplyDao.create(otherSupply);

        //Act
        Supply actual = supplyDao.findById(ownId);

        //Assert
        assertThat(actual)
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(ownSupply);
    }
}
