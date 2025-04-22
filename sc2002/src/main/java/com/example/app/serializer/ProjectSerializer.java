package com.example.app.serializer;

import com.example.app.enums.FlatType;
import com.example.app.enums.MaritalStatus;
import com.example.app.enums.RegistrationStatus;
import com.example.app.exceptions.DataParsingException;
import com.example.app.models.Project;
import com.example.app.models.Registration;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Serializer for Project objects.
 * This class implements the Serializer interface and provides methods to serialize and deserialize Project objects.
 * @see Serializer
 * @see Project
 */
public class ProjectSerializer implements Serializer<Project> {

    private StringSerializer stringSerializer = SerializerDependency.getStringSerializer();

    /**
     * Serializes a Project object into a string format.
     * @param project The Project object to serialize.
     * @return The serialized string representation of the Project object.
     */
    @Override
    public String serialize(Project project) {
        StringBuilder result = new StringBuilder();
        result.append(String.format("%d,%s,%s,%s,%s,%d,%b,%d", project.getId(),
                stringSerializer.serialize(project.getProjectName()),
                new SimpleDateFormat("yyyy-MM-dd").format(project.getApplicationOpenDate()),
                new SimpleDateFormat("yyyy-MM-dd").format(project.getApplicationCloseDate()), project.getNeighborhood(),
                project.getManagerId(), project.getVisibility(), project.getOfficerLimit()));

        result.append(",").append(project.getOfficers().size());
        for (Integer officerId : project.getOfficers()) {
            result.append(",").append(officerId);
        }

        result.append(",").append(project.getGroups().size());
        for (MaritalStatus group : project.getGroups()) {
            result.append(",").append(group.toString());
        }

        result.append(",").append(project.getFlats().size());
        for (Map.Entry<FlatType, Integer> entry : project.getFlats().entrySet()) {
            result.append(String.format(",%s:%d", entry.getKey().toString(), entry.getValue()));
        }
        return result.toString();
    }

    /**
     * Deserializes a string representation of a Project object into a Project object.
     * @param parts A LinkedList of strings representing the parts of the Project object.
     * @return The deserialized Project object.
     * @throws DataParsingException If there is an error during parsing.
     */
    @Override
    public Project deserialize(LinkedList<String> parts) throws DataParsingException {
        Integer id = Integer.parseInt(parts.removeFirst());
        String projectName = stringSerializer.deserialize(parts);
        Date applicationOpenDate;
        Date applicationCloseDate;
        try {
            applicationOpenDate = new SimpleDateFormat("yyyy-MM-dd").parse(parts.removeFirst().trim());
            applicationCloseDate = new SimpleDateFormat("yyyy-MM-dd").parse(parts.removeFirst().trim());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        String neighborhood = parts.removeFirst().trim();
        Integer managerId = Integer.parseInt(parts.removeFirst().trim());
        Boolean visibility = Boolean.parseBoolean(parts.removeFirst().trim());
        Integer officerLimit = Integer.parseInt(parts.removeFirst().trim());
        int officerCount = Integer.parseInt(parts.removeFirst().trim());
        Set<Integer> officers = new HashSet<>();
        for (int i = 0; i < officerCount; i++) {
            officers.add(Integer.parseInt(parts.removeFirst().trim()));
        }

        int groupCount = Integer.parseInt(parts.removeFirst().trim());
        Set<MaritalStatus> group = new HashSet<>();
        for (int i = 0; i < groupCount; i++) {
            group.add(MaritalStatus.valueOf(parts.removeFirst().trim()));
        }
        int flatCountEntries = Integer.parseInt(parts.removeFirst().trim());
        Map<FlatType, Integer> flatCount = new HashMap<>();

        for (int i = 0; i < flatCountEntries; i++) {
            String[] flatParts = parts.removeFirst().split(":");
            FlatType flatType = FlatType.valueOf(flatParts[0].trim());
            Integer count = Integer.parseInt(flatParts[1].trim());
            flatCount.put(flatType, count);
        }

        return new Project(id, projectName, applicationOpenDate, applicationCloseDate, neighborhood, managerId,
                visibility, officerLimit, officers, group, flatCount);
    }
}
