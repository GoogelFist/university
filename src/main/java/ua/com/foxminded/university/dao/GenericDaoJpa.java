package ua.com.foxminded.university.dao;

import java.util.List;

public interface GenericDaoJpa<T> {
    void create(T t);

    T getById(int id);

    List<T> getAll();

    void update(T t);

    void delete(int id);
}