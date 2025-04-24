package com.example.app.service;

import com.example.app.enums.*;
import com.example.app.models.*;
import com.example.app.repository.RepositoryDependency;
import com.example.app.service.impl.ApplicationServiceImpl;
import com.example.app.service.impl.OfficerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class OfficerServiceTest {
    private OfficerService service;
    private Officer officer;
    private ApplicationService applicationService = new ApplicationServiceImpl();

    @BeforeEach
    void setUp() throws IOException {
        RepositoryDependency.getUserRepository().deleteAll();
        RepositoryDependency.getProjectRepository().deleteAll();
        RepositoryDependency.getRegistrationRepository().deleteAll();
        RepositoryDependency.getApplicationRepository().deleteAll();
        RepositoryDependency.getEnquiryRepository().deleteAll();

        service = new OfficerServiceImpl();
        officer = new Officer(null, "officer1", "pw", "o@ntu.sg", Role.OFFICER, "S1111111A",
                30, MaritalStatus.SINGLE, null, null, null, null);
        officer = (Officer) RepositoryDependency.getUserRepository().save(officer);
    }

    @Test
    void registerForProjectAndViewRegistration() throws IOException {
        Project project = RepositoryDependency.getProjectRepository().save(new Project(
                null, "Proj1", new Date(System.currentTimeMillis() - 86400000L), new Date(System.currentTimeMillis() + 86400000L), "Tampines", 100, true, 5,
                new HashSet<>(), new HashSet<>(), Map.of(FlatType._2ROOM, 3)));

        Officer registered = service.registerForProject(officer, project.getId());
        assertNotNull(registered.getRegistrationId());

        Registration reg = service.viewCurrentRegistration(registered);
        assertEquals(project.getId(), reg.getProjectId());
    }

    @Test
    void getRegistrableProjects_filtersCorrectly() throws IOException {
        Project p1 = RepositoryDependency.getProjectRepository().save(new Project(null, "P1",
                new Date(System.currentTimeMillis() - 86400000L), new Date(System.currentTimeMillis() + 86400000L),
                "Yishun", 100, true, 5, new HashSet<>(), new HashSet<>(), Map.of(FlatType._2ROOM, 2)));

        List<Project> registrable = service.getRegistrableProjects(officer);
        assertEquals(1, registrable.size());
        assertTrue(registrable.stream().anyMatch(p -> p.getId().equals(p1.getId())));
    }

    @Test
    void getHandlingEnquiries_returnsCorrectly() throws IOException {
        Project project = RepositoryDependency.getProjectRepository().save(new Project(null, "EProj",
                new Date(System.currentTimeMillis() - 86400000L), new Date(System.currentTimeMillis() + 86400000L), "Bedok", 100, true, 2,
                new HashSet<>(), new HashSet<>(), Map.of(FlatType._2ROOM, 1)));
        officer.setProjectId(project.getId());
        officer = (Officer) RepositoryDependency.getUserRepository().save(officer);

        Enquiry e = RepositoryDependency.getEnquiryRepository().save(new Enquiry(null, "Any left?", project.getId(), 321));
        List<Enquiry> list = service.getHandlingEnquiries(officer);
        assertEquals(1, list.size());
        assertEquals(e.getId(), list.get(0).getId());
    }

    @Test
    void replyEnquiry_success() throws IOException {
        Project project = RepositoryDependency.getProjectRepository().save(new Project(null, "ReplyProj",
                new Date(System.currentTimeMillis() - 86400000L), new Date(System.currentTimeMillis() + 86400000L), "Bishan", 100, true, 3,
                new HashSet<>(), new HashSet<>(), Map.of(FlatType._2ROOM, 2)));
        officer.setProjectId(project.getId());
        officer = (Officer) RepositoryDependency.getUserRepository().save(officer);

        Enquiry e = RepositoryDependency.getEnquiryRepository().save(new Enquiry(null, "Can I apply?", project.getId(), 123));
        Enquiry replied = service.replyEnquiry(officer, e.getId(), "Yes you can.");

        assertEquals("Yes you can.", replied.getResponse());
        assertEquals(officer.getId(), replied.getReplierId());
    }

    @Test
    void bookFlatAndGenerateReceipt_success() throws IOException {
        Applicant applicant = new Applicant(null, "app", "pw", "a@a.com", Role.APPLICANT, "S2222222B", 35,
                MaritalStatus.MARRIED, null, null);
        applicant = (Applicant) RepositoryDependency.getUserRepository().save(applicant);

        Project project = RepositoryDependency.getProjectRepository().save(new Project(null, "BookingProj",
                new Date(System.currentTimeMillis() - 86400000L), new Date(System.currentTimeMillis() + 86400000L), "Woodlands", 100, true, 1,
                new HashSet<>(), new HashSet<>(), Map.of(FlatType._2ROOM, 1)));

        Application app = RepositoryDependency.getApplicationRepository().save(new Application(null,
                applicant.getId(), project.getId(), ApplicationStatus.SUCCESSFUL, false, FlatType._2ROOM));
        applicant.setApplicationId(app.getId());
        RepositoryDependency.getUserRepository().save(applicant);

        officer.setProjectId(project.getId());
        RepositoryDependency.getUserRepository().save(officer);

        service.bookFlatForApplicant(applicant.getId());

        Application updated = RepositoryDependency.getApplicationRepository().findById(app.getId());
        assertEquals(ApplicationStatus.BOOKED, updated.getStatus());

        String receipt = service.generateBookingReceipt(applicant.getId());
        assertTrue(receipt.contains("Booking Receipt"));
        assertTrue(receipt.contains("Woodlands"));
    }

    @Test
    void viewCurrentProject_throwsIfNone() {
        assertThrows(IllegalStateException.class, () -> service.viewHandlingProject(officer));
    }

    @Test
    void getHandlingApplications_returnsCorrectApps() throws IOException {
        Project project = RepositoryDependency.getProjectRepository().save(new Project(null, "AppProj",
                new Date(System.currentTimeMillis() - 86400000L), new Date(System.currentTimeMillis() + 86400000L), "Clementi", 100, true, 3,
                new HashSet<>(), new HashSet<>(), Map.of(FlatType._3ROOM, 2)));
        officer.setProjectId(project.getId());
        RepositoryDependency.getUserRepository().save(officer);

        Applicant a1 = new Applicant(null, "App1", "pw", "1@a.com", Role.APPLICANT, "S0000001A", 35, MaritalStatus.SINGLE, null, null);
        a1 = (Applicant) RepositoryDependency.getUserRepository().save(a1);
        applicationService.save(new Application(null, a1.getId(), project.getId(), ApplicationStatus.PENDING, false, FlatType._3ROOM));

        List<Application> apps = service.getBookingApplications(officer);
        assertEquals(1, apps.size());
    }
}
