package com.krutov.romashka.co.dao.impl;

import com.krutov.romashka.co.dao.SellingDao;
import com.krutov.romashka.co.model.Selling;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class SellingDaoTestCases {

    abstract SellingDao getDao();

    private SellingDao sellingDao = getDao();

    @Test
    void create_supply() {
        //Arrange
        Selling expected = Instancio.create(Selling.class);

        //Act
        sellingDao.create(expected);
        List<Selling> actual = sellingDao.getAll();

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
        Selling oldProduct = Instancio.create(Selling.class);
        long id = sellingDao.create(oldProduct);

        //Act
        Selling updatedProduct = oldProduct.toBuilder()
            .name("updatedName")
            .build();

        sellingDao.update(id, updatedProduct);
        List<Selling> actual = sellingDao.getAll();

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
        Selling sellingToDelete = Instancio.create(Selling.class);
        Selling remainingSelling = Instancio.create(Selling.class);
        long idToDelete = sellingDao.create(sellingToDelete);
        sellingDao.create(remainingSelling);

        //Act
        sellingDao.delete(idToDelete);
        List<Selling> actual = sellingDao.getAll();

        //Assert
        assertThat(actual)
            .hasSize(1)
            .first()
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(remainingSelling);
    }

    @Test
    void get_supply_by_id() {
        //Arrange
        Selling ownSelling = Instancio.create(Selling.class);
        Selling otherSelling = Instancio.create(Selling.class);
        long ownId = sellingDao.create(ownSelling);
        sellingDao.create(otherSelling);

        //Act
        Selling actual = sellingDao.getById(ownId);

        //Assert
        assertThat(actual)
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(ownSelling);
    }

    @Test
    void get_all_supplies() {
        //Arrange
        Selling selling1 = Instancio.create(Selling.class).toBuilder().name("ownName1").build();
        Selling selling2 = Instancio.create(Selling.class).toBuilder().name("ownName2").build();
        Selling selling3 = Instancio.create(Selling.class).toBuilder().name("ownName3").build();
        Selling selling4 = Instancio.create(Selling.class).toBuilder().name("ownName4").build();
        Selling selling5 = Instancio.create(Selling.class).toBuilder().name("ownName5").build();

        sellingDao.create(selling1);
        sellingDao.create(selling2);
        sellingDao.create(selling3);
        sellingDao.create(selling4);
        sellingDao.create(selling5);

        //Act

        List<Selling> actual = sellingDao.getAll();

        //Assert
        assertThat(actual)
            .hasSize(5)
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(List.of(selling1, selling2, selling3, selling4, selling5));
    }
}
