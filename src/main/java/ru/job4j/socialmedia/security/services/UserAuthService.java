package ru.job4j.socialmedia.security.services;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.socialmedia.model.User;
import ru.job4j.socialmedia.repository.UserRepository;
import ru.job4j.socialmedia.security.dtos.request.SignupRequestDTO;
import ru.job4j.socialmedia.security.dtos.response.RegisterDTO;
import ru.job4j.socialmedia.model.enums.ERole;
import ru.job4j.socialmedia.model.Role;
import ru.job4j.socialmedia.repository.RoleRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

@Service
@AllArgsConstructor
public class UserAuthService {
    private PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public RegisterDTO signUp(SignupRequestDTO signUpRequest) {
        if (Boolean.TRUE.equals(userRepository.existsByUsername(signUpRequest.getUsername()))
                || Boolean.TRUE.equals(userRepository.existsByEmail(signUpRequest.getEmail()))) {
            return new RegisterDTO(HttpStatus.BAD_REQUEST, "Error: Username or Email is already taken!");
        }
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        Supplier<RuntimeException> supplier = () -> new RuntimeException("Error: Role is not found.");

        if (strRoles == null) {
            roles.add(roleRepository.findByName(ERole.ROLE_USER).orElseThrow(supplier));
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin" -> roles.add(roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(supplier));
                    case "mod" -> roles.add(roleRepository.findByName(ERole.ROLE_MODERATOR).orElseThrow(supplier));
                    default -> roles.add(roleRepository.findByName(ERole.ROLE_USER).orElseThrow(supplier));
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return new RegisterDTO(HttpStatus.OK, "Person registered successfully!");
    }
}
