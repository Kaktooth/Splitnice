package com.example.splitwise.utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Pagination<T> {

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
        return IntStream.rangeClosed(1, pageCount)
            .boxed()
            .collect(Collectors.toList());
    }

    public int getPageCount(int pageSize) {
        return ((expenses.size() - 1) / pageSize);
    }
}
