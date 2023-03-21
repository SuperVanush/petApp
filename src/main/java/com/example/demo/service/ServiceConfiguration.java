package com.example.demo.service;

import com.example.demo.dao.DaoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@ComponentScan
@Import(DaoConfiguration.class)
@Configuration
public class ServiceConfiguration {
}