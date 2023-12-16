package hanshyn.onlinebookstore.mapper;

import hanshyn.onlinebookstore.config.MapperConfig;
import hanshyn.onlinebookstore.dto.user.UserRegistrationRequestDto;
import hanshyn.onlinebookstore.dto.user.UserResponseDto;
import hanshyn.onlinebookstore.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);

    User toModel(UserRegistrationRequestDto registrationRequestDto);
}
