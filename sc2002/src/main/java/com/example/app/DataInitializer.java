package com.example.app;

import com.example.app.enums.*;
import com.example.app.models.*;
import com.example.app.repository.RepositoryDependency;

import java.util.*;

public class DataInitializer {
    public static void initializeData() {
        try {
            // Clear existing data
            RepositoryDependency.getUserRepository().deleteAll();
            RepositoryDependency.getProjectRepository().deleteAll();
            RepositoryDependency.getApplicationRepository().deleteAll();
            RepositoryDependency.getRegistrationRepository().deleteAll();
            RepositoryDependency.getEnquiryRepository().deleteAll();

            // === Managers ===
            Manager m1 = new Manager(null, "ManagerOne", "pass1", "m1@hdb.sg", Role.MANAGER, "S1000001Z", 40, MaritalStatus.MARRIED);
            Manager m2 = new Manager(null, "ManagerTwo", "pass2", "m2@hdb.sg", Role.MANAGER, "S1000002Z", 42, MaritalStatus.SINGLE);
            RepositoryDependency.getUserRepository().save(m1);
            RepositoryDependency.getUserRepository().save(m2);

            // === Officers ===
            Officer o1 = new Officer(null, "OfficerOne", "pass1", "o1@hdb.sg", Role.OFFICER, "S2000001Z", 35, MaritalStatus.SINGLE, null, null, null, null);
            Officer o2 = new Officer(null, "OfficerTwo", "pass2", "o2@hdb.sg", Role.OFFICER, "S2000002Z", 36, MaritalStatus.MARRIED, null, null, null, null);
            Officer o3 = new Officer(null, "OfficerThree", "pass3", "o3@hdb.sg", Role.OFFICER, "S2000003Z", 33, MaritalStatus.SINGLE, null, null, null, null);
            RepositoryDependency.getUserRepository().save(o1);
            RepositoryDependency.getUserRepository().save(o2);
            RepositoryDependency.getUserRepository().save(o3);

            // === Applicants ===
            Applicant a1 = new Applicant(null, "Alice", "pass1", "alice@ntu.sg", Role.APPLICANT, "S3000001Z", 29, MaritalStatus.SINGLE, FlatType._2ROOM, null);
            Applicant a2 = new Applicant(null, "Bob", "pass2", "bob@ntu.sg", Role.APPLICANT, "S3000002Z", 34, MaritalStatus.MARRIED, FlatType._3ROOM, null);
            Applicant a3 = new Applicant(null, "Charlie", "pass3", "charlie@ntu.sg", Role.APPLICANT, "S3000003Z", 31, MaritalStatus.SINGLE, FlatType._3ROOM, null);
            RepositoryDependency.getUserRepository().save(a1);
            RepositoryDependency.getUserRepository().save(a2);
            RepositoryDependency.getUserRepository().save(a3);

            // === Projects ===
            Project p0 = new Project(null, "Maple Residence", new Date(System.currentTimeMillis() - 86400000L), new Date(System.currentTimeMillis() + 86400000L * 15), "Yishun",
                    m1.getId(), true, 5,
                    new HashSet<>(), Set.of(MaritalStatus.MARRIED), new HashMap<>(Map.of(FlatType._2ROOM, 10, FlatType._3ROOM, 5)));
            RepositoryDependency.getProjectRepository().save(p0);

            Project p1 = new Project(null, "Ocean View", new Date(System.currentTimeMillis() - 86400000L), new Date(System.currentTimeMillis() + 86400000L * 15), "Bedok",
                    m2.getId(), true, 5,
                    new HashSet<>(), Set.of(MaritalStatus.MARRIED, MaritalStatus.SINGLE), Map.of(FlatType._3ROOM, 10));
            RepositoryDependency.getProjectRepository().save(p1);

            Project p2 = new Project(null, "Green Spring", new Date(System.currentTimeMillis() - 86400000L), new Date(System.currentTimeMillis() + 86400000L * 15), "Bedok",
                    m1.getId(), true, 5,
                    new HashSet<>(), Set.of(MaritalStatus.SINGLE), Map.of(FlatType._3ROOM, 8));
            RepositoryDependency.getProjectRepository().save(p2);

            Project p3 = new Project(null, "Sky Meadow", new Date(System.currentTimeMillis() - 86400000L), new Date(System.currentTimeMillis() + 86400000L * 15), "Tampines",
                    m2.getId(), true, 3,
                    new HashSet<>(), Set.of(MaritalStatus.SINGLE, MaritalStatus.MARRIED), new HashMap<>(Map.of(FlatType._2ROOM, 5, FlatType._3ROOM, 7)));
            RepositoryDependency.getProjectRepository().save(p3);

            Project p4 = new Project(null, "Palm Haven", new Date(System.currentTimeMillis() - 86400000L), new Date(System.currentTimeMillis() + 86400000L * 15), "Sengkang",
                    m1.getId(), true, 5,
                    new HashSet<>(), Set.of(MaritalStatus.MARRIED, MaritalStatus.SINGLE), Map.of(FlatType._3ROOM, 5));
            RepositoryDependency.getProjectRepository().save(p4);

            Project p5 = new Project(null, "Cedar Hill", new Date(System.currentTimeMillis() - 86400000L), new Date(System.currentTimeMillis() + 86400000L * 15), "Sengkang",
                    m2.getId(), true, 2,
                    new HashSet<>(), Set.of(MaritalStatus.SINGLE), Map.of(FlatType._2ROOM, 4));
            RepositoryDependency.getProjectRepository().save(p5);

            Project p6 = new Project(null, "Nova Point", new Date(System.currentTimeMillis() - 86400000L), new Date(System.currentTimeMillis() + 86400000L * 15), "Bedok",
                    m1.getId(), true, 5,
                    new HashSet<>(), Set.of(MaritalStatus.MARRIED), Map.of(FlatType._2ROOM, 6, FlatType._3ROOM, 4));
            RepositoryDependency.getProjectRepository().save(p6);

            Project p7 = new Project(null, "Amber Park", new Date(System.currentTimeMillis() - 86400000L), new Date(System.currentTimeMillis() + 86400000L * 15), "Tampines",
                    m2.getId(), true, 4,
                    new HashSet<>(), Set.of(MaritalStatus.MARRIED), Map.of(FlatType._2ROOM, 10, FlatType._3ROOM, 7));
            RepositoryDependency.getProjectRepository().save(p7);

            Project p8 = new Project(null, "Lily Dale", new Date(System.currentTimeMillis() - 86400000L), new Date(System.currentTimeMillis() + 86400000L * 15), "Bedok",
                    m1.getId(), true, 5,
                    new HashSet<>(), Set.of(MaritalStatus.SINGLE), Map.of(FlatType._3ROOM, 10));
            RepositoryDependency.getProjectRepository().save(p8);

            Project p9 = new Project(null, "Sunrise Grove", new Date(System.currentTimeMillis() - 86400000L), new Date(System.currentTimeMillis() + 86400000L * 15), "Tampines",
                    m2.getId(), true, 3,
                    new HashSet<>(), Set.of(MaritalStatus.MARRIED), Map.of(FlatType._2ROOM, 7, FlatType._3ROOM, 4));
            RepositoryDependency.getProjectRepository().save(p9);
            System.out.println("Demo data initialized successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}