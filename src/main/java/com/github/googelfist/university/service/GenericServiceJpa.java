package com.github.googelfist.university.service;

import java.util.List;

public interface GenericServiceJpa<T> {
    void create(T t);

    T getById(int id);

    List<T> getAll();

    void update(T t);

    void delete(int id);
}