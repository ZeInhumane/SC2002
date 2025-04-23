package com.example.app.service;

import com.example.app.enums.MaritalStatus;
import com.example.app.enums.Role;
import com.example.app.models.Applicant;
import com.example.app.models.Manager;
import com.example.app.models.Officer;
import com.example.app.models.User;
import com.example.app.repository.RepositoryDependency;
import com.example.app.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private UserService service;

    @BeforeEach
    void setUp() throws IOException {
        RepositoryDependency.getUserRepository().deleteAll();
        service = new UserServiceImpl();
    }

    @Test
    void saveAndFindApplicantByIdAndNric() throws IOException {
        Applicant applicant = new Applicant(null, "alice", "pw123", "alice@example.com", Role.APPLICANT, "S1234567A", 25, MaritalStatus.SINGLE, null, null);
        Applicant saved = (Applicant) service.save(applicant);

        assertNotNull(saved.getId());
        Applicant foundById = (Applicant) service.findById(saved.getId());
        Applicant foundByNric = (Applicant) service.findByNric(saved.getNric());

        assertEquals(saved.getId(), foundById.getId());
        assertEquals(saved.getNric(), foundByNric.getNric());
    }

    @Test
    void saveAndFindOfficerByIdAndNric() throws IOException {
        Officer officer = new Officer(null, "bob", "mypassword", "bob@ntu.sg", Role.OFFICER, "S7654321B", 32, MaritalStatus.MARRIED, null, null, null, null);
        Officer saved = (Officer) service.save(officer);

        assertNotNull(saved.getId());
        Officer foundById = (Officer) service.findById(saved.getId());
        Officer foundByNric = (Officer) service.findByNric(saved.getNric());

        assertEquals(saved.getId(), foundById.getId());
        assertEquals(saved.getNric(), foundByNric.getNric());
    }

    @Test
    void saveAndFindManagerByIdAndNric() throws IOException {
        Manager manager = new Manager(null, "clara", "oldpass", "clara@ntu.sg", Role.MANAGER, "S8888888C", 40, MaritalStatus.SINGLE);
        Manager saved = (Manager) service.save(manager);

        assertNotNull(saved.getId());
        Manager foundById = (Manager) service.findById(saved.getId());
        Manager foundByNric = (Manager) service.findByNric(saved.getNric());

        assertEquals(saved.getId(), foundById.getId());
        assertEquals(saved.getNric(), foundByNric.getNric());
    }

    @Test
    void loginSuccessAndFail() throws IOException {
        Officer officer = new Officer(null, "bob", "mypassword", "bob@ntu.sg", Role.OFFICER, "S7654321B", 32, MaritalStatus.MARRIED, null, null, null, null);
        service.save(officer);

        User loginUser = service.login("S7654321B", "mypassword");
        assertNotNull(loginUser);
        assertEquals("bob", loginUser.getName());

        User failedLogin = service.login("S7654321B", "wrongpass");
        assertNull(failedLogin);
    }

    @Test
    void changePasswordUpdatesSuccessfully() throws IOException {
        Manager manager = new Manager(null, "clara", "oldpass", "clara@ntu.sg", Role.MANAGER, "S8888888C", 40, MaritalStatus.SINGLE);
        manager = (Manager) service.save(manager);

        Manager updated = (Manager) service.changePassword(manager, "newpass");
        assertEquals("newpass", updated.getPassword());

        User loginUser = service.login("S8888888C", "newpass");
        assertNotNull(loginUser);
    }
}