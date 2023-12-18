package com.example.demo.service.impl;


import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ServiceUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements ServiceUser {

    private final UserRepository userRepository;

    @Override
    public User addUser(String name, String login, String password) {
        if (userRepository.findByLogin(login).isPresent()) {
            throw new UserNotFoundException("User with this login exist. Enter another login");
        }
        User user = User.builder().username(name).login(login).password(password).build();
        user = userRepository.save(user);

        return user;
    }

    @Override
    public User findUserByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(() -> new UserNotFoundException("User not found by login = " + login));
    }

    @Override
    public String removeUser(String removeUserLogin) {
        int idRemoveUser = findUserByLogin(removeUserLogin).getId();
        userRepository.deleteById(idRemoveUser);
        return removeUserLogin;
    }
}
