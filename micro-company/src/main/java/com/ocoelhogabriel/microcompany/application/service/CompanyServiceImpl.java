package com.ocoelhogabriel.microcompany.application.service;

import com.ocoelhogabriel.microcompany.domain.entity.Company;
import com.ocoelhogabriel.microcompany.domain.exception.DomainException;
import com.ocoelhogabriel.microcompany.domain.exception.DuplicateResourceException;
import com.ocoelhogabriel.microcompany.domain.exception.ResourceNotFoundException;
import com.ocoelhogabriel.microcompany.domain.repository.CompanyRepository;
import com.ocoelhogabriel.microcompany.domain.service.CompanyService;
import com.ocoelhogabriel.microcompany.infrastructure.amqp.RabbitMQConfig;
import com.ocoelhogabriel.microcompany.interfaces.dto.CompanyEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implementation of CompanyService that handles business logic for Company operations.
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, RabbitTemplate rabbitTemplate) {
        this.companyRepository = companyRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    @Transactional
    public Company registerCompany(Company company) {
        if (!company.isValid()) {
            throw new DomainException("Invalid company data");
        }

        if (companyRepository.existsByCnpj(company.getCnpj())) {
            throw new DuplicateResourceException("Company with CNPJ " + company.getCnpj() + " already exists");
        }

        Company savedCompany = companyRepository.save(company);

        CompanyEvent event = new CompanyEvent(savedCompany.getId(),
                savedCompany.getCnpj(),
                savedCompany.getName(),
                savedCompany.getTradingName(),
                savedCompany.getPhone());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_COMPANY_EVENTS, "company.created", event);

        return savedCompany;
    }

    @Override
    @Transactional
    public Company updateCompany(Company company) {
        if (!company.isValid()) {
            throw new DomainException("Invalid company data");
        }
        if (company.getId() == null) {
            throw new DomainException("Company ID must be provided for update");
        }
        companyRepository.findById(company.getId()).orElseThrow(() -> new ResourceNotFoundException("Company not found with ID: " + company.getId()));
        Optional<Company> existingByCnpj = companyRepository.findByCnpj(company.getCnpj());
        if (existingByCnpj.isPresent() && !existingByCnpj.get().getId().equals(company.getId())) {
            throw new DuplicateResourceException("Company with CNPJ " + company.getCnpj() + " already exists");
        }
        Company updatedCompany = companyRepository.save(company);

        CompanyEvent event = new CompanyEvent(updatedCompany.getId(),
                updatedCompany.getCnpj(),
                updatedCompany.getName(),
                updatedCompany.getTradingName(),
                updatedCompany.getPhone());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_COMPANY_EVENTS, "company.updated", event);

        return updatedCompany;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Company> findById(Long id) {
        return companyRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Company not found with ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Company> findByCnpj(String cnpj) {
        return companyRepository.findByCnpj(cnpj);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Company> findAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteCompany(Long id) {
        // Use getCompanyById to ensure the company exists or throw ResourceNotFoundException
        getCompanyById(id);
        companyRepository.deleteById(id);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_COMPANY_EVENTS, "company.deleted", Map.of("id", id));
    }
}
