package com.example.app.serializer;

import com.example.app.enums.ApplicationStatus;
import com.example.app.enums.FlatType;
import com.example.app.exceptions.DataParsingException;
import com.example.app.models.Application;

import java.util.LinkedList;

public class ApplicationSerializer implements Serializer<Application> {

    @Override
    public String serialize(Application application) {
        return String.format("%d,%d,%d,%s,%s,%s",
                application.getId(),
                application.getUserId(),
                application.getProjectId(),
                application.getStatus().toString(),
                application.isRequestWithdrawal() ? "true" : "false",
                application.getFlatType().toString()
        );
    }

    @Override
    public Application deserialize(LinkedList<String> parts) throws DataParsingException {
        return new Application(
            Integer.parseInt(parts.removeFirst().trim()),
            Integer.parseInt(parts.removeFirst().trim()),
            Integer.parseInt(parts.removeFirst().trim()),
            ApplicationStatus.valueOf(parts.removeFirst().trim()),
            Boolean.parseBoolean(parts.removeFirst().trim()),
            FlatType.valueOf(parts.removeFirst().trim())
        );
    }
}
