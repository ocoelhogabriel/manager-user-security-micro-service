package com.ocoelhogabriel.microcompany.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Company {

    private Long id;
    private String cnpj;
    private String name;
    private String tradingName;
    private String phone;

    // Domain logic methods
    public boolean isValid() {
        return cnpj != null && !cnpj.isBlank() && name != null && !name.isBlank();
    }
}
