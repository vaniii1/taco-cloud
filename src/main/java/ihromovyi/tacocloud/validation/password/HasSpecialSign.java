package ihromovyi.tacocloud.validation.password;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = HasSpecialSignValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HasSpecialSign {
    String message() default "Must contain at least 1 special sign";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
