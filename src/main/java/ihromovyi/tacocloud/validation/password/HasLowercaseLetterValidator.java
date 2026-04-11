package ihromovyi.tacocloud.validation.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HasLowercaseLetterValidator implements
        ConstraintValidator<HasLowercaseLetter, String> {

    @Override
    public boolean isValid(
            String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.isEmpty()) {
            return true;
        }

        return value.chars().anyMatch(Character::isLowerCase);
    }
}

