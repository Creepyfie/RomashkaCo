package com.krutov.romashka.co.dao;

import com.krutov.romashka.co.model.Document;
import com.krutov.romashka.co.model.Selling;

import java.util.List;

public interface DocumentDao <T> {

    long create(T document);

    void update(long id, T updateDocument);

    void delete(long id);

    T getById(long id);

    List<T> getAll();
}
