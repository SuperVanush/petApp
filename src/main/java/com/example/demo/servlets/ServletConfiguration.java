package com.example.demo.servlets;

import com.example.demo.service.ServiceConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@ComponentScan
@Import(ServiceConfiguration.class)
@Configuration
public class ServletConfiguration {
}
