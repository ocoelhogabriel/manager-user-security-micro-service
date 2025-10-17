package com.ocoelhogabriel.microauth.infrastructure.persistence.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * JPA entity representing a role in the system.
 */
@Getter
@Entity
@Table(name = "roles")
@EntityListeners(AuditingEntityListener.class)
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Column(unique = true)
    private String name;

    @Size(max = 255)
    private String description;

    @Column(nullable = false)
    private boolean active = true;

    @Column(unique = true)
    private String code;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private final Set<UserEntity> users = new HashSet<>();

    /**
     * Default constructor.
     */
    public RoleEntity() {
    }

    /**
     * Constructor with basic fields.
     *
     * @param name        the role name
     * @param description the role description
     */
    public RoleEntity(String name, String description) {
        this.name = name;
        this.description = description;
        this.active = true;
    }

    /**
     * Constructor with all fields.
     *
     * @param name        the role name
     * @param description the role description
     * @param active      whether the role is active
     * @param code        the role code
     */
    public RoleEntity(String name, String description, boolean active, String code) {
        this.name = name;
        this.description = description;
        this.active = active;
        this.code = code;
    }

    @Override
    public String toString() {
        return "RoleEntity{" + "id=" + id + ", name='" + name + '\'' + ", description='" + description + '\'' + '}';
    }
}
