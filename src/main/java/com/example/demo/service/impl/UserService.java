package com.example.demo.service.impl;

import com.example.demo.exception.UserException;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.LoginRequest;
import com.example.demo.model.dto.request.RegistrationUserRequest;
import com.example.demo.model.dto.response.LoginResponse;
import com.example.demo.model.dto.response.RegistrationUserResponse;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ServiceUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements ServiceUser {

    private final UserRepository userRepository;

    @Override
    public RegistrationUserResponse addUser(RegistrationUserRequest request) {
        String login = request.getLogin();
        try {
            if (userRepository.findByLogin(login).isPresent()) {
                throw new UserException("Пользователь с таким логином существует, выберите другой логин");
            }
            User requestUser = User.builder()
                    .userName(request.getUserName())
                    .login(request.getLogin())
                    .password(request.getPassword())
                    .build();
            User addUser = userRepository.save(requestUser);
            return getSuccessAddUser(addUser);
        } catch (UserException e) {
            return getErrorAddUser(e.getMessage());
        }
    }

    @Override
    public User authorizationUser(LoginRequest request) {
        User user = Optional.ofNullable(request)
                .map(LoginRequest::getLogin)
                .flatMap(userRepository::findByLogin)
                .orElseThrow(() -> new UserException("Пользователь не найден"));
        return user;
    }

    public LoginResponse deleteUser(LoginRequest request) {
        int userId = authorizationUser(request).getUserId().compareTo(authorizationUser(request).getUserId());
        userRepository.deleteById(userId);
        return getSuccessDeleteUser();
    }

    public RegistrationUserResponse getSuccessAddUser(User user) {
        return RegistrationUserResponse.builder()
                .message("Success")
                .userName(user.getUserName())
                .build();
    }

    public RegistrationUserResponse getErrorAddUser(String message) {
        return RegistrationUserResponse.builder()
                .message(message)
                .build();
    }

    public LoginResponse getSuccessDeleteUser() {
        return LoginResponse.builder()
                .message("Success")
                .build();
    }
}
