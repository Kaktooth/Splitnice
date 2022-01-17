package com.example.splitwise.utils;

import java.util.List;

public interface Pageble<T> {

    List<T> getCurrentPageContent(int page, int size);

    List<Integer> getPageNumbers(int pageCount);

    int getPageCount(int pageSize);
}
