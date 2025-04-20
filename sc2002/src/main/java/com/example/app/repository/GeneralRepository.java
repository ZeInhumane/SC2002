package com.example.app.repository;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import com.example.app.models.BaseEntity;
import com.example.app.serializer.Serializer;

public class GeneralRepository<T extends BaseEntity> {

    private final Serializer<T> serializer;
    private final String filePath;

    public GeneralRepository(Serializer<T> serializer, String filePath) {
        this.serializer = serializer;
        this.filePath = filePath;
    }

    public T save(T entity) throws IOException {
        if (entity.getId() == null) {
            // Create new ID random number
            Random random = new Random();
            int id = random.nextInt(1000000); // Generate a random ID
            entity.setId(id);
        }
        HashMap<Integer, T> all = findAllAsMap();
        all.put(entity.getId(), entity); // prevents ID overlap by updating
        overwriteFile(all);
        return entity;
    }

    public List<T> findAll() throws IOException {
        List<T> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.add(serializer.deserialize(line));
            }
        }
        return result;
    }

    public T findById(Integer id) throws IOException {
        return this.findAll().stream()
                .filter(entity -> Objects.equals(entity.getId(), id))
                .findFirst()
                .orElse(null);
    }

    public void deleteById(Integer id) throws IOException {
        HashMap<Integer, T> all = findAllAsMap();
        all.remove(id);
        overwriteFile(all);
    }

    private HashMap<Integer, T> findAllAsMap() throws IOException {
        HashMap<Integer, T> result = new HashMap<>();
        for (T entity : findAll()) {
            result.put(entity.getId(), entity);
        }
        return result;
    }


    private void overwriteFile(HashMap<Integer, T> entities) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (T entity : new ArrayList<>(entities.values())) {
                writer.write(serializer.serialize(entity));
                writer.newLine();
            }
        }
    }
}
