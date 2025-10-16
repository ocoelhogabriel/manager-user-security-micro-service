package com.ocoelhogabriel.microcompany.interfaces.mapper;

import com.ocoelhogabriel.microcompany.domain.entity.Company;
import com.ocoelhogabriel.microcompany.infrastructure.persistence.entity.CompanyEntity;
import com.ocoelhogabriel.microcompany.interfaces.dto.CompanyRequest;
import com.ocoelhogabriel.microcompany.interfaces.dto.CompanyResponse;
import com.ocoelhogabriel.microcompany.interfaces.dto.CompanyUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * Consolidated Mapper for Company entity using MapStruct.
 * Handles conversions between Domain, DTOs, and Persistence entities.
 * Note: Method names have been standardized for clarity (e.g., toDomain, toPersistenceEntity).
 * This may require updates in classes that use this mapper.
 */
@Mapper(componentModel = "spring")
public interface CompanyMapper {

    // --- DTO -> Domain ---
    Company toDomain(CompanyRequest request);

    Company toDomain(CompanyUpdateRequest request);

    // --- Domain -> DTO ---
    CompanyResponse toResponse(Company domain);

    List<CompanyResponse> toResponseList(List<Company> domains);

    // --- Persistence -> Domain ---
    Company toDomain(CompanyEntity entity);

    List<Company> toDomainList(List<CompanyEntity> entities);


    // --- Domain -> Persistence ---
    CompanyEntity toPersistenceEntity(Company domain);

    List<CompanyEntity> toPersistenceEntityList(List<Company> domains);


    // --- Update Persistence from Domain ---
    void updatePersistenceEntity(@MappingTarget CompanyEntity entity, Company domain);
}
