package hanshyn.onlinebookstore.service.user;

import hanshyn.onlinebookstore.dto.user.UserRegistrationRequestDto;
import hanshyn.onlinebookstore.dto.user.UserResponseDto;
import hanshyn.onlinebookstore.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto) throws RegistrationException;
}
