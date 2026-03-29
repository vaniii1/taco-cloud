package ihromovyi.tacocloud.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class ExpirationDateValidator implements
        ConstraintValidator<ValidExpiration, String> {
    private static final String PATTERN = "^(0[1-9]|1[0-2])/\\d{4}$";

    @Override
    public boolean isValid(
            String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }
        if (!value.matches(PATTERN)) {
            return false;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        YearMonth expiration = YearMonth.parse(value, formatter);
        return !expiration.isBefore(YearMonth.now());
    }
}
