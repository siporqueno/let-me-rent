package ru.letmerent.core.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.letmerent.core.dto.UserDto;
import ru.letmerent.core.entity.GrantedAuthority;
import ru.letmerent.core.entity.User;
import ru.letmerent.core.exceptions.ResourceNotFoundException;
import ru.letmerent.core.repositories.RoleRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserConverter {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserDto userToUserDtoConverter(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .secondName(user.getSecondName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .userName(user.getUserName())
                .roles(user.getAuthorities()
                        .stream()
                        .map(a -> a.getRole().getRoleName())
                        .collect(Collectors.toList()))
                .build();
    }

    public User userDtoToUserConverter(UserDto userDto) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (!userDto.getRoles().isEmpty() && userDto.getRoles() != null) {
            for (String role : userDto.getRoles()) {
                GrantedAuthority authority = new GrantedAuthority();
                authority.setRole(roleRepository.findByRoleName(role).orElseThrow(() ->
                        new ResourceNotFoundException(String.format("Could not find role %s", role))));
                authorities.add(authority);
            }
        }
        return User.builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .secondName(userDto.getSecondName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .userName(userDto.getUserName())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .authorities(authorities)
                .build();
    }
}
