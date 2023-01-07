package com.example.demo.view;

import com.example.demo.dao.DaoConfiguration;
import com.example.demo.service.ServiceConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@ComponentScan
@Import({ServiceConfiguration.class, DaoConfiguration.class})
@Configuration
public class ViewConfig {
}
