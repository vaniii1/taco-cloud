package ihromovyi.tacocloud.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserRegistrationResponseDto(
        String firstName,
        String lastName,
        String country,
        String city,
        @JsonProperty("zip_code")
        String zipCode,
        String street,
        String email,
        @JsonProperty("phone_number")
        String phoneNumber){
}
