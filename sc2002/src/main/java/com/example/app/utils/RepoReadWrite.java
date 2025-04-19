package com.example.app.utils;

import com.example.app.models.BaseEntity;

import java.io.*;
import java.util.HashMap;

public class RepoReadWrite<T extends BaseEntity> {

    public void saveHashMap(HashMap<Integer, T> storage, String fileName) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
        oos.writeObject(storage);
    }

    public HashMap<Integer, T> readHashMap(String fileName) throws IOException, ClassNotFoundException {
        HashMap<Integer, T> storage = new HashMap<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            storage = (HashMap<Integer, T>) ois.readObject();
        }
        return storage;
    }
}
