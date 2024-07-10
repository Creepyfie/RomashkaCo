package com.krutov.dao.Impl;

import com.krutov.romashka.co.dao.SellingDao;
import com.krutov.romashka.co.model.Selling;
import org.instancio.Instancio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemorySellingDao implements SellingDao {

    Map<Long, Selling> sellings = new HashMap<>();

    @Override
    public long create(Selling selling) {
        Long id = Instancio.create(Long.class);
        sellings.put(id, (selling.toBuilder().id(id).build()));
        return id;
    }

    @Override
    public void update(long id, Selling updateSelling) {
        if (!sellings.containsKey(id)) {
            return;
        }
        sellings.put(id, updateSelling);
    }

    @Override
    public void delete(long id) {
        sellings.remove(id);
    }

    @Override
    public Selling getById(long id) {
        return sellings.get(id);
    }

    @Override
    public List<Selling> getAll() {
        return sellings.values().stream().toList();
    }

    public void clear() {
        sellings.clear();
    }
}
