package ihromovyi.tacocloud.service.user;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import ihromovyi.tacocloud.client.MyStripeClient;
import ihromovyi.tacocloud.dto.user.UserRegistrationRequestDto;
import ihromovyi.tacocloud.dto.user.UserRegistrationResponseDto;
import ihromovyi.tacocloud.exception.UserAlreadyRegisteredException;
import ihromovyi.tacocloud.mapper.UserMapper;
import ihromovyi.tacocloud.model.Cart;
import ihromovyi.tacocloud.model.Role;
import ihromovyi.tacocloud.model.User;
import ihromovyi.tacocloud.repository.CartRepository;
import ihromovyi.tacocloud.repository.RoleRepository;
import ihromovyi.tacocloud.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
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
    private static final Pattern DEVELOPER_PATTERN = Pattern.compile(".*developer([1-9][0-9]?)@.*");
    private static final Pattern MANAGER_PATTERN = Pattern.compile(".*manager([1-9][0-9]?)@.*");
    private final MyStripeClient myStripeClient;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserRegistrationResponseDto register(UserRegistrationRequestDto requestDto)
            throws StripeException {
        verifyValidEmail(requestDto.email());
        User user = userMapper.toEntity(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.password()));
        List<Role> roles = new ArrayList<>();
        boolean isDeveloper = DEVELOPER_PATTERN.matcher(user.getEmail()).matches();
        boolean isManager = MANAGER_PATTERN.matcher(user.getEmail()).matches();

        for (Role role : roleRepository.findAll()) {
            if (role.getRole() == Role.RoleName.USER
                    || (isDeveloper && role.getRole() == Role.RoleName.DEVELOPER)
                    || (isManager && role.getRole() == Role.RoleName.MANAGER)) {
                roles.add(new Role(role.getId()));
            }
        }
        user.setRoles(roles);

        Customer customer = myStripeClient.createCustomer(user.getFirstName(), user.getLastName(),
                user.getEmail(), user.getCountry(), user.getCity());
        user.setStripeCustomerId(customer.getId());
        userRepository.save(user);

        Cart cart = new Cart();
        cart.setUser(user);
        cartRepository.save(cart);
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
