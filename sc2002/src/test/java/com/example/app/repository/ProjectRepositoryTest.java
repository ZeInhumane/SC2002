package com.example.app.repository;

import com.example.app.enums.FlatType;
import com.example.app.enums.MaritalStatus;
import com.example.app.models.Project;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectRepositoryTest extends GeneralRepositoryTestBase<Project> {

    private final ProjectRepository projectRepository = RepositoryDependency.getProjectRepository();

    @Override
    protected GeneralRepository<Project> getRepository() {
        return projectRepository;
    }

    @Override
    protected List<Project> createSampleEntities() {
        Set<MaritalStatus> groups1 = new HashSet<>(Set.of(MaritalStatus.SINGLE, MaritalStatus.MARRIED));
        Map<FlatType, Integer> flats1 = new HashMap<>(Map.of(FlatType._2ROOM, 10, FlatType._3ROOM, 5));

        Set<MaritalStatus> groups2 = new HashSet<>(Set.of(MaritalStatus.SINGLE));
        Map<FlatType, Integer> flats2 = new HashMap<>(Map.of(FlatType._2ROOM, 8));

        Set<MaritalStatus> groups3 = new HashSet<>();
        Map<FlatType, Integer> flats3 = new HashMap<>(Map.of(FlatType._3ROOM, 12));

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 30);
        Date futureDate = calendar.getTime();

        Project project1 = new Project(null, "Sunshine Grove", today, futureDate, "Tampines", 101, true, groups1, flats1);
        Project project2 = new Project(null, "RiverEdge", today, futureDate, "Sengkang", 102, true, groups2, flats2);
        Project project3 = new Project(null, "Skyline Heights", today, futureDate, "Jurong", 103, true, groups3, flats3);

        return List.of(project1, project2, project3);
    }

    @Override
    protected List<Project> saveSampleEntities() throws IOException {
        List<Project> projects = createSampleEntities();
        for (Project p : projects) {
            projectRepository.save(p);
        }
        return projects;
    }

    // Optional: Test domain logic inside Project
    @Test
    public void testDecrementFlatCount() throws IOException {
        Project project = saveSampleEntities().get(0);
        assertTrue(project.hasFlatLeft(FlatType._3ROOM));
        assertDoesNotThrow(() -> project.decrementFlatCount(FlatType._3ROOM));
    }

    @Test
    public void testDecrementFlatCount_noFlatsLeft() {
        Project project = new Project();
        Map<FlatType, Integer> flats = new HashMap<>();
        flats.put(FlatType._2ROOM, 0);
        project.setFlats(flats);

        assertThrows(IllegalStateException.class, () -> project.decrementFlatCount(FlatType._2ROOM));
    }

    @Test
    public void testDecrementFlatCount_flatTypeNotPresent() {
        Project project = new Project(); // no flats at all
        assertThrows(IllegalArgumentException.class, () -> project.decrementFlatCount(FlatType._2ROOM));
    }
}