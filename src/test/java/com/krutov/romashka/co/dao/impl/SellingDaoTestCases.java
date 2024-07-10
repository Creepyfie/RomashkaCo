package com.krutov.romashka.co.dao.impl;

import com.krutov.romashka.co.dao.ProductDao;
import com.krutov.romashka.co.dao.SellingDao;
import com.krutov.romashka.co.model.Product;
import com.krutov.romashka.co.model.Selling;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class SellingDaoTestCases {

    abstract SellingDao getDao();

    private SellingDao sellingDao = getDao();

    abstract ProductDao getProductDao();

    private ProductDao productDao = getProductDao();

    @Test
    void create_supply() {
        //Arrange
        long productId = productDao.create(Instancio.create(Product.class));
        Selling expected = Instancio.create(Selling.class).toBuilder().productId(productId).build();

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
        long productId = productDao.create(Instancio.create(Product.class));
        Selling oldProduct = Instancio.create(Selling.class).toBuilder().productId(productId).build();
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
    void delete_supply() {
        //Arrange
        long productId = productDao.create(Instancio.create(Product.class));

        Selling sellingToDelete = Instancio.create(Selling.class).toBuilder().productId(productId).build();
        Selling remainingSelling = Instancio.create(Selling.class).toBuilder().productId(productId).build();
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
        long productId = productDao.create(Instancio.create(Product.class));

        Selling ownSelling = Instancio.create(Selling.class).toBuilder().productId(productId).build();
        Selling otherSelling = Instancio.create(Selling.class).toBuilder().productId(productId).build();
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
        long productId = productDao.create(Instancio.create(Product.class));

        Selling selling1 = Instancio.create(Selling.class).toBuilder().name("ownName1").productId(productId).build();
        Selling selling2 = Instancio.create(Selling.class).toBuilder().name("ownName2").productId(productId).build();
        Selling selling3 = Instancio.create(Selling.class).toBuilder().name("ownName3").productId(productId).build();
        Selling selling4 = Instancio.create(Selling.class).toBuilder().name("ownName4").productId(productId).build();
        Selling selling5 = Instancio.create(Selling.class).toBuilder().name("ownName5").productId(productId).build();

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
