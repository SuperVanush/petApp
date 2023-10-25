package com.example.demo.servlets;

import com.example.demo.service.ServiceConfiguration;
import org.springframework.context.annotation.Import;


@Import(ServiceConfiguration.class)
public class ServletConfiguration {
}
