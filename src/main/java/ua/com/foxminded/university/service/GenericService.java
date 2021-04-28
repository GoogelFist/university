package ua.com.foxminded.university.service;

import java.util.List;

public interface GenericService<T> {
    void create(T t);

    T getById(int id);

    List<T> getAll();

    void update(int id, T t);

    void delete(int id);
}