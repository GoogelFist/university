package ua.com.foxminded.university.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private static final String DATA_SOURCE_NAME = "jdbc/University";

    @Bean
    public DataSource dataSource() {
        JndiDataSourceLookup dataSource = new JndiDataSourceLookup();
        dataSource.setResourceRef(true);
        return dataSource.getDataSource(DATA_SOURCE_NAME);
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