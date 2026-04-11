package ihromovyi.tacocloud.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import ihromovyi.tacocloud.validation.password.HasCapitalLetter;
import ihromovyi.tacocloud.validation.password.HasLowercaseLetter;
import ihromovyi.tacocloud.validation.password.HasSpecialSign;
import ihromovyi.tacocloud.validation.password.PasswordMatch;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@PasswordMatch(password = "password", confirmPassword = "repeatPassword")
public record UserRegistrationRequestDto(
        @NotNull
        @JsonProperty("first_name")
        String firstName,
        @NotNull
        @JsonProperty("last_name")
        String lastName,
        String country,
        String city,
        @JsonProperty("zip_code")
        String zipCode,
        String street,
        @NotNull
        String email,
        @JsonProperty("phone_number")
        String phoneNumber,
        @NotNull
        @Size(min = 8)
        @HasCapitalLetter
        @HasLowercaseLetter
        @HasSpecialSign
        String password,
        @NotNull
        @JsonProperty("repeat_password")
        String repeatPassword
) {
}
