package com.krutov.dao.Impl;

import com.krutov.romashka.co.dao.DocumentDao;
import com.krutov.romashka.co.model.Supply;
import org.instancio.Instancio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemorySupplyDao implements DocumentDao<Supply> {

    Map<Long, Supply> supplies = new HashMap<>();

    @Override
    public long create(Supply supply) {
        Long id = Instancio.create(Long.class);
        supplies.put(id, (supply.toBuilder().id(id).build()));
        return id;
    }


    public void update(long id, Supply updateSupply) {
        if (!supplies.containsKey(id)) {
            return;
        }
        supplies.put(id, (com.krutov.romashka.co.model.Supply) updateSupply);
    }


    public void delete(long id) {
        supplies.remove(id);
    }


    public Supply getById(long id) {
        return supplies.get(id);
    }


    public List<Supply> getAll() {
        return supplies.values().stream().toList();
    }

    public void clear() {
        supplies.clear();
    }
}
