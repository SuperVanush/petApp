package com.example.demo.service.impl;

import com.example.demo.exception.RegistrationException;
import com.example.demo.exception.UserException;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.RegistrationUserRequest;
import com.example.demo.model.dto.response.RegistrationUserResponse;
import com.example.demo.model.dto.response.UserResponse;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ServiceUser;
import com.example.demo.service.converter.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements ServiceUser {

    private final UserRepository userRepository;
    private final Converter<User, RegistrationUserResponse> registrationUserResponseConverter;
    private final Converter<User, UserResponse> userResponseConverter;

    @Override
    public RegistrationUserResponse addUser(RegistrationUserRequest request) {
        String login = request.getLogin();


        if (userRepository.findByLogin(login).isPresent()) {
            throw new RegistrationException("Пользователь с таким логином существует, выберите другой логин");
        }
        User requestUser = User.builder()
                .userName(request.getUserName())
                .login(request.getLogin())
                .password(request.getPassword())
                .build();
        User addUser = userRepository.save(requestUser);
        return registrationUserResponseConverter.convert(addUser);
    }

    @Override
    public UserResponse authorizationUser(String login) {
        User findUser = findUserByLogin(login);
        return userResponseConverter.convert(findUser);
    }

    @Override
    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
    }

    public User findUserByLogin(String login) {
        return Optional.ofNullable(login)
                .flatMap(userRepository::findByLogin)
                .orElseThrow(() -> new UserException("Пользователь не найден"));
    }

    public User findUserById(UUID userId) {
        return Optional.of(userId)
                .flatMap(userRepository::findById)
                .orElseThrow(() -> new UserException("Пользователь не найден"));
    }
}
