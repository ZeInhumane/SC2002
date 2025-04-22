package com.example.app.serializer;

import com.example.app.enums.ApplicationStatus;
import com.example.app.enums.FlatType;
import com.example.app.exceptions.DataParsingException;
import com.example.app.models.Application;

import java.util.LinkedList;

/**
 * Serializer for Application objects.
 * This class implements the Serializer interface and provides methods to serialize and deserialize Application objects.
 * @see Serializer
 * @see Application
 */
public class ApplicationSerializer implements Serializer<Application> {

    /**
     * Serializes an Application object into a string format for storage.
     * 
     * @param application
     *            the Application object to serialize
     * @return a string representation of the Application object
     */
    @Override
    public String serialize(Application application) {
        return String.format("%d,%d,%d,%s,%s,%s", application.getId(), application.getUserId(),
                application.getProjectId(), application.getStatus().toString(),
                application.isRequestWithdrawal() ? "true" : "false", application.getFlatType().toString());
    }

    /**
     * Deserializes a string representation of an Application object into an Application object.
     * 
     * @param parts
     *            a LinkedList of strings representing the parts of the Application object
     * @return an Application object
     * @throws DataParsingException
     *             if there is an error during parsing
     */
    @Override
    public Application deserialize(LinkedList<String> parts) throws DataParsingException {
        return new Application(Integer.parseInt(parts.removeFirst().trim()),
                Integer.parseInt(parts.removeFirst().trim()), Integer.parseInt(parts.removeFirst().trim()),
                ApplicationStatus.valueOf(parts.removeFirst().trim()), Boolean.parseBoolean(parts.removeFirst().trim()),
                FlatType.valueOf(parts.removeFirst().trim()));
    }
}
