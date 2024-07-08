package com.krutov.romashka.co.dao.DB;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PagingSortingUtil {
    private PagingSortingUtil() {

    }
    public static String addSortingAndPaging(String query,
                                             ListData listData) {
        return addSortingAndPaging(query, listData, Map.of());
    }
    /**
     * @param query            исходный запрос
     * @param listData         listData
     * @param uiDbFieldMapping Маппинг полей для сортировки UI-DB
     * @return "... ORDER BY ... ASC, ... DESC LIMIT :limit OFFSET :offset"
     */
    public static String addSortingAndPaging(String query,
                                             ListData listData,
                                             Map<String, String> uiDbFieldMapping) {
        StringBuilder newQuery = new StringBuilder()
                .append(query)
                .append(PagingSortingUtil.toSortingQuery(listData, uiDbFieldMapping))
                .append(" ");

        if (listData.getLimit() > 0) {
            newQuery.append("limit ").append(listData.getLimit()).append(" ");
        }

        if (listData.getOffset() > 0) {
            newQuery.append("offset ").append(listData.getOffset()).append(" ");
        }
        return newQuery.toString();
    }

    private static String toSortingQuery(ListData listData, Map<String, String> uiDbFieldMapping) {
        List<SortData> sortData = listData.getSortData();
        if (sortData.isEmpty()) {
            return "";
        }
        return " ORDER BY " + sortData
                .stream()
                .map(data -> uiDbFieldMapping.getOrDefault(data.getField(), data.getField()) + " " + data.getSortDirection())
                .collect(Collectors.joining(", "));
    }
}
