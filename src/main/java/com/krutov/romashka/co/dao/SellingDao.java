package com.krutov.romashka.co.dao;

import com.krutov.romashka.co.model.Selling;

import java.util.List;

public interface SellingDao {

    long create(Selling selling);

    void update(long id, Selling updateSelling);

    void delete(long id);

    Selling getById(long id);

    List<Selling> getAll();
}
