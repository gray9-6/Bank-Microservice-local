package com.eazybytes.accounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass //This Annotation is used to define a class as a parent/super class for all the entity classes.
@Getter @Setter @ToString
@EntityListeners(AuditingEntityListener.class) // This annotation is used to specify the entity listener class that will be used to listen to the entity lifecycle events. Here, we are using the AuditingEntityListener class provided by Spring Data JPA to automatically populate the auditing fields.For example, it will automatically set the createdAt and updatedAt fields when the entity is created or updated.
public class BaseEntity { // This is the parent class for all the entity classes.It is not a table in the database.It is used to store common fields for all the entity classes.

    @CreatedDate // This annotation is used to mark a field as the creation date of the entity. It is used to automatically set the value of the field when the entity is created. This will be automatically set by Spring Data JPA. It is used in conjunction with @EnableJpaAuditing annotation in the main application class.
    @Column(name = "created_at",updatable = false) // updatable = false means don't include this column in the update query. It will be set only once when the entity is created.
    private LocalDateTime createdAt;

    @CreatedBy // This annotation is used to mark a field as the creator of the entity. It is used to automatically set the value of the field when the entity is created. This will be automatically set by Spring Data JPA. It is used in conjunction with @EnableJpaAuditing annotation in the main application class.
    @Column(name = "created_by",updatable = false) // updatable = false means don't include this column in the update query. It will be set only once when the entity is created.
    private String createdBy;

    @LastModifiedDate // This annotation is used to mark a field as the last modified date of the entity. It is used to automatically set the value of the field when the entity is updated. This will be automatically set by Spring Data JPA. It is used in conjunction with @EnableJpaAuditing annotation in the main application class.
    @Column(name = "updated_at",insertable = false) // means don't include this column in the insert query when the entity is created. It will be set only when the entity is updated.
    private LocalDateTime updatedAt;

    @LastModifiedDate // This annotation is used to mark a field as the last modifier of the entity. It is used to automatically set the value of the field when the entity is updated. This will be automatically set by Spring Data JPA. It is used in conjunction with @EnableJpaAuditing annotation in the main application class.
    @Column(name = "updated_by",insertable = false) // means don't include this column in the insert query when the entity is created. It will be set only when the entity is updated.
    private String updatedBy;
}

/*
    With These 4 fields in the BaseEntity class, we can track the creation and modification details of any entity that extends this class. This is useful for auditing purposes and for maintaining a history of changes made to the entities.
    With these 4 annotations, we are telling Spring Data JPA to automatically populate these fields with the appropriate values when the entity is created or updated.

    1. @CreatedDate: This annotation is used to mark a field as the creation date of the entity. It is used to automatically set the value of the field when the entity is created.
    2. @CreatedBy: This annotation is used to mark a field as the creator of the entity. It is used to automatically set the value of the field when the entity is created.
    3. @LastModifiedDate: This annotation is used to mark a field as the last modified date of the entity. It is used to automatically set the value of the field when the entity is updated.
    4. @LastModifiedBy: This annotation is used to mark a field as the last modifier of the entity. It is used to automatically set the value of the field when the entity is updated.

    Problem:
            1. Spring Data JPA can know the current date and time, so it can automatically set the values for @CreatedDate and @LastModifiedDate annotations. But it does not know who the current user is, so we need to provide a way to get the current user. This can be done by implementing the AuditorAware interface.
            2. By default, Spring Data JPA does not know who the current user is. So, we need to provide a way to get the current user. This can be done by implementing the AuditorAware interface.

    Solution:
            1. Implement the AuditorAware interface and override the getCurrentAuditor() method to return the current user. This can be done by getting the user from the security context or from a thread-local variable.
            2. Register the AuditorAware implementation as a bean in the Spring context.
            3. Enable JPA Auditing in the main application class by adding @EnableJpaAuditing annotation.
    Example:
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.data.domain.AuditorAware;
        import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
        import java.util.Optional;
        @Configuration
        @EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
        public class JpaConfig {
            @Bean
            public AuditorAware<String> auditorProvider() {
                return new AuditorAwareImpl();
            }
        }


        import org.springframework.data.domain.AuditorAware;
        import java.util.Optional;
        @Component("auditorAwareImpl")// To avoid conflict with Spring Boot's default AuditorAware bean name "auditorAware".We are giving a custom name "auditorAwareImpl"

        public class AuditorAwareImpl implements AuditorAware<String> { // Here, we are using String as the type of the user identifier Because our created by and update by is of String type. It can be changed to any other type as per the requirement.
            1. We need to implement the AuditorAware interface and override the getCurrentAuditor() method to return the current user.
            2. In this example, we are returning a hardcoded value. In a real application, we can get the current user from the security context or from a thread-local variable.
            3. The getCurrentAuditor() method should return an Optional object. If the current user is not available, we can return Optional.empty().
            4. The type of the user identifier can be changed as per the requirement.
            5. The AuditorAware implementation should be registered as a bean in the Spring context.
            6. The @EnableJpaAuditing annotation should be added to the main application class to enable JPA Auditing.
            7. The auditorAwareRef attribute of the @EnableJpaAuditing annotation should be set to the name of the AuditorAware bean.
            8. The @CreatedBy and @LastModifiedBy annotations will now be automatically populated with the current user when the entity is created or updated.
            9. The @CreatedDate and @LastModifiedDate annotations will be automatically populated with the current date and time when the entity is created or updated.
            10. The BaseEntity class can now be extended by any entity class to inherit the auditing fields.
            11. The auditing fields will be automatically populated when the entity is created or updated.
            12. The auditing fields can be used for auditing purposes and for maintaining a history of changes made to the entities.
            @Override
            public Optional<String> getCurrentAuditor() {
                // Here, we can get the current user from the security context or from a thread-local variable.
                // For simplicity, we are returning a hardcoded value.
                return Optional.of("system"); // Replace "system" with the actual user identifier
            }
        }

    Note: To make these annotations work, we need to enable JPA Auditing in the main application class by adding @EnableJpaAuditing annotation.

        Example:
        @SpringBootApplication
        @EnableJpaAuditing
        public class MyApplication {
            public static void main(String[] args) {
                SpringApplication.run(MyApplication.class, args);
            }
        }
 */