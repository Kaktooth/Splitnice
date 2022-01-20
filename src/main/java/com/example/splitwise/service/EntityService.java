package com.example.splitwise.service;

import com.example.splitwise.model.Identifiable;

import java.util.List;
import java.util.Set;

public interface EntityService<T extends Identifiable> {

    T add(T entity);

    T getById(Integer entityId);

    List<T> getAll(Set<Integer> ids);

    void delete(Integer entityId);
}
