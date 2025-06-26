package com.egorov.dao;

import java.util.List;

public interface Dao <K, E> {
    void createTable();

    void deleteTable();

    void deleteAllFromTable();

    void update(E entity);

    E findById(K id);

    List <E> findAll();

    void delete(K id);

    void save(E entity);
}
