package com.ocoelhogabriel.microauth.domain.entity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Role domain entity representing a role in the system. Roles are used to group permissions and assign them to users.
 */
public class Role {
    private Long id;
    private String name;
    private String description;
    private boolean active;
    private String code;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Default constructor for Role.
     */
    public Role() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Creates a new Role with the specified name.
     *
     * @param name        the name of the role
     * @param description the description of the role
     */
    public Role(String name, String description) {
        this.name = name;
        this.description = description;
        this.active = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Creates a new Role with the specified name, description, active state and code.
     *
     * @param name        the name of the role
     * @param description the description of the role
     * @param active      whether the role is active
     * @param code        the code of the role
     */
    public Role(String name, String description, boolean active, String code) {
        this.name = name;
        this.description = description;
        this.active = active;
        this.code = code;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Creates a Role with an existing ID.
     *
     * @param id          the ID of the role
     * @param name        the name of the role
     * @param description the description of the role
     */
    public Role(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.active = true;
    }

    /**
     * Creates a Role with an existing ID, name, description, active state and code.
     *
     * @param id          the ID of the role
     * @param name        the name of the role
     * @param description the description of the role
     * @param active      whether the role is active
     * @param code        the code of the role
     */
    public Role(Long id, String name, String description, boolean active, String code) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.active = active;
        this.code = code;
    }

    /**
     * Creates a Role with an existing ID and permissions.
     *
     * @param id          the ID of the role
     * @param name        the name of the role
     * @param description the description of the role
     */
    public Role(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.active = true;
    }

    /**
     * Creates a Role with an existing ID, permissions, active state and code.
     *
     * @param id          the ID of the role
     * @param name        the name of the role
     * @param description the description of the role
     * @param active      whether the role is active
     * @param code        the code of the role
     */
    public Role(Long id, String name, String description, boolean active, String code) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.active = active;
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
