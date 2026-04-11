package ihromovyi.tacocloud.validation.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HasSpecialSignValidator implements
        ConstraintValidator<HasSpecialSign, String> {
    private static final String PATTERN = "!@#$%^&*()_+-=[]{}|;':,./<>?";

    @Override
    public boolean isValid(
            String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.isEmpty()) {
            return true;
        }

        return value.chars().anyMatch(ch -> PATTERN.indexOf(ch) >= 0);
    }
}

