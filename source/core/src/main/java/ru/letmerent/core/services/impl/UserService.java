package ru.letmerent.core.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.letmerent.core.converters.UserConverter;
import ru.letmerent.core.dto.UserDto;
import ru.letmerent.core.entity.User;
import ru.letmerent.core.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;

    public User findByUsername(String username) {
        return userRepository.findByUserName(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User %s not found.", username)));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        return new org.springframework.security.core.userdetails.User(user.getUserName(),
                user.getPassword(),
                user.getAuthorities().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRole().getRoleName()))
                        .collect(Collectors.toList()));
    }

    public Boolean existByUsername(String username) {
        return userRepository.existsByUserName(username);
    }

    public Boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Boolean emailBelongsToThisUser (UserDto userDto){
        return findByUsername(userDto.getUserName()).getEmail().equals(userDto.getEmail());
    }

    @Transactional
    public void saveUser(UserDto userDto) {
        if (userRepository.existsById(userDto.getId())) {
            User user = userRepository.getById(userDto.getId());
            user.setFirstName(userDto.getFirstName());
            user.setSecondName(userDto.getSecondName());
            user.setLastName(userDto.getLastName());
            user.setEmail(userDto.getEmail());
            userRepository.save(user);
            return;
        }
        User user = userConverter.userDtoToUserConverter(userDto);
        userRepository.save(user);
    }

    public void deleteUser(String username) {
        userRepository.deleteUserByUserName(username);
    }
}
