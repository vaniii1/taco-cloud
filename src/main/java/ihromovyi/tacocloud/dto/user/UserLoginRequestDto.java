package ihromovyi.tacocloud.dto.user;

import jakarta.validation.constraints.NotNull;

public record UserLoginRequestDto(
        @NotNull
        String email,
        @NotNull
        String password) {
}
