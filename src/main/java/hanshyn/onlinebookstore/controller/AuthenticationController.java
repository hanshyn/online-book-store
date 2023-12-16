package hanshyn.onlinebookstore.controller;

import hanshyn.onlinebookstore.dto.user.UserRegistrationRequestDto;
import hanshyn.onlinebookstore.dto.user.UserResponseDto;
import hanshyn.onlinebookstore.exception.RegistrationException;
import hanshyn.onlinebookstore.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;

    @PostMapping("/registration")
    public UserResponseDto registerUser(@RequestBody UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        return userService.register(requestDto);
    }
}
