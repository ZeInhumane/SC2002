package com.example.app.repository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.app.models.MaritalStatus;
import com.example.app.models.Project;
import com.example.app.models.Registration;
import com.example.app.models.RegistrationStatus;
import com.example.app.models.Role;
import com.example.app.models.User;

// public class RegistrationRepositoryTest {

//     private RegistrationRepository repo;
//     private UserRepository userRepo;
//     private ProjectRepository projectRepo;

//     @BeforeEach
//     public void setup() {
//         repo = new RegistrationRepository();
//         userRepo = new UserRepository();
//         projectRepo = new ProjectRepository();
//     }

//     @Test
//     public void testFindByUserId() {
//         User user1 = new User("Alice", "pass", "a@email.com", Role.APPLICANT, "S123", 25, MaritalStatus.SINGLE);
//         User user2 = new User("Bob", "pass", "b@email.com", Role.MANAGER, "S456", 35, MaritalStatus.MARRIED);
//         userRepo.save(user1);
//         userRepo.save(user2);

//         Project project = new Project();
//         project.setProjectName("Skyline");
//         projectRepo.save(project);

//         Registration reg1 = new Registration(user1, project, RegistrationStatus.PENDING);
//         Registration reg2 = new Registration(user2, project, RegistrationStatus.SUCCESSFUL);
//         repo.save(reg1);
//         repo.save(reg2);

//         List<Registration> result = repo.findByUserId(user1.getId());
//         assertEquals(1, result.size());
//         assertEquals("S123", result.get(0).getUser().getNric());
//     }

//     @Test
//     public void testFindByProjectId() {
//         User user = new User("Alice", "pass", "a@email.com", Role.APPLICANT, "S123", 25, MaritalStatus.SINGLE);
//         userRepo.save(user);

//         Project project1 = new Project();
//         project1.setProjectName("Skyline");
//         projectRepo.save(project1);

//         Project project2 = new Project();
//         project2.setProjectName("Horizon");
//         projectRepo.save(project2);

//         Registration reg1 = new Registration(user, project1, RegistrationStatus.PENDING);
//         Registration reg2 = new Registration(user, project2, RegistrationStatus.SUCCESSFUL);
//         repo.save(reg1);
//         repo.save(reg2);

//         List<Registration> result = repo.findByProjectId(project2.getId());
//         assertEquals(1, result.size());
//         assertEquals("Horizon", result.get(0).getProject().getProjectName());
//     }
// }
