package com.krutov.romashka.co.util;

public enum Direction {
    ASC("ASC"),
    DESC("DESC");

    private final String sortDirection;

    Direction(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getSortDirection() {
        return sortDirection;
    }
}
