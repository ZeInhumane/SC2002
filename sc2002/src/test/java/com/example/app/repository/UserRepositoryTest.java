package com.example.app.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.app.models.MaritalStatus;
import com.example.app.models.Role;
import com.example.app.models.User;

public class UserRepositoryTest {

    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        userRepository = new UserRepository();
    }

    @Test
    public void testFindByNric() {
        User user1 = new User("Alice", "pass123", "alice@example.com", Role.APPLICANT, "S1234567A", 25, MaritalStatus.SINGLE);
        User user2 = new User("Bob", "pass456", "bob@example.com", Role.MANAGER, "S7654321B", 30, MaritalStatus.MARRIED);

        userRepository.save(user1);
        userRepository.save(user2);

        User found = userRepository.findByNric("S1234567A");

        assertNotNull(found);
        assertEquals("Alice", found.getName());
        assertEquals("S1234567A", found.getNric());
    }

    @Test
    public void testFindByNric_NotFound() {
        User user = new User("Charlie", "charliepass", "charlie@example.com", Role.OFFICER, "S0000001X", 28, MaritalStatus.SINGLE);
        userRepository.save(user);

        User result = userRepository.findByNric("S9999999Z");

        assertNull(result);
    }
}
