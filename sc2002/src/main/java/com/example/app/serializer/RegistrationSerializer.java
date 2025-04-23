package com.example.app.serializer;

import com.example.app.enums.RegistrationStatus;
import com.example.app.exceptions.DataParsingException;
import com.example.app.models.Registration;
import java.util.LinkedList;

/**
 * Serializer for the Registration class.
 * This class implements the Serializer interface and provides methods to serialize and deserialize
 * Registration objects to and from a string format.
 *
 * @see Serializer
 * @see Registration
 */
public class RegistrationSerializer implements Serializer<Registration> {

    /**
     * Serializes a Registration object into a string format.
     * @param registration The Registration object to serialize.
     * @return The serialized string representation of the Registration object.
     */
    @Override
    public String serialize(Registration registration) {
        return String.format("%d,%d,%d,%s", registration.getId(), registration.getUserId(), registration.getProjectId(),
                registration.getStatus().toString());
    }

    /**
     * Deserializes a string representation of a Registration object into a Registration object.
     * @param parts A LinkedList of strings representing the parts of the Registration object.
     * @return The deserialized Registration object.
     * @throws DataParsingException If there is an error during parsing.
     */
    @Override
    public Registration deserialize(LinkedList<String> parts) throws DataParsingException {
        return new Registration(Integer.parseInt(parts.removeFirst().trim()),
                Integer.parseInt(parts.removeFirst().trim()), Integer.parseInt(parts.removeFirst().trim()),
                RegistrationStatus.valueOf(parts.removeFirst().trim()));
    }
}
