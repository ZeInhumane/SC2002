package com.example.app.repository;

import com.example.app.enums.FlatType;
import com.example.app.enums.MaritalStatus;
import com.example.app.enums.RegistrationStatus;
import com.example.app.enums.Role;
import com.example.app.models.Applicant;
import com.example.app.models.Project;
import com.example.app.models.Registration;
import com.example.app.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class RegistrationRepositoryTest extends GeneralRepositoryTestBase<Registration> {

    private final RegistrationRepository registrationRepository = new RegistrationRepository();
    private final UserRepository userRepository = RepositoryDependency.getUserRepository();
    private final ProjectRepository projectRepository = RepositoryDependency.getProjectRepository();

    private User user1;
    private User user2;
    private Project project1;
    private Project project2;

    @BeforeEach
    public void setupDependencies() throws IOException {
        // Clean up all involved repositories
        registrationRepository.deleteAll();
        userRepository.deleteAll();
        projectRepository.deleteAll();

        // Save users
        user1 = userRepository.save(new Applicant(null, "Alice", "pass123", "alice@example.com", Role.APPLICANT,
                "S1234567A", 25, MaritalStatus.SINGLE, null, null));
        user2 = userRepository.save(new Applicant(null, "Bob", "pass456", "bob@example.com", Role.APPLICANT,
                "S7654321B", 30, MaritalStatus.MARRIED, null, null));

        // Sample group and flat data
        Set<MaritalStatus> groups = Set.of(MaritalStatus.SINGLE, MaritalStatus.MARRIED);
        Map<FlatType, Integer> flats = Map.of(FlatType._3ROOM, 10, FlatType._2ROOM, 5);
        Date open = new Date();
        Date close = new Date(open.getTime() + 7 * 24 * 60 * 60 * 1000L); // +7 days

        // Save projects
        project1 = projectRepository
                .save(new Project(null, "AI Research", open, close, "Punggol", 123, true, groups, flats));
        project2 = projectRepository
                .save(new Project(null, "Blockchain Project", open, close, "Tampines", 456, true, groups, flats));
    }

    @Override
    protected GeneralRepository<Registration> getRepository() {
        return registrationRepository;
    }

    @Override
    protected List<Registration> createSampleEntities() {
        return List.of(new Registration(null, user1.getId(), project1.getId(), RegistrationStatus.PENDING),
                new Registration(null, user2.getId(), project1.getId(), RegistrationStatus.APPROVED),
                new Registration(null, user1.getId(), project2.getId(), RegistrationStatus.REJECTED));
    }

    @Override
    protected List<Registration> saveSampleEntities() throws IOException {
        List<Registration> registrations = createSampleEntities();
        for (Registration r : registrations) {
            registrationRepository.save(r);
        }
        return registrations;
    }

    @Test
    public void testFindByUserId() throws IOException {
        saveSampleEntities();

        List<Registration> user1Regs = registrationRepository.findByUserId(user1.getId());
        assertEquals(2, user1Regs.size());

        List<Registration> user2Regs = registrationRepository.findByUserId(user2.getId());
        assertEquals(1, user2Regs.size());

        List<Registration> notFoundRegs = registrationRepository.findByUserId(9999);
        assertTrue(notFoundRegs.isEmpty());
    }

    @Test
    public void testFindByProjectId() throws IOException {
        saveSampleEntities();

        List<Registration> project1Regs = registrationRepository.findByProjectId(project1.getId());
        assertEquals(2, project1Regs.size());

        List<Registration> project2Regs = registrationRepository.findByProjectId(project2.getId());
        assertEquals(1, project2Regs.size());

        List<Registration> projectNone = registrationRepository.findByProjectId(9999);
        assertTrue(projectNone.isEmpty());
    }
}