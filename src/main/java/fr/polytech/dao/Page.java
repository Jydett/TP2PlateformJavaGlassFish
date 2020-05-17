package fr.polytech.dao;

import java.util.List;

public interface Page<T> {
    List<T> getList();
    int getPageNumber();
    int getPageSize();
    long getTotalResult();
    int getNumberOfPages();
}
