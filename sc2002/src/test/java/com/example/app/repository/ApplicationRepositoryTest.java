package com.example.app.repository;

import com.example.app.enums.ApplicationStatus;
import com.example.app.enums.FlatType;
import com.example.app.models.Application;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationRepositoryTest extends GeneralRepositoryTestBase<Application> {

    private final ApplicationRepository applicationRepository = new ApplicationRepository();

    @Override
    protected GeneralRepository<Application> getRepository() {
        return applicationRepository;
    }

    @Override
    protected List<Application> createSampleEntities() {
        return List.of(
                new Application(null, 1, 100, ApplicationStatus.PENDING, false, FlatType._3ROOM),
                new Application(null, 2, 100, ApplicationStatus.WITHDRAWN, true, FlatType._3ROOM),
                new Application(null, 1, 101, ApplicationStatus.BOOKED, false, FlatType._2ROOM),
                new Application(null, 3, 100, ApplicationStatus.PENDING, true, FlatType._2ROOM),
                new Application(null, 2, 101, ApplicationStatus.SUCCESSFUL, false, FlatType._3ROOM)
        );
    }

    @Override
    protected List<Application> saveSampleEntities() throws IOException {
        List<Application> applications = createSampleEntities();
        for (Application app : applications) {
            applicationRepository.save(app);
        }
        return applications;
    }

    @Test
    public void testFindByUserId() throws IOException {
        saveSampleEntities();

        List<Application> user1Apps = applicationRepository.findByUserId(1);
        assertEquals(2, user1Apps.size());

        List<Application> user2Apps = applicationRepository.findByUserId(2);
        assertEquals(2, user2Apps.size());

        List<Application> user3Apps = applicationRepository.findByUserId(3);
        assertEquals(1, user3Apps.size());

        List<Application> notFound = applicationRepository.findByUserId(999);
        assertTrue(notFound.isEmpty());
    }

    @Test
    public void testFindByUserIdAndStatus() throws IOException {
        saveSampleEntities();

        List<Application> pending = applicationRepository.findByUserIdAndStatus(1, ApplicationStatus.PENDING);
        assertEquals(1, pending.size());

        List<Application> booked = applicationRepository.findByUserIdAndStatus(1, ApplicationStatus.BOOKED);
        assertEquals(1, booked.size());

        List<Application> successful = applicationRepository.findByUserIdAndStatus(2, ApplicationStatus.SUCCESSFUL);
        assertEquals(1, successful.size());

        List<Application> notFound = applicationRepository.findByUserIdAndStatus(3, ApplicationStatus.SUCCESSFUL);
        assertTrue(notFound.isEmpty());
    }

    @Test
    public void testFindByProjectId() throws IOException {
        saveSampleEntities();

        List<Application> project100 = applicationRepository.findByProjectId(100);
        assertEquals(3, project100.size());

        List<Application> project101 = applicationRepository.findByProjectId(101);
        assertEquals(2, project101.size());

        List<Application> notFound = applicationRepository.findByProjectId(999);
        assertTrue(notFound.isEmpty());
    }

    @Test
    public void testFindByProjectIdAndRequestWithdrawal() throws IOException {
        saveSampleEntities();

        List<Application> withdrawalTrue = applicationRepository.findByProjectIdAndRequestWithdrawal(100, true);

        assertEquals(2, withdrawalTrue.size());

        List<Application> withdrawalFalse = applicationRepository.findByProjectIdAndRequestWithdrawal(100, false);
        assertEquals(1., withdrawalFalse.size());
    }

    @Test
    public void testFindByStatus() throws IOException {
        saveSampleEntities();

        List<Application> pending = applicationRepository.findByStatus(ApplicationStatus.PENDING);
        assertEquals(2, pending.size());

        List<Application> withdrawn = applicationRepository.findByStatus(ApplicationStatus.WITHDRAWN);
        assertEquals(1, withdrawn.size());

        List<Application> successful = applicationRepository.findByStatus(ApplicationStatus.SUCCESSFUL);
        assertEquals(1, successful.size());

        List<Application> unsuccessful = applicationRepository.findByStatus(ApplicationStatus.UNSUCCESSFUL);
        assertTrue(unsuccessful.isEmpty());
    }
}