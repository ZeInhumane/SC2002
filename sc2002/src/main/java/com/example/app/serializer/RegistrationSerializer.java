package com.example.app.serializer;

import com.example.app.enums.MaritalStatus;
import com.example.app.enums.RegistrationStatus;
import com.example.app.enums.Role;
import com.example.app.models.Registration;
import com.example.app.models.User;

import java.io.IOException;

public class RegistrationSerializer implements Serializer<Registration> {

    @Override
    public String serialize(Registration registration) {
        return String.format("%d, %d, %d, %s",
                registration.getId(),
                registration.getUserId(),
                registration.getProjectId(),
                registration.getStatus().toString()
        );
    }

    @Override
    public Registration deserialize(String inputLine) throws RuntimeException {
        String[] parts = inputLine.split(",");
        if (parts.length != 4) {
            return null;
        }
        return new Registration(
                Integer.parseInt(parts[0].trim()),
                Integer.parseInt(parts[1].trim()),
                Integer.parseInt(parts[2].trim()),
                RegistrationStatus.valueOf(parts[3].trim())
        );
    }
}
