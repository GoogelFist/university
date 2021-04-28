package ua.com.foxminded.university.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"ua.com.foxminded.university.service", "ua.com.foxminded.university.dao"})
public class ServiceTestConfig {
}