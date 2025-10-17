package com.eazybytes.accounts;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication //spring boot application annotation. It indicates that this is a Spring Boot application and triggers auto-configuration, component scanning, and other features of Spring Boot.

/*@ComponentScans({ @ComponentScan("com.eazybytes.accounts.controller") }) //component scan annotation. It specifies the base packages to scan for Spring components (like @Controller, @Service, @Repository, etc.). In this case, it scans the "com.eazybytes.accounts.controller" package for controller components.
@EnableJpaRepositories("com.eazybytes.accounts.repository") //JPA repositories annotation. It enables the creation of JPA repositories and specifies the base package to scan for repository interfaces. In this case, it scans the "com.eazybytes.accounts.repository" package for repository components.
@EntityScan("com.eazybytes.accounts.model")*/ //entity scan annotation. It specifies the base package to scan for JPA entity classes. In this case, it scans the "com.eazybytes.accounts.model" package for entity components.

@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl") //enables JPA Auditing in the application. It allows automatic population of auditing-related fields (like createdBy, createdDate, lastModifiedBy, lastModifiedDate) in JPA entities. The auditorAwareRef attribute specifies the bean name of the AuditorAware implementation that provides the current auditor information.
@OpenAPIDefinition( //swagger documentation annotation. It marks the application as a OpenAPI definition
		info = @Info( //swagger info annotation. It defines the metadata for the API documentation. It includes details such as title, version, description, contact information, and license information
				title = "Accounts Microservice API", //title of the API
				version = "1.0", //version of the API
				description = "API Documentation for Accounts Microservice", //description of the API
				contact = @Contact( //contact information for the API. It provides details about the person or organization responsible for the API
						name = "Ajay Yadav", //name of the contact person
						email = "ajay@gmail.com", //email of the contact person
						url = "https://www.bluesolutions.com" //url of the contact person
				),
				license = @License(  //license information for the API. It defines the license under which the API is released
						name = "Apache 2.0", //name of the license. It specifies the type of license
						url = "https://www.eazybytes.com" //url of the license. It points to the location where the license details can be found
				)
		),
		externalDocs = @ExternalDocumentation( //external documentation annotation. It provides additional documentation related to the API. It includes a description and a URL pointing to the external documentation
				description =  "EazyBank Accounts microservice REST API Documentation", //description of the external documentation. It provides a brief overview of the content or purpose of the documentation
				url = "https://www.eazybytes.com/swagger-ui.html" //url of the external documentation. It points to the location where the documentation can be accessed
		)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
