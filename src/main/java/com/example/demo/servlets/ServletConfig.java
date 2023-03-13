package com.example.demo.servlets;

import com.example.demo.dao.impl.BillStorage;
import com.example.demo.dao.impl.UserStorage;
import com.example.demo.service.impl.BillService;
import com.example.demo.service.impl.UserService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@ComponentScan
@Configuration
@Import({UserService.class, UserStorage.class, BillService.class, BillStorage.class})
public class ServletConfig {
}
