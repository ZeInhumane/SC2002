package com.example.app.serializer;

import com.example.app.exceptions.DataParsingException;
import com.example.app.models.BaseEntity;
import com.example.app.models.User;

import java.util.LinkedList;

public interface Serializer<T> {
    String serialize(T entity);

    T deserialize(LinkedList<String> parts) throws DataParsingException;

    default Integer parseIntOrNull(String raw) {
        return (raw == null || raw.trim().isEmpty()) ? null : Integer.parseInt(raw.trim());
    }

    default <E extends Enum<E>> E parseEnumOrNull(String raw, Class<E> enumClass) {
        return (raw == null || raw.trim().isEmpty()) ? null : Enum.valueOf(enumClass, raw.trim());
    }
}