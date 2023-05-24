package com.example.demo.servlets;

import com.example.demo.service.ServiceConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan
@Import(ServiceConfiguration.class)
public class ServletConfiguration {
}
