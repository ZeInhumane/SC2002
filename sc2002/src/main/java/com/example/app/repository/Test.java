package com.example.app.repository;

import com.example.app.models.MaritalStatus;
import com.example.app.models.Role;
import com.example.app.models.User;


public class Test {
    public static void main(String[] args) {
        UserRepository ur = new UserRepository();
        ur.save(new User("Alice", "pass123", "alice@example.com", Role.APPLICANT, "S1234567A", 25, MaritalStatus.SINGLE));
        ur.save(new User("Bob", "bobpass", "bob@example.com", Role.MANAGER, "S7654321B", 35, MaritalStatus.MARRIED));

        for (User user : ur.findAll()) {
            System.out.println("ID: " + user.getId() +
                            ", Name: " + user.getName() +
                            ", NRIC: " + user.getNric() +
                            ", Email: " + user.getEmail() +
                            ", Role: " + user.getRole() +
                            ", Age: " + user.getAge() +
                            ", Marital Status: " + user.getMaritalStatus());
        }
    }
}