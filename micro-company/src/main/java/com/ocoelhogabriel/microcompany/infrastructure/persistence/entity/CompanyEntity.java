package com.ocoelhogabriel.microcompany.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "company")
@Data
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cnpj", nullable = false, unique = true)
    private String cnpj;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "trading_name")
    private String tradingName;

    @Column(name = "phone")
    private String phone;

}
