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

    @Override
    public void update(long id, Supply updateSupply) {
        if (!supplies.containsKey(id)) {
            return;
        }
        supplies.put(id, (com.krutov.romashka.co.model.Supply) updateSupply);
    }

    @Override
    public void delete(long id) {
        supplies.remove(id);
    }

    @Override
    public Supply findById(long id) {
        return supplies.get(id);
    }

    @Override
    public List<Supply> findByProductId(long productId) {
        return supplies.values()
            .stream()
            .filter(it -> productId == it.getProductId())
            .toList();
    }

    public List<Supply> findAll() {
        return List.copyOf(supplies.values());
    }

    public void clear() {
        supplies.clear();
    }
}
