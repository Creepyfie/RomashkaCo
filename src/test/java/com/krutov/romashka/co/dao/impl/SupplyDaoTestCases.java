package com.krutov.romashka.co.dao.impl;

import com.krutov.romashka.co.dao.SupplyDao;
import com.krutov.romashka.co.model.Supply;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class SupplyDaoTestCases {

    abstract SupplyDao getDao();

    private SupplyDao supplyDao = getDao();

    @Test
    void create_supply() {
        //Arrange
        Supply expected = Instancio.create(Supply.class);

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
        Supply oldProduct = Instancio.create(Supply.class);
        long id = supplyDao.create(oldProduct);

        //Act
        Supply updatedProduct = oldProduct.toBuilder()
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
        Supply supplyToDelete = Instancio.create(Supply.class);
        Supply remainingSuply = Instancio.create(Supply.class);
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
        Supply ownSupply = Instancio.create(Supply.class);
        Supply otherSupply = Instancio.create(Supply.class);
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
        Supply supply1 = Instancio.create(Supply.class).toBuilder().name("ownName1").build();
        Supply supply2 = Instancio.create(Supply.class).toBuilder().name("ownName2").build();
        Supply supply3 = Instancio.create(Supply.class).toBuilder().name("ownName3").build();
        Supply supply4 = Instancio.create(Supply.class).toBuilder().name("ownName4").build();
        Supply supply5 = Instancio.create(Supply.class).toBuilder().name("ownName5").build();

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
