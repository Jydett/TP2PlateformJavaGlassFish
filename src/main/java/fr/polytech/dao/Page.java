package fr.polytech.dao;

import java.util.List;

public interface Page<T extends Identifiable> {
    List<T> getList();
    int getPageNumber();
    int getPageSize();
    long getTotalResult();
    int getNumberOfPages();
}
