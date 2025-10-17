package com.ocoelhogabriel.microcompany;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
    info = @Info(
        title = "Micro Company API",
        version = "v1",
        description = "API para gerenciamento de empresas"
    )
)
@SpringBootApplication
public class MicroCompanyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroCompanyApplication.class, args);
	}

}
