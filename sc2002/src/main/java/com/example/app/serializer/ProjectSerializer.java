package com.example.app.serializer;

import com.example.app.enums.FlatType;
import com.example.app.enums.MaritalStatus;
import com.example.app.enums.RegistrationStatus;
import com.example.app.models.Project;
import com.example.app.models.Registration;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ProjectSerializer implements Serializer<Project> {

    @Override
    public String serialize(Project project) {
        String result = "";
        result += String.format("%d, %s, %s, %s, %s, %d, %s, %b, %d",
                project.getId(),
                project.getProjectName(),
                new SimpleDateFormat("yyyy-MM-dd").format(project.getApplicationOpenDate()),
                new SimpleDateFormat("yyyy-MM-dd").format(project.getApplicationCloseDate()),
                project.getNeighborhood(),
                project.getManagerId(),
                project.getGroup().toString(),
                project.getVisibility(),
                project.getFlats().size()
        );

        for (Map.Entry<FlatType, Integer> entry : project.getFlats().entrySet()) {
            result += String.format(", %s:%d", entry.getKey().toString(), entry.getValue());
        }
        return result;
    }

    @Override
    public Project deserialize(String inputLine) throws RuntimeException {
        String[] parts = inputLine.split(",");

        Integer id = Integer.parseInt(parts[0].trim());
        String projectName = parts[1].trim();
        Date applicationOpenDate;
        Date applicationCloseDate;
        try {
            applicationOpenDate = new SimpleDateFormat("yyyy-MM-dd").parse(parts[2].trim());
            applicationCloseDate = new SimpleDateFormat("yyyy-MM-dd").parse(parts[3].trim());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        String neighborhood = parts[4].trim();
        Integer managerId = Integer.parseInt(parts[5].trim());
        MaritalStatus group = MaritalStatus.valueOf(parts[6].trim());
        Boolean visibility = Boolean.parseBoolean(parts[7].trim());
        int flatCountEntries = Integer.parseInt(parts[8].trim());
        Map<FlatType, Integer> flatCount = new HashMap<>();

        for (int i = 0; i < flatCountEntries; i++) {
            String[] flatParts = parts[9 + i].trim().split(":");
            FlatType flatType = FlatType.valueOf(flatParts[0].trim());
            Integer count = Integer.parseInt(flatParts[1].trim());
            flatCount.put(flatType, count);
        }

        return new Project(id, projectName, applicationOpenDate, applicationCloseDate, neighborhood, managerId, group, visibility, flatCount);
    }
}
