package com.krutov.romashka.co.dao.DB;

import lombok.Value;

import java.util.List;

@Value
public class ListData {
    int limit;
    int offset;
    List<SortData> sortData;
}
