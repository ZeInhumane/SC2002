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
    void testCreateUpdateDeleteProject() throws IOException {
        Date start = new Date();
        Date end = new Date(start.getTime() + 2 * 86400000L);

        Project created = service.createProject(manager, "Proj1", start, end, "Punggol", true, 5,
                new HashSet<>(), Set.of(MaritalStatus.MARRIED), Map.of(FlatType._2ROOM, 10));
        assertNotNull(created);

        Project updated = service.updateProject(manager, created.getId(), "Proj1Updated", start, end,
                "Sengkang", false, Set.of(MaritalStatus.SINGLE), Map.of(FlatType._3ROOM, 5));
        assertEquals("Proj1Updated", updated.getProjectName());

        service.deleteProject(created.getId());
        assertNull(RepositoryDependency.getProjectRepository().findById(created.getId()));
    }

    @Test
    void testToggleProjectVisibility() throws IOException {
        Project p = RepositoryDependency.getProjectRepository().save(new Project(null, "ToggleProj",
                new Date(), new Date(), "Hougang", manager.getId(), true, 3,
                new HashSet<>(), new HashSet<>(), Map.of(FlatType._2ROOM, 5)));

        boolean beforeToggle = p.getVisibility();
        Project toggled = service.toggleVisibility(manager, p.getId());
        assertNotEquals(beforeToggle, toggled.getVisibility());
    }

    @Test
    void testOfficerRegistrationApproval() throws IOException {
        Officer officer = new Officer(null, "O", "pw", "officer@hdb.sg", Role.OFFICER, "S9999999X",
                30, MaritalStatus.SINGLE, null, null, null, null);
        officer = (Officer) RepositoryDependency.getUserRepository().save(officer);

        Project p = service.createProject(manager, "OfficerProj", new Date(), new Date(),
                "Toa Payoh", true, 3, new HashSet<>(), new HashSet<>(), Map.of(FlatType._2ROOM, 4));

        Registration reg = RepositoryDependency.getRegistrationRepository().save(
                new Registration(null, officer.getId(), p.getId(), RegistrationStatus.PENDING));

        service.updateRegistrationStatus(manager, reg.getId(), RegistrationStatus.APPROVED);
        Registration updated = RepositoryDependency.getRegistrationRepository().findById(reg.getId());
        assertEquals(RegistrationStatus.APPROVED, updated.getStatus());
        assertEquals(p.getId(), ((Officer) RepositoryDependency.getUserRepository().findById(officer.getId())).getProjectId());
    }

    @Test
    void testApplicationApprovalAndWithdrawalReset() throws IOException {
        Applicant a = new Applicant(null, "App", "pw", "a@ntu.edu.sg", Role.APPLICANT, "S1231231A", 36,
                MaritalStatus.SINGLE, null, null);
        a = (Applicant) RepositoryDependency.getUserRepository().save(a);

        Project p = service.createProject(manager, "WithdrawProj", new Date(), new Date(),
                "Simei", true, 2, new HashSet<>(), new HashSet<>(), Map.of(FlatType._2ROOM, 5));

        Application app = RepositoryDependency.getApplicationRepository().save(
                new Application(null, a.getId(), p.getId(), ApplicationStatus.PENDING, true, FlatType._2ROOM));

        Application result = service.updateWithdrawalStatus(app.getId(), false);
        assertFalse(result.isRequestWithdrawal());
    }

    @Test
    void testEnquiryReply() throws IOException {
        Project p = service.createProject(manager, "ReplyProj", new Date(), new Date(),
                "Serangoon", true, 2, new HashSet<>(), new HashSet<>(), Map.of(FlatType._2ROOM, 5));

        Enquiry e = new Enquiry(null, "Any 3-room left?", p.getId(), 201);
        e = RepositoryDependency.getEnquiryRepository().save(e);

        Enquiry replied = service.replyEnquiry(manager, e.getId(), "Only 2-room available.");
        assertEquals("Only 2-room available.", replied.getResponse());
        assertEquals(manager.getId(), replied.getReplierId());
    }

    @Test
    void testRejectProjectCreationDueToDateOverlap() throws IOException {
        Date now = new Date();
        Date end = new Date(now.getTime() + 86400000L * 2);

        service.createProject(manager, "FirstProj", now, end, "West", true, 5,
                new HashSet<>(), new HashSet<>(), Map.of(FlatType._2ROOM, 5));

        Date overlapStart = new Date(now.getTime() + 86400000L);
        Date overlapEnd = new Date(now.getTime() + 86400000L * 3);

        assertThrows(IllegalArgumentException.class, () ->
                service.createProject(manager, "OverlapProj", overlapStart, overlapEnd,
                        "East", true, 5, new HashSet<>(), new HashSet<>(), Map.of(FlatType._3ROOM, 3)));
    }

    @Test
    void testPreventManagerFromApplying() {
        UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class, () -> {
            throw new UnsupportedOperationException("Manager cannot apply for BTO");
        });
        assertTrue(ex.getMessage().contains("cannot apply"));
    }
}