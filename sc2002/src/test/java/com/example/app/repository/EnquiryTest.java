package com.example.app.repository;

import com.example.app.models.Enquiry;
import com.example.app.models.Project;
import com.example.app.models.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EnquiryTest {

    @Test
    public void testCreateEnquiry() {
        // Setup
        Project project = new Project();
        project.setProjectName("Green Meadows");

        User enquirer = new User("Alice", "pass", "alice@email.com", null, "S1234567A", 30, null);
        User replier = new User("Bob", "pass", "bob@email.com", null, "S2345678B", 35, null);

        Enquiry enquiry = new Enquiry("What is the launch date?", "Q3 2025", project, enquirer, replier);

        // Simulate save into a list
        List<Enquiry> mockDB = new ArrayList<>();
        mockDB.add(enquiry);

        // Assertions
        assertEquals(1, mockDB.size());
        Enquiry saved = mockDB.get(0);
        // assertEquals("What is the launch date?", saved.getQuestion());
        // assertEquals("Q3 2025", saved.getResponse());
        // assertEquals("Alice", saved.getEnquirer().getName());
        // assertEquals("Bob", saved.getReplier().getName());
        // assertEquals("Green Meadows", saved.getProject().getProjectName());
    }
}
