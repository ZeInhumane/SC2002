package com.example.app.service;

import com.example.app.enums.ApplicationStatus;
import com.example.app.enums.FlatType;
import com.example.app.models.Application;
import com.example.app.repository.RepositoryDependency;
import com.example.app.service.impl.ApplicationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationServiceTest {
    private ApplicationService service;

    @BeforeEach
    void setUp() throws IOException {
        RepositoryDependency.getApplicationRepository().deleteAll();
        service = new ApplicationServiceImpl();
    }

    @Test
    void applyAndFindApplication() throws IOException {
        Application application = service.applyForProject(1, 101, FlatType._2ROOM);
        assertNotNull(application.getId());
        assertEquals(ApplicationStatus.PENDING, application.getStatus());

        Application found = service.findById(application.getId());
        assertNotNull(found);
        assertEquals(application.getId(), found.getId());
    }

    @Test
    void updateApplicationStatus() throws IOException {
        Application app = service.applyForProject(2, 102, FlatType._3ROOM);
        assertEquals(ApplicationStatus.PENDING, app.getStatus());

        Application updated = service.updateStatus(app.getId(), ApplicationStatus.SUCCESSFUL);
        assertEquals(ApplicationStatus.SUCCESSFUL, updated.getStatus());
    }

    @Test
    void updateWithdrawalStatusRejectAndApprove() throws IOException {
        Application app = service.applyForProject(3, 103, FlatType._2ROOM);
        app.setRequestWithdrawal(true);
        service.save(app);

        Application rejected = service.updateWithdrawalStatus(app.getId(), false);
        assertFalse(rejected.isRequestWithdrawal());

        app.setRequestWithdrawal(true);
        service.save(app);
        Application approved = service.updateWithdrawalStatus(app.getId(), true);
        assertEquals(ApplicationStatus.WITHDRAWN, approved.getStatus());
    }

    @Test
    void saveApplicationUpdatesValues() throws IOException {
        Application app = service.applyForProject(4, 104, FlatType._3ROOM);
        app.setFlatType(FlatType._2ROOM);
        app.setStatus(ApplicationStatus.UNSUCCESSFUL);
        Application saved = service.save(app);

        assertEquals(FlatType._2ROOM, saved.getFlatType());
        assertEquals(ApplicationStatus.UNSUCCESSFUL, saved.getStatus());
    }
}