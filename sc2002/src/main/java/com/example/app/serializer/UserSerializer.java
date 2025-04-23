package com.example.app.serializer;

import com.example.app.enums.FlatType;
import com.example.app.enums.MaritalStatus;
import com.example.app.enums.Role;
import com.example.app.exceptions.DataParsingException;
import com.example.app.models.Applicant;
import com.example.app.models.Manager;
import com.example.app.models.Officer;
import com.example.app.models.User;

import java.util.LinkedList;

/**
 * Serializer for User objects.
 * This class implements the Serializer interface and provides methods to serialize and deserialize User objects.
 *
 * @see Serializer
 * @see User
 */
public class UserSerializer implements Serializer<User> {

    private StringSerializer stringSerializer = SerializerDependency.getStringSerializer();

    /**
     * Serializes a User object into a string format.
     * Remember to cast the User object to the appropriate subclass (e.g., Applicant, Officer) before serialization.
     * @param user The User object to serialize.
     * @return The serialized string representation of the User object.
     */
    @Override
    public String serialize(User user) {
        // Common fields
        String base = String.format("%d,%s,%s,%s,%s,%s,%d,%s",
                user.getId(),
                stringSerializer.serialize(user.getName()),
                stringSerializer.serialize(user.getPassword()),
                stringSerializer.serialize(user.getEmail()),
                user.getRole(),
                stringSerializer.serialize(user.getNric()),
                user.getAge(),
                user.getMaritalStatus()
        );

        // Append role-specific data
        return switch (user.getRole()) {
            case MANAGER -> base;
            case APPLICANT -> {
                Applicant applicant = (Applicant) user;
                yield base + "," +
                        (applicant.getFlatType() != null ? applicant.getFlatType().name() : "") + "," +
                        (applicant.getApplicationId() != null ? applicant.getApplicationId() : "");
            }
            case OFFICER -> {
                Officer officer = (Officer) user;
                yield base + "," +
                        (officer.getFlatType() != null ? officer.getFlatType().name() : "") + "," +
                        (officer.getApplicationId() != null ? officer.getApplicationId() : "") + "," +
                        (officer.getRegisteredId() != null ? officer.getRegisteredId() : "") + "," +
                        (officer.getProjectId() != null ? officer.getProjectId() : "");
            }
        };
    }

    /**
     * Deserializes a string representation of a User object into a User object.
     * @param parts A LinkedList of strings representing the parts of the User object.
     * @return The deserialized User object.
     * @throws DataParsingException If there is an error during parsing.
     */
    @Override
    public User deserialize(LinkedList<String> parts) throws DataParsingException {

        Integer id = Integer.valueOf(parts.removeFirst().trim());
        String name = stringSerializer.deserialize(parts);
        String password = stringSerializer.deserialize(parts);
        String email = stringSerializer.deserialize(parts);
        Role role = Role.valueOf(parts.removeFirst().trim());
        String nric = stringSerializer.deserialize(parts);
        int age = Integer.parseInt(parts.removeFirst().trim());
        MaritalStatus maritalStatus = MaritalStatus.valueOf(parts.removeFirst().trim());


        // Shared constructor args
        if (role == Role.MANAGER) {
            return new Manager(id, name, password, email, role, nric, age, maritalStatus);
        }

        if (role == Role.APPLICANT) {
            FlatType flatType = parseEnumOrNull(parts.removeFirst(), FlatType.class);
            Integer applicationId = parseIntOrNull(parts.removeFirst());

            return new Applicant(id, name, password, email, role, nric, age, maritalStatus, flatType, applicationId);
        }

        if (role == Role.OFFICER) {
            FlatType flatType = parseEnumOrNull(parts.removeFirst(), FlatType.class);
            Integer applicationId = parseIntOrNull(parts.removeFirst());
            Integer registeredId = parseIntOrNull(parts.removeFirst());
            Integer projectId = parseIntOrNull(parts.removeFirst());

            return new Officer(id, name, password, email, role, nric, age, maritalStatus, flatType, applicationId,
                    registeredId, projectId);
        }

        return new User(id, name, password, email, role, nric, age, maritalStatus);
    }

}
