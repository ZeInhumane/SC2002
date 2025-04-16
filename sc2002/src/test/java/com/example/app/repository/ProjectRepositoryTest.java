package com.example.app.repository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.app.models.MaritalStatus;
import com.example.app.models.Project;

public class ProjectRepositoryTest {

    private ProjectRepository projectRepo;

    @BeforeEach
    public void setup() {
        projectRepo = new ProjectRepository();
    }

    @Test
    public void testSaveAndFindById() {
        Project project = new Project();
        project.setProjectName("Green Haven");
        project.setGroup(MaritalStatus.MARRIED);
        project.setVisibility(true);

        projectRepo.save(project);
        Project fetched = projectRepo.findById(project.getId());

        assertNotNull(fetched);
        assertEquals("Green Haven", fetched.getProjectName());
    }

    @Test
    public void testFindByMaritalStatusAndVisibility() {
        Project marriedVisible = new Project();
        marriedVisible.setProjectName("SkyView");
        marriedVisible.setGroup(MaritalStatus.MARRIED);
        marriedVisible.setVisibility(true);

        Project singleVisible = new Project();
        singleVisible.setProjectName("SoloPlace");
        singleVisible.setGroup(MaritalStatus.SINGLE);
        singleVisible.setVisibility(true);

        Project marriedHidden = new Project();
        marriedHidden.setProjectName("SecretMarried");
        marriedHidden.setGroup(MaritalStatus.MARRIED);
        marriedHidden.setVisibility(false);

        projectRepo.save(marriedVisible);
        projectRepo.save(singleVisible);
        projectRepo.save(marriedHidden);

        List<Project> result = projectRepo.findByMaritalStatusAndVisibility(MaritalStatus.MARRIED, true);
        assertEquals(1, result.size());
        assertEquals("SkyView", result.get(0).getProjectName());
    }

    @Test
    public void testFindByVisibility() {
        Project visible1 = new Project();
        visible1.setProjectName("Open1");
        visible1.setVisibility(true);

        Project visible2 = new Project();
        visible2.setProjectName("Open2");
        visible2.setVisibility(true);

        Project hidden = new Project();
        hidden.setProjectName("HiddenGem");
        hidden.setVisibility(false);

        projectRepo.save(visible1);
        projectRepo.save(visible2);
        projectRepo.save(hidden);

        List<Project> visibleProjects = projectRepo.findByVisibility(true);
        assertEquals(2, visibleProjects.size());
        assertTrue(visibleProjects.stream().anyMatch(p -> p.getProjectName().equals("Open1")));
        assertTrue(visibleProjects.stream().anyMatch(p -> p.getProjectName().equals("Open2")));

        List<Project> hiddenProjects = projectRepo.findByVisibility(false);
        assertEquals(1, hiddenProjects.size());
        assertEquals("HiddenGem", hiddenProjects.get(0).getProjectName());
    }
}
