package audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;


// @Component annotation registers the class as a Spring Bean in the Spring Application Context.
// So that it can be auto-detected through classpath scanning.And the name "auditorAwareImpl" is the bean name.So that we can inject it wherever needed.
@Component("auditorAwareImpl")// To avoid conflict with Spring Boot's default AuditorAware bean name "auditorAware".We are giving a custom name "auditorAwareImpl"
public class AuditorAwareImpl implements AuditorAware<String> { // Here, we are using String as the type of the user identifier Because our created by and update by is of String type. It can be changed to any other type as per the requirement.

    /* 1. We need to implement the AuditorAware interface and override the getCurrentAuditor() method to return the current user.
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
     */

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("ACCOUNTS_MS"); // Replace "system" with the actual user identifier For e.g., we can get the user from the security context or from a thread-local variable.
    }
}
