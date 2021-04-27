package ua.com.foxminded.university.dao;

import java.util.List;

public interface GenericDao<T> {
    void create(T t);

    T getByID(int id);

    List<T> getAll();

    void update(int id, T t);

    void delete(int id);
}