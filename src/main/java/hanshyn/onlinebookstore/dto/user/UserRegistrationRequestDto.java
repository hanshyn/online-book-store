package hanshyn.onlinebookstore.dto.user;

import hanshyn.onlinebookstore.validation.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@FieldMatch(field = "password",
        fieldMatch = "repeatPassword",
        message = "Password does not match")
public class UserRegistrationRequestDto {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Length(min = 8, max = 32)
    private String password;

    @NotBlank
    @Length(min = 8, max = 32)
    private String repeatPassword;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String shippingAddress;
}
