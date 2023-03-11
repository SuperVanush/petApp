package com.example.demo.servlets;

import com.example.demo.view.ViewConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@ComponentScan
@Import(ViewConfig.class)
@Configuration
public class ServletConfiguration {
}
