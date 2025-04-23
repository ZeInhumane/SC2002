package com.example.app.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.app.enums.*;
import com.example.app.models.*;
import com.example.app.repository.RepositoryDependency;
import com.example.app.service.impl.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

class ApplicantServiceTest {
    private ApplicantServiceImpl service;
    private ApplicationService applicationService;
    private ProjectService projectService;

    @BeforeEach
    void setUp() throws IOException {
        // wipe out persisted data to avoid collisions
        RepositoryDependency.getApplicationRepository().deleteAll();
        RepositoryDependency.getProjectRepository().deleteAll();

        service = new ApplicantServiceImpl();
        applicationService = new ApplicationServiceImpl();
        projectService = new ProjectServiceImpl();
    }

    @Test
    void getEligibleFlatTypes_variousAgesAndStatus() {
        // SINGLE & age ≥ 35 => only 2‑room
        List<FlatType> t1 = service.getEligibleFlatTypes(MaritalStatus.SINGLE, 35);
        assertEquals(List.of(FlatType._2ROOM), t1);

        // SINGLE & age 25 => age ≥21 branch => both
        List<FlatType> t2 = service.getEligibleFlatTypes(MaritalStatus.SINGLE, 25);
        assertEquals(List.of(FlatType._2ROOM, FlatType._3ROOM), t2);

        // MARRIED & age 22 => age ≥21 branch => both
        List<FlatType> t3 = service.getEligibleFlatTypes(MaritalStatus.MARRIED, 22);
        assertEquals(List.of(FlatType._2ROOM, FlatType._3ROOM), t3);

        // age < 21 => none
        List<FlatType> t4 = service.getEligibleFlatTypes(MaritalStatus.SINGLE, 20);
        assertTrue(t4.isEmpty());
    }

    @Test
    void getEligibleFlatTypesForProject_filtersByProjectOfferings() throws IOException {
        // create a project offering only 2‑room
        Map<FlatType,Integer> flats = Map.of(FlatType._2ROOM, 5);
        Project p = new Project(null, "TestProj",
                new Date(), new Date(), "X-area",
                99, true, 1,
                Collections.emptySet(), Collections.emptySet(), flats);
        // save directly via repo
        p = RepositoryDependency.getProjectRepository().save(p);

        Applicant applicant = new Applicant(null, "U", "pw", "e@e", null,
                "S0000000A", 30, MaritalStatus.SINGLE, null, null);

        // applicant age 30 => eligible for both, but project only has 2‑room
        List<FlatType> result = service.getEligibleFlatTypesForProject(applicant, p.getId());
        assertEquals(1, result.size());
        assertEquals(FlatType._2ROOM, result.get(0));
    }

    @Test
    void isAbleToApply_whenNoApplicationOrFailedOrWithdrawn() throws IOException {
        Applicant a = new Applicant(null, "U", "pw", "e@e", null,
                "S000", 40, MaritalStatus.MARRIED, null, null);

        // no appId => true
        assertTrue(service.isAbleToApply(a));

        // save a PENDING application
        Application app1 = new Application(null, 0, 0,
                ApplicationStatus.PENDING, false, FlatType._2ROOM);
        app1 = applicationService.save(app1);
        a.setApplicationId(app1.getId());
        assertFalse(service.isAbleToApply(a));

        // change to UNSUCCESSFUL => true
        app1.setStatus(ApplicationStatus.UNSUCCESSFUL);
        applicationService.save(app1);
        assertTrue(service.isAbleToApply(a));

        // change to WITHDRAWN => true
        app1.setStatus(ApplicationStatus.WITHDRAWN);
        applicationService.save(app1);
        assertTrue(service.isAbleToApply(a));
    }

    @Test
    void viewCurrentApplication_and_viewCurrentProject() throws IOException {
        Applicant a = new Applicant(null, "U", "pw", "e@e", null,
                "S000", 30, MaritalStatus.SINGLE, null, null);

        // no appId => both return null
        assertNull(service.viewCurrentApplication(a));
        assertNull(service.viewCurrentProject(a));

        // save a project
        Project p = new Project(null, "P", new Date(), new Date(),
                "X", 1, true, 1, Set.of(), Set.of(), Map.of(FlatType._2ROOM,1));
        p = RepositoryDependency.getProjectRepository().save(p);

        // save an application pointing to that project
        Application app = new Application(null, 0, p.getId(),
                ApplicationStatus.PENDING, false, FlatType._2ROOM);
        app = applicationService.save(app);
        a.setApplicationId(app.getId());

        // now both should return non-null
        Application gotApp = service.viewCurrentApplication(a);
        assertNotNull(gotApp);
        assertEquals(app.getId(), gotApp.getId());

        Project gotProj = service.viewCurrentProject(a);
        assertNotNull(gotProj);
        assertEquals(p.getId(), gotProj.getId());
    }

    @Test
    void withdrawApplication_successAndErrors() throws IOException {
        Applicant a = new Applicant(null, "U", "pw", "e@e", null,
                "S000", 30, MaritalStatus.SINGLE, null, null);

        // no application => IllegalArgument
        IllegalArgumentException ex1 = assertThrows(
            IllegalArgumentException.class,
            () -> service.withdrawApplication(a)
        );
        assertTrue(ex1.getMessage().contains("No application"));

        // save a fresh application
        Application app = new Application(null, 0, 0,
                ApplicationStatus.PENDING, false, FlatType._2ROOM);
        app = applicationService.save(app);
        a.setApplicationId(app.getId());

        // withdraw first time => sets requestWithdrawal=true
        Application out = service.withdrawApplication(a);
        assertTrue(out.isRequestWithdrawal());
        // confirm persisted
        Application reloaded = applicationService.findById(out.getId());
        assertTrue(reloaded.isRequestWithdrawal());

        // withdraw again => IllegalArgument about already requested
        IllegalArgumentException ex2 = assertThrows(
            IllegalArgumentException.class,
            () -> service.withdrawApplication(a)
        );
        assertTrue(ex2.getMessage().contains("already been requested"));
    }
}