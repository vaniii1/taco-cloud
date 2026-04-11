package ihromovyi.tacocloud.service.user;

import ihromovyi.tacocloud.dto.user.UserRegistrationRequestDto;
import ihromovyi.tacocloud.dto.user.UserRegistrationResponseDto;
import ihromovyi.tacocloud.exception.UserAlreadyRegisteredException;
import ihromovyi.tacocloud.mapper.UserMapper;
import ihromovyi.tacocloud.model.Role;
import ihromovyi.tacocloud.model.User;
import ihromovyi.tacocloud.repository.RoleRepository;
import ihromovyi.tacocloud.repository.UserRepository;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Pattern ADMIN_PATTERN = Pattern.compile("admin([1-9][0-9]?)@.*");
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserRegistrationResponseDto register(UserRegistrationRequestDto requestDto) {
        verifyValidEmail(requestDto.email());
        User user = userMapper.toEntity(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.password()));
        Set<Role> roles = new HashSet<>();
        boolean isAdmin = ADMIN_PATTERN.matcher(user.getEmail()).matches();

        for (Role role : roleRepository.findAll()) {
            if (role.getRole() == Role.RoleName.USER
                    || (isAdmin && role.getRole() == Role.RoleName.ADMIN)) {
                roles.add(new Role(role.getId()));
            }
        }

        user.setRoles(roles);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    private void verifyValidEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyRegisteredException(
                    "user with " + email + " email is already registered");
        }
    }
}
