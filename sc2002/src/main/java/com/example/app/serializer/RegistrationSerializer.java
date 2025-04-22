package com.example.app.serializer;

import com.example.app.enums.MaritalStatus;
import com.example.app.enums.RegistrationStatus;
import com.example.app.enums.Role;
import com.example.app.exceptions.DataParsingException;
import com.example.app.models.Registration;
import com.example.app.models.User;

import java.io.IOException;
import java.util.LinkedList;

public class RegistrationSerializer implements Serializer<Registration> {

    @Override
    public String serialize(Registration registration) {
        return String.format("%d,%d,%d,%s",
                registration.getId(),
                registration.getUserId(),
                registration.getProjectId(),
                registration.getStatus().toString()
        );
    }

    @Override
    public Registration deserialize(LinkedList<String> parts) throws DataParsingException {
        return new Registration(
                Integer.parseInt(parts.removeFirst().trim()),
                Integer.parseInt(parts.removeFirst().trim()),
                Integer.parseInt(parts.removeFirst().trim()),
                RegistrationStatus.valueOf(parts.removeFirst().trim())
        );
    }
}
