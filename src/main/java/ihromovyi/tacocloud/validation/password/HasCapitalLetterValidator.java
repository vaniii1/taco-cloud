package ihromovyi.tacocloud.validation.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HasCapitalLetterValidator implements
        ConstraintValidator<HasCapitalLetter, String> {

    @Override
    public boolean isValid(
            String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.isEmpty()) {
            return true;
        }

        return value.chars().anyMatch(Character::isUpperCase);
    }
}

