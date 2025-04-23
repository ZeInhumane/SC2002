package com.example.app.repository;

import java.io.*;
import java.util.*;

import com.example.app.Settings;
import com.example.app.models.BaseEntity;
import com.example.app.serializer.Serializer;

/**
 * GeneralRepository is a generic class that provides basic CRUD operations (Create, Read, Update, Delete)
 * for any entity that extends BaseEntity.
 * It uses a Serializer to convert entities to and from a string format.
 * The repository is backed by a text file, and the file path is specified during instantiation.
 *
 * @param <T> the type of entity that extends BaseEntity
 *
 * @see BaseEntity
 * @see Serializer
 */
public class GeneralRepository<T extends BaseEntity> {

    private final Serializer<T> serializer;
    private final String filePath;

    /**
     * Constructor for GeneralRepository
     * @param serializer the serializer to use for converting entities to and from string format
     * @param filePath the path to the file where entities are stored. This path is relative to the DB_PATH specified in Settings.
     *
     * @see Settings
     */
    public GeneralRepository(Serializer<T> serializer, String filePath) {
        this.serializer = serializer;
        this.filePath = Settings.DB_PATH + filePath;

        // Ensure file exists
        File file = new File(this.filePath);
        try {
            File parent = file.getParentFile();
            if (parent != null) {
                parent.mkdirs(); // create directories if needed
            }
            file.createNewFile(); // create file if not exists
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize repository file: " + this.filePath, e);
        }
    }

    /**
     * Saves an entity to the file. If the entity does not have an ID, a new ID is generated.
     * If the entity already exists, it is updated.
     *
     * @param entity the entity to save
     * @return the saved entity
     * @throws IOException if there is an error writing to the file
     */
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

    /**
     * Finds all entities in the file.
     *
     * @return a list of all entities
     * @throws IOException if there is an error reading from the file
     */
    public List<T> findAll() throws IOException {
        List<T> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                LinkedList<String> parts = new LinkedList<>(Arrays.asList(line.split(",", -1)));
                result.add(serializer.deserialize(parts));
            }
        }
        return result;
    }

    /**
     * Finds all entities in the file and returns them as a map with ID as the key.
     * @param id the ID of the entity to find
     * @return a map of all entities with ID as the key
     * @throws IOException if there is an error reading from the file
     */
    public T findById(Integer id) throws IOException {
        return this.findAll().stream().filter(entity -> Objects.equals(entity.getId(), id)).findFirst().orElse(null);
    }

    /**
     * Deletes an entity by its ID.
     * @param id the ID of the entity to delete
     * @throws IOException if there is an error writing to the file
     */
    public void deleteById(Integer id) throws IOException {
        HashMap<Integer, T> all = findAllAsMap();
        all.remove(id);
        overwriteFile(all);
    }

    /**
     * Deletes all entities in the file.
     * @throws IOException if there is an error writing to the file
     */
    public void deleteAll() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("");
        }
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
