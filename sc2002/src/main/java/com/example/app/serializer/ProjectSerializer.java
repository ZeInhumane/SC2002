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

public class ProjectSerializer implements Serializer<Project> {

    private StringSerializer stringSerializer = SerializerDependency.getStringSerializer();

    @Override
    public String serialize(Project project) {
        StringBuilder result = new StringBuilder();
        result.append(String.format("%d,%s,%s,%s,%s,%d,%b",
                project.getId(),
                stringSerializer.serialize(project.getProjectName()),
                new SimpleDateFormat("yyyy-MM-dd").format(project.getApplicationOpenDate()),
                new SimpleDateFormat("yyyy-MM-dd").format(project.getApplicationCloseDate()),
                project.getNeighborhood(),
                project.getManagerId(),
                project.getVisibility()));
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

        return new Project(id, projectName, applicationOpenDate, applicationCloseDate, neighborhood, managerId, visibility, group, flatCount);
    }
}
