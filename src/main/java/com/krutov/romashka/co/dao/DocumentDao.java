package com.krutov.romashka.co.dao;

import com.krutov.romashka.co.model.Supply;
import com.krutov.romashka.co.model.documents.Document;

import java.util.List;

public interface DocumentDao {

    long create(Document document);

    void update(long id, Document updateDaocument);

    void delete(long id);

    Document getById(long id);

    List<? extends Document> getAll();
}
