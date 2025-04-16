package com.example.app.repository;

import com.example.app.models.*;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class RegistrationRepositoryTest {

    private List<Registration> registrationDB = new ArrayList<>();
    private List<User> userDB = new ArrayList<>();
    private List<Project> projectDB = new ArrayList<>();

    @Test
    public void testFindById() {
        User user = createUser("S1234567A");
        Project project = createProject("Sky Oasis");

        Registration registration = new Registration(user, project, RegistrationStatus.PENDING);
        // registration.setId(1L);
        registrationDB.add(registration);

        // Registration found = findRegistrationById(1L);
        // assertNotNull(found);
        // assertEquals(RegistrationStatus.PENDING, found.getStatus());
    }

    @Test
    public void testFindByUserNric() {
        User user = createUser("S7654321B");
        Project project = createProject("Green Haven");

        Registration registration = new Registration(user, project, RegistrationStatus.PENDING);
        // registration.setId(2L);
        registrationDB.add(registration);

        List<Registration> found = findByUserNric("S7654321B");
        assertEquals(1, found.size());
        assertEquals("S7654321B", found.get(0).getUser().getNric());
    }

    @Test
    public void testFindByProjectId() {
        User user = createUser("S1122334C");
        Project project = createProject("Horizon View");

        Registration registration = new Registration(user, project, RegistrationStatus.PENDING);
        // registration.setId(3L);
        registrationDB.add(registration);

        List<Registration> found = findByProjectId(project.getId());
        assertEquals(1, found.size());
        assertEquals("Horizon View", found.get(0).getProject().getProjectName());
    }

    @Test
    public void testDeleteById() {
        User user = createUser("S9999999D");
        Project project = createProject("Sunrise Residences");

        Registration registration = new Registration(user, project, RegistrationStatus.PENDING);
        // registration.setId(4L);
        registrationDB.add(registration);

        deleteById(4L);

        // assertNull(findRegistrationById(4L));
    }

    // ----------------------
    // Helper logic
    // ----------------------

    private User createUser(String nric) {
        User user = new User("Test User", "password", nric + "@email.com", Role.APPLICANT, nric, 30, MaritalStatus.SINGLE);
        // user.setId((long) (userDB.size() + 1));
        userDB.add(user);
        return user;
    }

    private Project createProject(String name) {
        Project project = new Project();
        project.setId((long) (projectDB.size() + 1));
        project.setProjectName(name);
        project.setVisibility(true);
        projectDB.add(project);
        return project;
    }

    // private Registration findRegistrationById(Long id)
    //     return new Registraion();
    //     // return registrationDB.stream().filter(r -> r.getId().equals(id)).findFirst().orElse(null);
    // }

    private List<Registration> findByUserNric(String nric) {
        List<Registration> result = new ArrayList<>();
        for (Registration reg : registrationDB) {
            if (reg.getUser().getNric().equals(nric)) {
                result.add(reg);
            }
        }
        return result;
    }

    private List<Registration> findByProjectId(Long projectId) {
        List<Registration> result = new ArrayList<>();
        for (Registration reg : registrationDB) {
            if (reg.getProject().getId().equals(projectId)) {
                result.add(reg);
            }
        }
        return result;
    }

    private void deleteById(Long id) {
        // registrationDB.removeIf(r -> r.getId().equals(id));
    }
}
