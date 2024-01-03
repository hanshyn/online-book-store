package hanshyn.onlinebookstore.controller;

import hanshyn.onlinebookstore.dto.user.UserLoginRequestDto;
import hanshyn.onlinebookstore.dto.user.UserLoginResponseDto;
import hanshyn.onlinebookstore.dto.user.UserRegistrationRequestDto;
import hanshyn.onlinebookstore.dto.user.UserResponseDto;
import hanshyn.onlinebookstore.exception.RegistrationException;
import hanshyn.onlinebookstore.security.AuthenticationService;
import hanshyn.onlinebookstore.service.user.UserService;
import jakarta.validation.Valid;
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
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }

    @PostMapping("/registration")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        return userService.register(requestDto);
    }
}
