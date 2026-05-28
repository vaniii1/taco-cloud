package ihromovyi.tacocloud.controller;

import com.stripe.exception.StripeException;
import ihromovyi.tacocloud.dto.user.UserLoginRequestDto;
import ihromovyi.tacocloud.dto.user.UserLoginResponseDto;
import ihromovyi.tacocloud.dto.user.UserRegistrationRequestDto;
import ihromovyi.tacocloud.dto.user.UserRegistrationResponseDto;
import ihromovyi.tacocloud.security.AuthenticationService;
import ihromovyi.tacocloud.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public UserRegistrationResponseDto createUser(
            @RequestBody @Valid UserRegistrationRequestDto user) throws StripeException {
        return userService.register(user);
    }

    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto user) {
        return authenticationService.authenticate(user);
    }
}
