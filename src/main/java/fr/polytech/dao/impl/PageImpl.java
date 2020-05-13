package fr.polytech.dao.impl;

import fr.polytech.dao.Identifiable;
import fr.polytech.dao.Page;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PageImpl<T extends Identifiable> implements Page<T> {
    private List<T> list;
    private int pageSize;
    private int pageNumber;
    private long totalResult;
    private int numberOfPages;
}
