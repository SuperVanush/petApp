package com.example.demo.service.converter;

public interface Converter<S, R> {

    R convert(S source);
}
