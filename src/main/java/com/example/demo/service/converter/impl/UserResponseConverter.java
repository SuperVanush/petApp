package com.example.demo.service.converter.impl;

import com.example.demo.model.User;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.service.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class UserResponseConverter implements Converter<User, UserResponse> {

    @Override
    public UserResponse convert(User source) {
        return UserResponse.builder()
                .userId(source.getId())
                .status(String.valueOf(HttpStatus.OK))
                .build();
    }
}
