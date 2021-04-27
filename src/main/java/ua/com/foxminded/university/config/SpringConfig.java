package ua.com.foxminded.university.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan("ua.com.foxminded.university")
@PropertySource("classpath:database.properties")
public class SpringConfig {

    @Value("${datasource.driver}")
    private String getDriver;

    @Value("${datasource.url}")
    private String getUrl;

    @Value("${datasource.user}")
    private String getUser;

    @Value("${datasource.password}")
    private String getPassword;


    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(getDriver);
        dataSource.setUrl(getUrl);
        dataSource.setUsername(getUser);
        dataSource.setPassword(getPassword);

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    @Scope("prototype")
    public SimpleJdbcInsert jdbcInsert() {
        return new SimpleJdbcInsert(dataSource());
    }
}
