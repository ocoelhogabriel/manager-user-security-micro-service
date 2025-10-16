package com.ocoelhogabriel.microcompany.domain.repository;

import com.ocoelhogabriel.microcompany.domain.entity.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository {

    /**
     * Save or update a company
     *
     * @param company The company to save or update
     * @return The saved company with ID
     */
    Company save(Company company);

    /**
     * Find a company by its ID
     *
     * @param id The company ID
     * @return An Optional containing the company if found
     */
    Optional<Company> findById(Long id);

    /**
     * Find a company by its CNPJ
     *
     * @param cnpj The company CNPJ
     * @return An Optional containing the company if found
     */
    Optional<Company> findByCnpj(String cnpj);

    /**
     * Find all companies
     *
     * @return A list of all companies
     */
    List<Company> findAll();

    /**
     * Delete a company by its ID
     *
     * @param id The company ID
     */
    void deleteById(Long id);

    /**
     * Check if a company exists by CNPJ
     *
     * @param cnpj The company CNPJ
     * @return true if exists, false otherwise
     */
    boolean existsByCnpj(String cnpj);
}
