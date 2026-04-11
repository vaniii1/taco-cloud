package ihromovyi.tacocloud.validation.password;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = HasCapitalLetterValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HasCapitalLetter {
    String message() default "Must contain at least 1 capital letter";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
