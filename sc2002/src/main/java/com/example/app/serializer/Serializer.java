package com.example.app.serializer;

import com.example.app.exceptions.DataParsingException;
import com.example.app.models.BaseEntity;
import com.example.app.models.User;

public interface Serializer<T extends BaseEntity> {
    String serialize(T entity);

    T deserialize(String csvLine);
}