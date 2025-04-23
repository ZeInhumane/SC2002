package com.example.app.service;

import com.example.app.enums.*;
import com.example.app.models.*;
import com.example.app.repository.RepositoryDependency;
import com.example.app.service.impl.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static com.example.app.enums.MaritalStatus.*;
import static org.junit.jupiter.api.Assertions.*;

class ApplicantServiceTest {
    private ApplicantServiceImpl service;
    private ApplicationService applicationService;
    private ProjectService projectService;
    private Applicant applicant;

    @BeforeEach
    void setUp() throws IOException {
        RepositoryDependency.getApplicationRepository().deleteAll();
        RepositoryDependency.getProjectRepository().deleteAll();
        RepositoryDependency.getUserRepository().deleteAll();
        RepositoryDependency.getEnquiryRepository().deleteAll();

        service = new ApplicantServiceImpl();
        applicationService = new ApplicationServiceImpl();
        projectService = new ProjectServiceImpl();

        applicant = new Applicant(null, "User", "pw", "user@mail.com", Role.APPLICANT, "S1234567A", 35,
                SINGLE, null, null);
        applicant = (Applicant) RepositoryDependency.getUserRepository().save(applicant);
    }

    @Test
    void getEligibleFlatTypes_variousAgesAndStatus() throws IOException {
        List<FlatType> t1 = service.getEligibleFlatTypes(SINGLE, 35);
        assertEquals(List.of(FlatType._2ROOM), t1);

        List<FlatType> t2 = service.getEligibleFlatTypes(SINGLE, 25);
        assertEquals(List.of(FlatType._2ROOM, FlatType._3ROOM), t2);

        List<FlatType> t3 = service.getEligibleFlatTypes(MARRIED, 22);
        assertEquals(List.of(FlatType._2ROOM, FlatType._3ROOM), t3);

        List<FlatType> t4 = service.getEligibleFlatTypes(SINGLE, 20);
        assertTrue(t4.isEmpty());
    }

    @Test
    void getEligibleFlatTypesForProject_filtersByProjectOfferings() throws IOException {
        Map<FlatType, Integer> flats = Map.of(FlatType._2ROOM, 5);
        Project p = projectService.createProject("TestProj", new Date(System.currentTimeMillis() - 86400000L * 3), new Date(System.currentTimeMillis() + 86400000L * 3), "Hougang",
        1, true, 1, Set.of(), Set.of(MaritalStatus.SINGLE), flats);

        applicant.setAge(30);
    List<FlatType> result = service.getEligibleFlatTypesForProject(applicant, p.getId());
    assertEquals(1, result.size());
    assertEquals(FlatType._2ROOM, result.get(0));
            }

    @Test
    void isAbleToApply_checksStatusCorrectly() throws IOException {
        assertTrue(service.isAbleToApply(applicant));

        Application app = applicationService.save(new Application(null, applicant.getId(), 1, ApplicationStatus.PENDING, false, FlatType._2ROOM));
        applicant.setApplicationId(app.getId());
        RepositoryDependency.getUserRepository().save(applicant);

        assertFalse(service.isAbleToApply(applicant));

        app.setStatus(ApplicationStatus.UNSUCCESSFUL);
        applicationService.save(app);
        assertTrue(service.isAbleToApply(applicant));

        app.setStatus(ApplicationStatus.WITHDRAWN);
        applicationService.save(app);
        assertTrue(service.isAbleToApply(applicant));
    }

    @Test
    void viewCurrentApplication_and_viewCurrentProject() throws IOException {
        assertNull(service.viewCurrentApplication(applicant));
        assertNull(service.viewCurrentProject(applicant));

        Project p = projectService.createProject("Proj", new Date(), new Date(), "Jurong",
                1, true, 1, Set.of(), Set.of(), Map.of(FlatType._2ROOM, 1));

        Application app = applicationService.save(new Application(null, applicant.getId(), p.getId(), ApplicationStatus.PENDING, false, FlatType._2ROOM));
        applicant.setApplicationId(app.getId());
        RepositoryDependency.getUserRepository().save(applicant);

        assertEquals(app.getId(), service.viewCurrentApplication(applicant).getId());
        assertEquals(p.getId(), service.viewCurrentProject(applicant).getId());
    }

    @Test
    void withdrawApplication_setsFlag_andRejectDuplicate() throws IOException {
        assertThrows(IllegalArgumentException.class, () -> service.withdrawApplication(applicant));

        Application app = applicationService.save(new Application(null, applicant.getId(), 1, ApplicationStatus.PENDING, false, FlatType._2ROOM));
        applicant.setApplicationId(app.getId());
        RepositoryDependency.getUserRepository().save(applicant);

        Application result = service.withdrawApplication(applicant);
        assertTrue(result.isRequestWithdrawal());

        assertThrows(IllegalArgumentException.class, () -> service.withdrawApplication(applicant));
    }

    @Test
    void getViewableProjects_basedOnStatusAndVisibility() throws IOException {
        Project visible = projectService.createProject("Visible", new Date(System.currentTimeMillis() - 86400000L * 3), new Date(System.currentTimeMillis() + 86400000L * 3), "Yishun",
                1, true, 2, Set.of(), Set.of(SINGLE), Map.of(FlatType._2ROOM, 5));

        Project hidden = projectService.createProject("Hidden", new Date(System.currentTimeMillis() - 86400000L * 3), new Date(System.currentTimeMillis() + 86400000L * 3), "Yishun",
                1, false, 2, Set.of(), Set.of(SINGLE), Map.of(FlatType._2ROOM, 5));

        List<Project> viewable = service.getViewableProjects(applicant);
        assertEquals(1, viewable.size());
        assertEquals("Visible", viewable.get(0).getProjectName());
    }

    @Test
    void applyForProject_successfulFlow() throws IOException {
        Project project = projectService.createProject("TestApply", new Date(), new Date(), "Tampines",
                1, true, 2, new HashSet<>(), Set.of(SINGLE), Map.of(FlatType._2ROOM, 5));

        assertTrue(service.isAbleToApply(applicant));
        Application app = service.applyForProject(applicant, project.getId(), FlatType._2ROOM);

        assertNotNull(app);
        assertEquals(project.getId(), app.getProjectId());
        assertEquals(FlatType._2ROOM, app.getFlatType());
    }

    @Test
    void updateEnquiryAndDelete_success() throws IOException {
        Project project = projectService.createProject("EnqProj", new Date(), new Date(), "Jurong",
                1, true, 2, new HashSet<>(), Set.of(SINGLE), Map.of(FlatType._2ROOM, 3));

        Enquiry e = service.submitEnquiry(applicant, "Is this open?", project.getId());
        assertEquals("Is this open?", e.getQuestion());

        Enquiry updated = service.updateEnquiry(applicant, e.getId(), "Updated question?");
        assertEquals("Updated question?", updated.getQuestion());

        service.deleteEnquiry(applicant, updated.getId());
        assertNull(RepositoryDependency.getEnquiryRepository().findById(updated.getId()));
    }

    @Test
    void submitAndGetAllEnquiries() throws IOException {
        Project p = projectService.createProject("QueryProj", new Date(), new Date(), "Pasir Ris",
                1, true, 1, Set.of(), Set.of(SINGLE), Map.of(FlatType._2ROOM, 2));
        service.submitEnquiry(applicant, "Q1?", p.getId());
        service.submitEnquiry(applicant, "Q2?", p.getId());

        List<Enquiry> list = service.getAllEnquiries(applicant);
        assertEquals(2, list.size());
    }
}
