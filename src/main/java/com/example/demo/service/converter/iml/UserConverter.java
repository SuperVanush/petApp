package com.example.demo.service.converter.iml;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.converter.Converter;

public class UserConverter implements Converter<User,UserRepository> {
    @Override
    public UserRepository convert(User source) {
        return null;
    }
}
