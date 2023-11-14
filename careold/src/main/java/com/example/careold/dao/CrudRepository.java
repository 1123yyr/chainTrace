package com.example.careold.dao;

import java.util.List;

public interface CrudRepository<T,ID> {

    List<T> findById(ID id);

    int save(T t);

    int update(T t);

    int delete(ID id);
}
