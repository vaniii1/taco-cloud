package ihromovyi.tacocloud.controller;

import ihromovyi.tacocloud.dto.user.UserRegistrationRequestDto;
import ihromovyi.tacocloud.dto.user.UserRegistrationResponseDto;
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
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public UserRegistrationResponseDto createUser(
            @RequestBody @Valid UserRegistrationRequestDto user) {
        return userService.register(user);
    }
}
