package com.example.app.models;

/**
 * BaseEntity interface that defines the basic structure for entities in the application. It includes methods to get and
 * set the ID of the entity and a toString method for string representation.
 *
 */
public interface BaseEntity {

    /**
     * Gets the ID of the entity.
     *
     * @return the ID of the entity
     */
    Integer getId();

    /**
     * Sets the ID of the entity.
     *
     * @param id
     *            the new ID of the entity
     */
    void setId(Integer id);

    /**
     * Returns a string representation of the entity.
     *
     * @return a string representation of the entity
     */
    String toString();
}
