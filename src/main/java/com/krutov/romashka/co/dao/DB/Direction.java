package com.krutov.romashka.co.dao.DB;

public enum Direction {
    ASC("ASC"),
    DESC("DESC");

    private String sortDirection;

    Direction(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getSortDirection() {
        return sortDirection;
    }
}
