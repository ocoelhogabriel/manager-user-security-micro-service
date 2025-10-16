package com.ocoelhogabriel.microcompany.infrastructure.adapter;

import com.ocoelhogabriel.microcompany.domain.entity.Company;
import com.ocoelhogabriel.microcompany.domain.repository.CompanyRepository;
import com.ocoelhogabriel.microcompany.infrastructure.persistence.entity.CompanyEntity;
import com.ocoelhogabriel.microcompany.infrastructure.persistence.repository.CompanyJpaRepository;
import com.ocoelhogabriel.microcompany.interfaces.mapper.CompanyMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CompanyRepositoryAdapter implements CompanyRepository {

    private final CompanyJpaRepository companyJpaRepository;
    private final CompanyMapper companyMapper;

    public CompanyRepositoryAdapter(CompanyJpaRepository companyJpaRepository, CompanyMapper companyMapper) {
        this.companyJpaRepository = companyJpaRepository;
        this.companyMapper = companyMapper;
    }

    @Override
    public Company save(Company company) {
        CompanyEntity companyEntity = companyMapper.toPersistenceEntity(company);
        CompanyEntity savedEntity = companyJpaRepository.save(companyEntity);
        return companyMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Company> findById(Long id) {
        return companyJpaRepository.findById(id).map(companyMapper::toDomain);
    }

    @Override
    public Optional<Company> findByCnpj(String cnpj) {
        return companyJpaRepository.findByCnpj(cnpj).map(companyMapper::toDomain);
    }

    @Override
    public List<Company> findAll() {
        return companyJpaRepository.findAll().stream().map(companyMapper::toDomain).toList();
    }

    @Override
    public void deleteById(Long id) {
        companyJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByCnpj(String cnpj) {
        return companyJpaRepository.existsByCnpj(cnpj);
    }
}
