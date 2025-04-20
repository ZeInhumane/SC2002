package com.example.app.serializer;

import com.example.app.enums.FlatType;
import com.example.app.enums.MaritalStatus;
import com.example.app.enums.Role;
import com.example.app.exceptions.DataParsingException;
import com.example.app.models.Applicant;
import com.example.app.models.Manager;
import com.example.app.models.Officer;
import com.example.app.models.User;

public class UserSerializer implements Serializer<User>{

    @Override
    public String serialize(User user) {
        // Common fields
        String base = String.format("%d,%s,%s,%s,%s,%s,%d,%s",
                user.getId(),
                user.getName(),
                user.getPassword(),
                user.getEmail(),
                user.getRole(),
                user.getNric(),
                user.getAge(),
                user.getMaritalStatus()
        );

        if (user instanceof Officer officer) {
            return base + "," +
                    (officer.getFlatType() != null ? officer.getFlatType() : "") + "," +
                    (officer.getApplicationId() != null ? officer.getApplicationId() : "") + "," +
                    (officer.getRegisteredProject() != null ? officer.getRegisteredProject() : "");
        }
        else if (user instanceof Manager manager) {
            return base + "," +
                    (manager.getCurrentProjectId() != null ? manager.getCurrentProjectId() : "");
        }
        else if (user instanceof Applicant applicant) {
            return base + "," + (applicant.getFlatType() != null ? applicant.getFlatType() : "") + "," +
                    (applicant.getApplicationId() != null ? applicant.getApplicationId() : "");
        }
        else {
            return base;
        }
    }

    @Override
    public User deserialize(String inputLine) throws RuntimeException{
        String[] parts = inputLine.split(",");

        Role role = Role.valueOf(parts[4].trim());

        switch (role) {
            case OFFICER -> {
                FlatType flatType = parts[8].isEmpty() ? null : FlatType.valueOf(parts[8].trim());
                Integer applicationId = parts[9].isEmpty() ? null : Integer.valueOf(parts[9].trim());
                Integer registeredProject = parts[9].isEmpty() ? null : Integer.valueOf(parts[9].trim());

                return new Officer(
                        Integer.valueOf(parts[0].trim()),
                        parts[1].trim(),
                        parts[2].trim(),
                        parts[3].trim(),
                        role,
                        parts[5].trim(),
                        Integer.parseInt(parts[6].trim()),
                        MaritalStatus.valueOf(parts[7].trim()),
                        flatType,
                        applicationId,
                        registeredProject
                );
            }
            case MANAGER -> {
                Integer currentProjectId = parts[8].isEmpty() ? null : Integer.valueOf(parts[8].trim());
                Manager manager = new Manager(
                        Integer.valueOf(parts[0].trim()),
                        parts[1].trim(),
                        parts[2].trim(),
                        parts[3].trim(),
                        role,
                        parts[5].trim(),
                        Integer.parseInt(parts[6].trim()),
                        MaritalStatus.valueOf(parts[7].trim()),
                        currentProjectId
                );
            }
            case APPLICANT -> {
                FlatType flatType = parts[8].isEmpty() ? null : FlatType.valueOf(parts[8].trim());
                Integer applicationId = parts[9].isEmpty() ? null : Integer.valueOf(parts[9].trim());
                return new Applicant(
                        Integer.valueOf(parts[0].trim()),
                        parts[1].trim(),
                        parts[2].trim(),
                        parts[3].trim(),
                        role,
                        parts[5].trim(),
                        Integer.parseInt(parts[6].trim()),
                        MaritalStatus.valueOf(parts[7].trim()),
                        flatType,
                        applicationId
                );
            }
            default -> {
                return new User(
                        Integer.valueOf(parts[0].trim()),
                        parts[1].trim(),
                        parts[2].trim(),
                        parts[3].trim(),
                        role,
                        parts[5].trim(),
                        Integer.parseInt(parts[6].trim()),
                        MaritalStatus.valueOf(parts[7].trim())
                );
            }
        }
        return null;
    }
}
