package com.example.demo.service.impl;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.LoginRequest;
import com.example.demo.model.dto.request.UserRequest;
import com.example.demo.model.dto.response.LoginResponse;
import com.example.demo.model.dto.response.UserResponse;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ServiceUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements ServiceUser {

    private final UserRepository userRepository;

    @Override
    public UserResponse addUser(UserRequest request) {
        try {
            String name = request.getName();
            String login = request.getLogin();
            String password = request.getPassword();
            if (userRepository.findByLogin(login).isPresent()) {
                throw new UserNotFoundException("User with this login exist. Enter another login");
            }
            User user = User.builder().username(name).login(login).password(password).build();
            User addUser = userRepository.save(user);
            return getSuccessUserResponse(addUser);
        } catch (UserNotFoundException e) {
            return getErrorUserResponse(e.getMessage());
        }
    }

    @Override
    public LoginResponse findUserByLogin(LoginRequest request) throws UserNotFoundException {
        String login = request.getUserLogin();
        if (userRepository.findByLogin(login).isEmpty()) {
            throw new UserNotFoundException("ЭТО ИСКЛЮЧЕНИЕ");
        }
        User userByLogin = userRepository.findByLogin(login).get();
        return getSuccessLoginResponse(userByLogin);

    }

    public LoginResponse removeUser(LoginRequest request) throws UserNotFoundException {
        String login = request.getUserLogin();
        if (userRepository.findByLogin(login).isEmpty()) {
            throw new UserNotFoundException("ЭТО ИСКЛЮЧЕНИЕ");
        }
        User userByLogin = userRepository.findByLogin(login).get();
        int idRemoveUser = userByLogin.getId();
        userRepository.deleteById(idRemoveUser);
        return getSuccessUserRemoveResponse(login);
    }

    private UserResponse getSuccessUserResponse(User user) {
        return UserResponse.builder()
                .message("Success")
                .name(user.getUsername())
                .build();
    }

    private UserResponse getErrorUserResponse(String message) {
        return UserResponse.builder()
                .message(message)
                .build();
    }

    private LoginResponse getSuccessLoginResponse(User user) {
        return LoginResponse.builder()
                .message("Hello")
                .login(user.getLogin())
                .build();
    }

    private LoginResponse getErrorLoginResponse(String message) {
        return LoginResponse.builder()
                .message(message)
                .build();
    }

    private LoginResponse getSuccessUserRemoveResponse(String login) {
        return LoginResponse.builder()
                .message("Success  remove")
                .login(login)
                .build();
    }

    private LoginResponse getErrorUserRemoveResponse(String message) {
        return LoginResponse.builder()
                .message(message)
                .build();
    }
}
