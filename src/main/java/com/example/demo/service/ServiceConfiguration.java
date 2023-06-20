package com.example.demo.service;

import com.example.demo.model.JpaConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@ComponentScan
@Import(JpaConfiguration.class)
@Configuration
public class ServiceConfiguration {
}