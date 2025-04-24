package com.example.app.repository;

import com.example.app.models.BaseEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public abstract class GeneralRepositoryTestBase<T extends BaseEntity> {
    protected abstract GeneralRepository<T> getRepository();

    // Return multiple test entities
    protected abstract List<T> createSampleEntities();

    protected abstract List<T> saveSampleEntities() throws IOException;

    @BeforeEach
    public void cleanRepository() throws IOException {
        getRepository().deleteAll();
    }

    @Test
    public void testSaveAndFindById() throws IOException {
        T entity = createSampleEntities().get(0);
        T saved = getRepository().save(entity);
        assertNotNull(saved.getId());

        T found = getRepository().findById(saved.getId());
        assertNotNull(found);
        assertEquals(saved.getId(), found.getId());
    }

    @Test
    public void testFindAll() throws IOException {
        List<T> entities = createSampleEntities();
        for (T entity : entities) {
            getRepository().save(entity);
        }

        List<T> all = getRepository().findAll();
        assertEquals(entities.size(), all.size());
    }

    @Test
    public void testDeleteById() throws IOException {
        T entity = getRepository().save(createSampleEntities().get(0));
        getRepository().deleteById(entity.getId());
        T found = getRepository().findById(entity.getId());
        assertNull(found);
    }

    @Test
    public void testDeleteAll() throws IOException {
        for (T entity : createSampleEntities()) {
            getRepository().save(entity);
        }
        getRepository().deleteAll();
        List<T> all = getRepository().findAll();
        assertTrue(all.isEmpty());
    }
}