package com.example.app.serializer;

import com.example.app.exceptions.DataParsingException;

import java.util.LinkedList;

public class StringSerializer implements Serializer<String>{

    @Override
    public String serialize(String entity) {
        if (entity == null) {
            return "0,";
        }
        int questionCommas = entity.split(",", -1).length;
        return String.format("%d,%s", questionCommas, entity);
    }

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
