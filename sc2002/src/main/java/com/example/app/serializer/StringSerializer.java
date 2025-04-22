package com.example.app.serializer;

import com.example.app.exceptions.DataParsingException;

import java.util.LinkedList;

/**
 * Serializer for String objects.
 * This class implements the Serializer interface and provides methods to serialize and deserialize
 * Since there are 'commas' in the string, we need to count the number of commas in the string
 * and store it in the first part of the string
 * then we can use the comma to separate the string
 */
public class StringSerializer implements Serializer<String> {

    /**
     * Serializes a String object into a string format.
     * @param entity The String object to serialize.
     * @return The serialized string representation of the String object.
     */
    @Override
    public String serialize(String entity) {
        if (entity == null) {
            return "0";
        }
        int questionCommas = entity.split(",", -1).length;
        return String.format("%d,%s", questionCommas, entity);
    }

    /**
     * Deserializes a string representation of a String object into a String object.
     * @param parts A LinkedList of strings representing the parts of the String object.
     * @return The deserialized String object.
     * @throws DataParsingException If there is an error during parsing.
     */
    @Override
    public String deserialize(LinkedList<String> parts) throws DataParsingException {
        if (parts.isEmpty()) {
            throw new DataParsingException("Cannot deserialize an empty string");
        }
        int count = Integer.parseInt(parts.removeFirst());
        if (count == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(parts.removeFirst());
            if (i != count - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

}
