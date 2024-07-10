package com.krutov.romashka.co.dao;

import java.util.List;

public interface DocumentDao <T> {

    long create(T document);

    void update(long id, T updateDocument);

    void delete(long id);

    T findById(long id);

    List<T> findByProductId(long productId);
}
