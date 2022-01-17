package com.example.splitwise.utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Pagination<T> implements Pageble<T> {

    private final List<T> expenses;

    public Pagination(List<T> expenses) {
        this.expenses = expenses;
    }

    public List<T> getCurrentPageContent(int page, int size) {
        List<T> expenseList = expenses;
        expenseList = expenseList.stream()
            .skip((long) page * size)
            .limit(size)
            .collect(Collectors.toList());

        return expenseList;
    }

    public List<Integer> getPageNumbers(int pageCount) {
        List<Integer> pageNumbers = IntStream.rangeClosed(1, pageCount)
            .boxed()
            .collect(Collectors.toList());
        return pageNumbers;
    }

    public int getPageCount(int pageSize) {
        int pageCount = (expenses.size() / pageSize);
        return pageCount;
    }
}
