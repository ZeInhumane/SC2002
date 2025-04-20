package com.example.app.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.app.models.MaritalStatus;
import com.example.app.models.Role;
import com.example.app.models.User;

public class GeneralRepositoryTest {

    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        userRepository = new UserRepository();
    }

    @Test
    public void testSaveAndRetrieveSingleUser() {
        User user = new User("Alice", "securepass", "alice@example.com", Role.APPLICANT, "S1234567A", 25, MaritalStatus.SINGLE);
        userRepository.save(user);

        User retrieved = userRepository.findById(user.getId());
        assertNotNull(retrieved);
        assertEquals("Alice", retrieved.getName());
    }

    @Test
    public void testSaveAndRetrieveMultipleUsers() {
        User user1 = new User("Alice", "pass1", "alice@example.com", Role.APPLICANT, "S1234567A", 25, MaritalStatus.SINGLE);
        User user2 = new User("Bob", "pass2", "bob@example.com", Role.MANAGER, "S7654321B", 30, MaritalStatus.MARRIED);

        userRepository.save(user1);
        userRepository.save(user2);

        Collection<User> allUsers = userRepository.findAll();
        List<String> names = new ArrayList<>();
        for (User u : allUsers) {
            names.add(u.getName());
        }

        assertEquals(2, allUsers.size());
        assertTrue(names.containsAll(List.of("Alice", "Bob")));
    }

    @Test
    public void testSaveAndFindByNric() {
        User user = new User("Alice", "password123", "alice@example.com", Role.OFFICER, "S1234567A", 28, MaritalStatus.MARRIED);
        userRepository.save(user);

        User found = findByNric("S1234567A");
        assertNotNull(found);
        assertEquals("alice@example.com", found.getEmail());
    }

    private User findByNric(String nric) {
        return userRepository.findAll().stream().filter(u -> u.getNric().equals(nric)).findFirst().orElse(null);
    }
}
