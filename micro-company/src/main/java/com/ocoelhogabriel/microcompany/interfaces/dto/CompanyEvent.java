package com.ocoelhogabriel.microcompany.interfaces.dto;

import java.io.Serializable;

public record CompanyEvent(
        Long id,
        String cnpj,
        String name,
        String tradingName,
        String phone) implements Serializable {
}
