package hanshyn.onlinebookstore.service.user;

import hanshyn.onlinebookstore.dto.user.UserRegistrationRequestDto;
import hanshyn.onlinebookstore.dto.user.UserResponseDto;
import hanshyn.onlinebookstore.exception.RegistrationException;
import hanshyn.onlinebookstore.mapper.UserMapper;
import hanshyn.onlinebookstore.model.Role;
import hanshyn.onlinebookstore.model.ShoppingCart;
import hanshyn.onlinebookstore.model.User;
import hanshyn.onlinebookstore.repository.role.RoleRepository;
import hanshyn.onlinebookstore.repository.shopping.ShoppingCartRepository;
import hanshyn.onlinebookstore.repository.user.UserRepository;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ShoppingCartRepository shoppingCartRepository;
    private final RoleRepository roleRepository;

    @Transactional
    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException("Can't register user");
        }

        User user = new User();
        user.setEmail(requestDto.getEmail());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setShippingAddress(requestDto.getShippingAddress());

        Role userRole = roleRepository.findByRole(Role.RoleName.USER);

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);

        user.setRoles(userRoles);
        User savedUser = userRepository.save(user);

        createNewShoppingCart(user);

        return userMapper.toDto(savedUser);
    }

    private ShoppingCart createNewShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        return shoppingCartRepository.save(shoppingCart);
    }
}
