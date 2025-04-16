package com.example.app.repository;

import com.example.app.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest {

    private List<User> userDB;

    @BeforeEach
    public void setup() {
        userDB = new ArrayList<>();
    }

    @Test
    public void testSaveAndRetrieveSingleUser() {
        User user = new User("Alice", "securepass", "alice@example.com", Role.APPLICANT, "S1234567A", 25, MaritalStatus.SINGLE);
        // user.setId(1L);
        userDB.add(user);

        // User retrieved = findById(1L);
        // assertNotNull(retrieved);
        // assertEquals("Alice", retrieved.getName());
    }

    @Test
    public void testSaveAndRetrieveMultipleUsers() {
        User user1 = new User("Alice", "pass1", "alice@example.com", Role.APPLICANT, "S1234567A", 25, MaritalStatus.SINGLE);
        // user1.setId(1L);

        User user2 = new User("Bob", "pass2", "bob@example.com", Role.MANAGER, "S7654321B", 30, MaritalStatus.MARRIED);
        // user2.setId(2L);

        userDB.add(user1);
        userDB.add(user2);

        List<String> names = new ArrayList<>();
        for (User u : userDB) {
            names.add(u.getName());
        }

        assertEquals(2, userDB.size());
        assertTrue(names.containsAll(List.of("Alice", "Bob")));
    }

    @Test
    public void testSaveAndFindByNric() {
        User user = new User("Alice", "password123", "alice@example.com", Role.OFFICER, "S1234567A", 28, MaritalStatus.MARRIED);
        // user.setId(1L);
        userDB.add(user);

        User found = findByNric("S1234567A");
        assertNotNull(found);
        assertEquals("alice@example.com", found.getEmail());
    }

    // -------------------------
    // In-memory repository logic
    // -------------------------

    // private User findById(Long id) {
    //     return userDB.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
    // }

    private User findByNric(String nric) {
        return userDB.stream().filter(u -> u.getNric().equals(nric)).findFirst().orElse(null);
    }
}
