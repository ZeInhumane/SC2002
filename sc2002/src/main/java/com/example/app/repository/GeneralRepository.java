package com.example.app.repository;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.example.app.models.BaseEntity;

public class GeneralRepository<T extends BaseEntity> {

    protected final Map<Integer, T> storage = new HashMap<>();

    public T save(T obj) {
        storage.put(obj.getId(), obj);
        return obj;
    }

    public T findById(int id) {
        return storage.get(id);
    }

    public Collection<T> findAll() {
        return storage.values();
    }

    public void deleteById(int id) {
        storage.remove(id);
    }
}
