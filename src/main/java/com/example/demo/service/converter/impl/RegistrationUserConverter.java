package com.example.demo.service.converter.impl;

import com.example.demo.model.User;
import com.example.demo.model.dto.response.RegistrationUserResponse;
import com.example.demo.service.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RegistrationUserConverter implements Converter<User, RegistrationUserResponse> {

    @Override
    public RegistrationUserResponse convert(User source) {
        return RegistrationUserResponse.builder()
                .message("Success")
                .userName(source.getUserName())
                .build();
    }
}
