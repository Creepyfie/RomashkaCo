package com.krutov.romashka.co.dao;

import com.krutov.romashka.co.model.Supply;

import java.util.List;

public interface SupplyDao {

    long create(Supply supply);

    void update(long id, Supply updateDaocument);

    void delete(long id);

    Supply getById(long id);

    List<Supply> getAll();
}
