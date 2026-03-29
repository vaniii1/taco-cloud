package ihromovyi.tacocloud.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = ExpirationDateValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidExpiration {
    String message() default "Expiration is not in MM/YYYY format or the it is expired";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
