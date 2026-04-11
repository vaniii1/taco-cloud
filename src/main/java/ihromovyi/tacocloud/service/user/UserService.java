package ihromovyi.tacocloud.service.user;

import ihromovyi.tacocloud.dto.user.UserRegistrationRequestDto;
import ihromovyi.tacocloud.dto.user.UserRegistrationResponseDto;
import ihromovyi.tacocloud.model.User;

public interface UserService {
    UserRegistrationResponseDto register(UserRegistrationRequestDto requestDto);

    User getCurrentUser();
}
