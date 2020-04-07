package com.maksym.customermanager.repository;

import java.util.List;

public interface GenericRepository<T, ID> {
    List<T> getAll();

    T get(ID id);

    T saveOrUpdate(T t);

    boolean remove(ID id);
}
