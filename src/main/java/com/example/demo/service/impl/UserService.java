package com.example.demo.service.impl;

import com.example.demo.exception.UserException;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.RegistrationUserRequest;
import com.example.demo.model.dto.response.RegistrationUserResponse;
import com.example.demo.model.dto.response.UserDeleteResponse;
import com.example.demo.model.dto.response.UserResponse;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ServiceUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

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
    public UserResponse authorizationUser(String login) {
        User findUser = findUserByLogin(login);
        return getUser(findUser);
    }


    public UserDeleteResponse deleteUser(UUID userId) {
        userRepository.deleteById(userId);
        return getSuccessDeleteUser();
    }

    public User findUserByLogin(String login) {
        return Optional.ofNullable(login)
                .flatMap(userRepository::findByLogin)
                .orElseThrow(() -> new UserException("Пользователь не найден"));
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

    public UserDeleteResponse getSuccessDeleteUser() {
        return UserDeleteResponse.builder()
                .status(String.valueOf(HttpStatus.OK))
                .build();
    }

    public UserResponse getUser(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .status(String.valueOf(HttpStatus.OK))
                .build();
    }
}
