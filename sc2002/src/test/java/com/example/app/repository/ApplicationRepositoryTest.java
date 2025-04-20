package com.example.app.repository;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.app.enums.FlatType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.app.models.Application;
import com.example.app.enums.ApplicationStatus;

public class ApplicationRepositoryTest {

    private ApplicationRepository applicationRepo;

    @BeforeEach
    public void setup() throws IOException {
        applicationRepo = new ApplicationRepository();

        // Sample data
        applicationRepo.save(new Application(null, 1, 100, ApplicationStatus.PENDING, false, FlatType._2ROOM));
        applicationRepo.save(new Application(null, 2, 101, ApplicationStatus.SUCCESSFUL, false, FlatType._3ROOM));
        applicationRepo.save(new Application(null, 1, 102, ApplicationStatus.PENDING, false, FlatType._2ROOM));
        applicationRepo.save(new Application(null, 3, 100, ApplicationStatus.UNSUCCESSFUL, false, FlatType._3ROOM));
    }

    @Test
    public void testFindByUserId() throws IOException {
        List<Application> user1Apps = applicationRepo.findByUserId(1);
        assertEquals(2, user1Apps.size());
        assertTrue(user1Apps.stream().allMatch(app -> app.getUserId() == 1));
    }

    @Test
    public void testFindByProjectId() throws IOException {
        List<Application> project100Apps = applicationRepo.findByProjectId(100);
        assertEquals(2, project100Apps.size());
        assertTrue(project100Apps.stream().allMatch(app -> app.getProjectId() == 100));
    }

    @Test
    public void testFindByStatus() throws IOException {
        List<Application> pendingApps = applicationRepo.findByStatus(ApplicationStatus.PENDING);
        assertEquals(2, pendingApps.size());
        assertTrue(pendingApps.stream().allMatch(app -> app.getStatus() == ApplicationStatus.PENDING));
    }
}
