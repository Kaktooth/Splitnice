package com.example.splitwise.service;

import com.example.splitwise.model.Identifiable;

import java.util.Collection;
import java.util.Set;

public interface EntityService<T extends Identifiable> {

    T add(T entity);

    T update(Integer id, T entity);

    T getById(Integer entityId);

    Collection<T> getAll(Set<Integer> ids);

    void delete(Integer entityId);
}
