package com.example.app.repository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.app.models.Application;
import com.example.app.models.ApplicationStatus;

public class ApplicationRepositoryTest {

    private ApplicationRepository applicationRepo;

    @BeforeEach
    public void setup() {
        applicationRepo = new ApplicationRepository();

        // Sample data
        applicationRepo.save(new Application(1, 100, ApplicationStatus.PENDING));
        applicationRepo.save(new Application(2, 101, ApplicationStatus.SUCCESSFUL));
        applicationRepo.save(new Application(1, 102, ApplicationStatus.PENDING));
        applicationRepo.save(new Application(3, 100, ApplicationStatus.UNSUCCESSFUL));
    }

    @Test
    public void testFindByUserId() {
        List<Application> user1Apps = applicationRepo.findByUserId(1);
        assertEquals(2, user1Apps.size());
        assertTrue(user1Apps.stream().allMatch(app -> app.getUserId() == 1));
    }

    @Test
    public void testFindByProjectId() {
        List<Application> project100Apps = applicationRepo.findByProjectId(100);
        assertEquals(2, project100Apps.size());
        assertTrue(project100Apps.stream().allMatch(app -> app.getProjectId() == 100));
    }

    @Test
    public void testFindByStatus() {
        List<Application> pendingApps = applicationRepo.findByStatus(ApplicationStatus.PENDING);
        assertEquals(2, pendingApps.size());
        assertTrue(pendingApps.stream().allMatch(app -> app.getStatus() == ApplicationStatus.PENDING));
    }
}
