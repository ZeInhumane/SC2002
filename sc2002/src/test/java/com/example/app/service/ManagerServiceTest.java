package com.example.app.service;

import com.example.app.enums.*;
import com.example.app.models.*;
import com.example.app.repository.RepositoryDependency;
import com.example.app.service.impl.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ManagerServiceTest {
    private ManagerServiceImpl service;
    private Manager manager;

    @BeforeEach
    void setUp() throws IOException {
        RepositoryDependency.getProjectRepository().deleteAll();
        RepositoryDependency.getRegistrationRepository().deleteAll();
        RepositoryDependency.getApplicationRepository().deleteAll();
        RepositoryDependency.getUserRepository().deleteAll();
        RepositoryDependency.getEnquiryRepository().deleteAll();

        service = new ManagerServiceImpl();
        manager = new Manager(100, "Mgr", "pw", "m@ntu", Role.MANAGER, "S1234567A", 40, MaritalStatus.MARRIED);
        manager = (Manager) RepositoryDependency.getUserRepository().save(manager);
    }

    @Test
    void testModifyProjectBeforeOpenDate_shouldSucceed() throws IOException {
        // Set open and close dates in the future
        Date futureOpen = new Date(System.currentTimeMillis() + 86400000L * 2); // 2 days from now
        Date futureClose = new Date(System.currentTimeMillis() + 86400000L * 5); // 5 days from now

        // Create a project that hasn't opened yet
        Project created = service.createProject(manager, "FutureProj", futureOpen, futureClose, "Woodlands", true, 4,
                new HashSet<>(), Set.of(MaritalStatus.SINGLE), Map.of(FlatType._3ROOM, 6));
        assertNotNull(created);

        // Modify the project (should succeed)
        Project updated = service.updateProject(manager, created.getId(), "FutureProjUpdated", futureOpen, futureClose,
                "Tampines", false, Set.of(MaritalStatus.MARRIED), Map.of(FlatType._2ROOM, 3));

        assertEquals("FutureProjUpdated", updated.getProjectName());
        assertEquals("Tampines", updated.getNeighborhood());
        assertEquals(FlatType._2ROOM, updated.getFlats().keySet().iterator().next());
    }

    @Test
    void testModifyProjectAfterOpenDate_shouldFail() throws IOException {
        // Set start date to 1 day ago (already open)
        Date start = new Date(System.currentTimeMillis() - 86400000L); // Opened yesterday
        Date end = new Date(System.currentTimeMillis() + 86400000L); // Closes tomorrow

        // Create project (allowed)
        Project created = service.createProject(manager, "Proj1", start, end, "Punggol", true, 5, new HashSet<>(),
                Set.of(MaritalStatus.MARRIED), Map.of(FlatType._2ROOM, 10));
        assertNotNull(created);

        // Try to update project after open date → should fail
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            service.updateProject(manager, created.getId(), "Proj1Updated", start, end, "Sengkang", false,
                    Set.of(MaritalStatus.SINGLE), Map.of(FlatType._3ROOM, 5));
        });
        assertEquals("Cannot edit project after application open date.", exception.getMessage());

        // Deletion should still work
        service.deleteProject(created.getId());
        assertNull(RepositoryDependency.getProjectRepository().findById(created.getId()));
    }

    @Test
    void testToggleProjectVisibility() throws IOException {
        Project p = RepositoryDependency.getProjectRepository()
                .save(new Project(null, "ToggleProj", new Date(), new Date(), "Hougang", manager.getId(), true, 3,
                        new HashSet<>(), new HashSet<>(), Map.of(FlatType._2ROOM, 5)));

        boolean beforeToggle = p.getVisibility();
        Project toggled = service.toggleVisibility(manager, p.getId());
        assertNotEquals(beforeToggle, toggled.getVisibility());
    }

    @Test
    void testOfficerRegistrationApproval() throws IOException {
        Officer officer = new Officer(null, "O", "pw", "officer@hdb.sg", Role.OFFICER, "S9999999X", 30,
                MaritalStatus.SINGLE, null, null, null, null);
        officer = (Officer) RepositoryDependency.getUserRepository().save(officer);

        Project p = service.createProject(manager, "OfficerProj", new Date(), new Date(), "Toa Payoh", true, 3,
                new HashSet<>(), new HashSet<>(), Map.of(FlatType._2ROOM, 4));

        Registration reg = RepositoryDependency.getRegistrationRepository()
                .save(new Registration(null, officer.getId(), p.getId(), RegistrationStatus.PENDING));

        service.updateRegistrationStatus(manager, reg.getId(), RegistrationStatus.APPROVED);
        Registration updated = RepositoryDependency.getRegistrationRepository().findById(reg.getId());
        assertEquals(RegistrationStatus.APPROVED, updated.getStatus());
        assertEquals(p.getId(),
                ((Officer) RepositoryDependency.getUserRepository().findById(officer.getId())).getProjectId());
    }

    @Test
    void testApplicationApprovalAndWithdrawalReset() throws IOException {
        Applicant a = new Applicant(null, "App", "pw", "a@ntu.edu.sg", Role.APPLICANT, "S1231231A", 36,
                MaritalStatus.SINGLE, null, null);
        a = (Applicant) RepositoryDependency.getUserRepository().save(a);

        Project p = service.createProject(manager, "WithdrawProj", new Date(), new Date(), "Simei", true, 2,
                new HashSet<>(), new HashSet<>(), Map.of(FlatType._2ROOM, 5));

        Application app = RepositoryDependency.getApplicationRepository()
                .save(new Application(null, a.getId(), p.getId(), ApplicationStatus.PENDING, true, FlatType._2ROOM));

        Application result = service.updateWithdrawalStatus(app.getId(), false);
        assertFalse(result.isRequestWithdrawal());
    }

    @Test
    void testEnquiryReplyAndGetEnquiries() throws IOException {
        Project p = service.createProject(manager, "ReplyProj", new Date(), new Date(), "Serangoon", true, 2,
                new HashSet<>(), new HashSet<>(), Map.of(FlatType._2ROOM, 5));

        Enquiry e = RepositoryDependency.getEnquiryRepository()
                .save(new Enquiry(null, "Any 3-room left?", p.getId(), 201));

        Enquiry replied = service.replyEnquiry(manager, e.getId(), "Only 2-room available.");
        assertEquals("Only 2-room available.", replied.getResponse());
        assertEquals(manager.getId(), replied.getReplierId());

        List<Enquiry> enquiries = service.getEnquiriesOfProject(manager, p.getId());
        assertEquals(1, enquiries.size());
        assertEquals(e.getId(), enquiries.get(0).getId());
    }

    @Test
    void testGetAllProjectsAndMyProjects() throws IOException {
        Project p1 = service.createProject(manager, "A", new Date(), new Date(), "A1", true, 3, Set.of(), Set.of(),
                Map.of(FlatType._2ROOM, 5));
        List<Project> all = service.getAllProjects();
        assertTrue(all.stream().anyMatch(p -> p.getId().equals(p1.getId())));

        List<Project> mine = service.getMyProjects(manager);
        assertEquals(1, mine.size());
        assertEquals(p1.getId(), mine.get(0).getId());
    }

    @Test
    void testGetApplicationsOfProject() throws IOException {
        Project p = service.createProject(manager, "AppProj", new Date(), new Date(), "Yew Tee", true, 1, Set.of(),
                Set.of(), Map.of(FlatType._2ROOM, 1));

        Applicant a = new Applicant(null, "A", "pw", "a@a.com", Role.APPLICANT, "S1233211A", 34, MaritalStatus.MARRIED,
                null, null);
        a = (Applicant) RepositoryDependency.getUserRepository().save(a);
        Application app = RepositoryDependency.getApplicationRepository()
                .save(new Application(null, a.getId(), p.getId(), ApplicationStatus.PENDING, false, FlatType._2ROOM));

        List<Application> apps = service.getApplicationsOfProject(manager, p.getId());
        assertEquals(1, apps.size());
    }

    @Test
    void testRejectProjectCreationDueToDateOverlap() throws IOException {
        Date now = new Date();
        Date end = new Date(now.getTime() + 86400000L * 2);

        service.createProject(manager, "FirstProj", now, end, "West", true, 5, new HashSet<>(), new HashSet<>(),
                Map.of(FlatType._2ROOM, 5));

        Date overlapStart = new Date(now.getTime() + 86400000L);
        Date overlapEnd = new Date(now.getTime() + 86400000L * 3);

        assertThrows(IllegalStateException.class, () -> service.createProject(manager, "OverlapProj", overlapStart,
                overlapEnd, "East", true, 5, new HashSet<>(), new HashSet<>(), Map.of(FlatType._3ROOM, 3)));
    }

    @Test
    void testDeleteProject_thenAccessFromApplicantAndOfficerFailsGracefully() throws IOException {
        Project p = service.createProject(manager, "DeleteAccessProj", new Date(System.currentTimeMillis() - 86400000L),
                new Date(System.currentTimeMillis() + 86400000L), "Jurong", true, 2, new HashSet<>(),
                Set.of(MaritalStatus.SINGLE), Map.of(FlatType._2ROOM, 3));

        // Create applicant and submit enquiry and application
        Applicant applicant = new Applicant(null, "App", "pw", "app@ntu.sg", Role.APPLICANT, "S1231231Z", 32,
                MaritalStatus.SINGLE, null, null);
        applicant = (Applicant) RepositoryDependency.getUserRepository().save(applicant);

        Enquiry enquiry = RepositoryDependency.getEnquiryRepository()
                .save(new Enquiry(null, "Is this project still open?", p.getId(), applicant.getId()));
        Application application = RepositoryDependency.getApplicationRepository().save(new Application(null,
                applicant.getId(), p.getId(), ApplicationStatus.SUCCESSFUL, false, FlatType._2ROOM));
        applicant.setApplicationId(application.getId());
        RepositoryDependency.getUserRepository().save(applicant);

        // Create officer and register
        Officer officer = new Officer(null, "O", "pw", "officer@hdb.sg", Role.OFFICER, "S1122334Z", 30,
                MaritalStatus.SINGLE, null, null, null, null);
        officer = (Officer) RepositoryDependency.getUserRepository().save(officer);
        Registration registration = RepositoryDependency.getRegistrationRepository()
                .save(new Registration(null, officer.getId(), p.getId(), RegistrationStatus.APPROVED));
        officer.setProjectId(p.getId());
        officer.setRegistrationId(registration.getId());
        RepositoryDependency.getUserRepository().save(officer);

        // Delete the project
        service.deleteProject(p.getId());

        // Attempt access from applicant and officer
        assertThrows(IllegalArgumentException.class, () -> service.getApplicationsOfProject(manager, p.getId()));

        Application loadedApp = RepositoryDependency.getApplicationRepository().findById(application.getId());
        assertEquals(p.getId(), loadedApp.getProjectId());
        assertNull(RepositoryDependency.getProjectRepository().findById(loadedApp.getProjectId()));

        Enquiry loadedEnq = RepositoryDependency.getEnquiryRepository().findById(enquiry.getId());
        assertEquals(p.getId(), loadedEnq.getProjectId());
        assertNull(RepositoryDependency.getProjectRepository().findById(loadedEnq.getProjectId()));

        Registration loadedReg = RepositoryDependency.getRegistrationRepository().findById(registration.getId());
        assertEquals(p.getId(), loadedReg.getProjectId());
        assertNull(RepositoryDependency.getProjectRepository().findById(loadedReg.getProjectId()));

    }

}
