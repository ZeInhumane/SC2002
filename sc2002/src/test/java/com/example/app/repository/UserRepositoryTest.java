package com.example.app.repository;

import com.example.app.enums.MaritalStatus;
import com.example.app.enums.Role;
import com.example.app.models.Applicant;
import com.example.app.models.Manager;
import com.example.app.models.Officer;
import com.example.app.models.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest extends GeneralRepositoryTestBase<User> {

    private final UserRepository userRepository = RepositoryDependency.getUserRepository();

    @Override
    protected GeneralRepository<User> getRepository() {
        return userRepository;
    }

    @Override
    protected List<User> createSampleEntities() {

        Applicant testApplicant = new Applicant(null, "Alice", "pass123", null, Role.APPLICANT,
                "S1234567A", 25, MaritalStatus.SINGLE, null, null);
        Manager testManager = new Manager(null, "Bob", "pass456", "bob@example.com", Role.MANAGER, "S7654321B", 30,
                MaritalStatus.MARRIED);
        Officer testOfficer = new Officer(null, "Charlie", "pass789", "charlie@example.com", Role.OFFICER, "S9999999C",
                28, MaritalStatus.SINGLE, null, null, null, null);
        return List.of(testApplicant, testManager, testOfficer);
    }

    @Override
    protected List<User> saveSampleEntities() throws IOException {
        List<User> sampleUsers = createSampleEntities();
        for (User user : sampleUsers) {
            userRepository.save(user);
        }
        return sampleUsers;
    }

    @Test
    public void testFindByNric() throws IOException {
        // Save sample users
        saveSampleEntities();
        // Look up Alice by NRIC
        User found = userRepository.findByNric("S1234567A");

        assertNotNull(found);
        assertEquals("Alice", found.getName());
        assertEquals("S1234567A", found.getNric());
        assertEquals(Role.APPLICANT, found.getRole());
    }
}