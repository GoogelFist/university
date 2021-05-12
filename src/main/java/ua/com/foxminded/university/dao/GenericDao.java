package ua.com.foxminded.university.dao;

import ua.com.foxminded.university.dao.exceptions.DaoException;

import java.util.List;

public interface GenericDao<T> {
    void create(T t) throws DaoException;

    T getById(int id) throws DaoException;

    List<T> getAll() throws DaoException;

    void update(int id, T t) throws DaoException;

    void delete(int id) throws DaoException;
}