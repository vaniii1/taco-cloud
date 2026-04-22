package ihromovyi.tacocloud.security;

import ihromovyi.tacocloud.dto.user.UserLoginRequestDto;
import ihromovyi.tacocloud.dto.user.UserLoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UserLoginResponseDto authenticate(UserLoginRequestDto user) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.email(), user.password()));
        String token = jwtUtil.generateToken(authenticate.getName());
        return new UserLoginResponseDto(token);
    }
}
