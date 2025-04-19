package com.example.app.serializer;

import com.example.app.enums.ApplicationStatus;
import com.example.app.enums.FlatType;
import com.example.app.models.Application;

public class ApplicationSerializer implements Serializer<Application> {

    @Override
    public String serialize(Application application) {
        return String.format("%d,%d,%d,%s,%s",
                application.getId(),
                application.getUserId(),
                application.getProjectId(),
                application.getStatus().toString(),
                application.getFlatType().toString()
        );
    }

    @Override
    public Application deserialize(String data) throws RuntimeException {
        String[] parts = data.split(",");
        return new Application(
            Integer.parseInt(parts[0].trim()),
            Integer.parseInt(parts[1].trim()),
            Integer.parseInt(parts[2].trim()),
            ApplicationStatus.valueOf(parts[3].trim()),
            FlatType.valueOf(parts[4].trim())
        );
    }
}
