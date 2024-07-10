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

    private DocumentDao<Supply> supplyDao = getDao();

    abstract ProductDao getProductDao();

    private ProductDao productDao = getProductDao();

    @Test
    void create_supply() {
        //Arrange
        long productId = productDao.create(Instancio.create(Product.class));
        Supply expected = Instancio.create(Supply.class).toBuilder().productId(productId).build();

        //Act
        supplyDao.create(expected);
        List<Supply> actual = supplyDao.getAll();

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

        Supply oldSupply = Instancio.create(Supply.class).toBuilder().productId(productId).build();
        long id = supplyDao.create(oldSupply);

        //Act
        Supply updatedProduct = oldSupply.toBuilder()
            .name("updatedName")
            .build();

        supplyDao.update(id, updatedProduct);
        List<Supply> actual = supplyDao.getAll();

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
        long productId = productDao.create(Instancio.create(Product.class));


        Supply supplyToDelete = Instancio.create(Supply.class).toBuilder().productId(productId).build();
        Supply remainingSuply = Instancio.create(Supply.class).toBuilder().productId(productId).build();
        long idToDelete = supplyDao.create(supplyToDelete);
        supplyDao.create(remainingSuply);

        //Act
        supplyDao.delete(idToDelete);
        List<Supply> actual = supplyDao.getAll();

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
        Supply actual = supplyDao.getById(ownId);

        //Assert
        assertThat(actual)
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(ownSupply);
    }

    @Test
    void get_all_supplies() {
        //Arrange
        long productId = productDao.create(Instancio.create(Product.class));

        Supply supply1 = Instancio.create(Supply.class).toBuilder().name("ownName1").productId(productId).build();
        Supply supply2 = Instancio.create(Supply.class).toBuilder().name("ownName2").productId(productId).build();
        Supply supply3 = Instancio.create(Supply.class).toBuilder().name("ownName3").productId(productId).build();
        Supply supply4 = Instancio.create(Supply.class).toBuilder().name("ownName4").productId(productId).build();
        Supply supply5 = Instancio.create(Supply.class).toBuilder().name("ownName5").productId(productId).build();

        supplyDao.create(supply1);
        supplyDao.create(supply2);
        supplyDao.create(supply3);
        supplyDao.create(supply4);
        supplyDao.create(supply5);

        //Act

        List<Supply> actual = supplyDao.getAll();

        //Assert
        assertThat(actual)
            .hasSize(5)
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(List.of(supply1, supply2, supply3, supply4, supply5));
    }
}
