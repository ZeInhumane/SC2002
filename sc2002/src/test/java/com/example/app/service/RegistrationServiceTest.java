package com.example.app.service;

import com.example.app.enums.RegistrationStatus;
import com.example.app.repository.RepositoryDependency;
import com.example.app.service.impl.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceTest {
    private RegistrationService service;

    @BeforeEach
    void setUp() throws IOException {
        RepositoryDependency.getRegistrationRepository().deleteAll();
        service = new RegistrationServiceImpl();
    }

    @Test
    void registerAndFindById() throws IOException {
        com.example.app.models.Registration registration = service.registerForProject(1, 100);
        assertNotNull(registration.getId());
        assertEquals(1, registration.getUserId());
        assertEquals(100, registration.getProjectId());
        assertEquals(RegistrationStatus.PENDING, registration.getStatus());

        com.example.app.models.Registration found = service.findById(registration.getId());
        assertEquals(registration.getId(), found.getId());
    }

    @Test
    void findByProjectId() throws IOException {
        service.registerForProject(2, 200);
        service.registerForProject(3, 200);

        List<com.example.app.models.Registration> list = service.findByProjectId(200);
        assertEquals(2, list.size());
    }

    @Test
    void changeStatusToApproved() throws IOException {
        com.example.app.models.Registration reg = service.registerForProject(4, 300);
        com.example.app.models.Registration updated = service.changeStatus(reg.getId(), RegistrationStatus.APPROVED);

        assertEquals(RegistrationStatus.APPROVED, updated.getStatus());
    }

    @Test
    void deleteRegistration() throws IOException {
        com.example.app.models.Registration reg = service.registerForProject(5, 400);
        service.deleteById(reg.getId());
        assertNull(service.findById(reg.getId()));
    }
}
