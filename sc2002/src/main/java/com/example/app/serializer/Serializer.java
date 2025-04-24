package com.example.app.serializer;

import com.example.app.exceptions.DataParsingException;
import java.util.LinkedList;

/**
 * Serializer interface for serializing and deserializing objects. This interface defines methods for converting objects
 * to a string format and vice versa.
 *
 * @param <T>
 *            The type of the object to be serialized/deserialized.
 */

public interface Serializer<T> {

    /**
     * Serializes an object of type T into a string format.
     *
     * @param entity
     *            The object to serialize.
     * @return The serialized string representation of the object.
     */
    String serialize(T entity);

    /**
     * Deserializes a string representation of an object into an object of type T.
     * 
     * @param parts
     *            A LinkedList of strings representing the parts of the object.
     * @return The deserialized object of type T.
     * @throws DataParsingException
     *             If there is an error during parsing.
     */
    T deserialize(LinkedList<String> parts) throws DataParsingException;

    /**
     * Parses a string into an integer. Returns null if the string is null or empty.
     *
     * @param raw
     *            The string to parse.
     * @return The parsed integer, or null if the string is null or empty.
     */
    default Integer parseIntOrNull(String raw) {
        return (raw == null || raw.trim().isEmpty()) ? null : Integer.parseInt(raw.trim());
    }

    /**
     * Parses a string into a long. Returns null if the string is null or empty.
     *
     * @param raw
     *            The string to parse.
     * @return The parsed long, or null if the string is null or empty.
     */
    default <E extends Enum<E>> E parseEnumOrNull(String raw, Class<E> enumClass) {
        return (raw == null || raw.trim().isEmpty()) ? null : Enum.valueOf(enumClass, raw.trim());
    }
}