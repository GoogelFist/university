package ua.com.foxminded.university.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Properties;

import static ua.com.foxminded.university.utils.Constants.CREATE_TABLES_SQL_PATH;

@Configuration
@EnableWebMvc
@ComponentScan("ua.com.foxminded.university")
@EnableTransactionManagement
public class WebTestConfig implements WebMvcConfigurer {
    private static final String PACKAGE_NAME_FOR_SCAN = "ua.com.foxminded.university";
    private static final String DIALECT_PROPERTY_NAME = "hibernate.dialect";
    private static final String DIALECT_PROPERTY_VALUE = "org.hibernate.dialect.H2Dialect";
    private static final String SHOW_SQL_PROPERTY_NAME = "hibernate.show_sql";
    private static final String SHOW_SQL_PROPERTY_VALUE = "true";


    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScript(CREATE_TABLES_SQL_PATH)
            .build();
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

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(PACKAGE_NAME_FOR_SCAN);

        Properties properties = new Properties();
        properties.setProperty(DIALECT_PROPERTY_NAME, DIALECT_PROPERTY_VALUE);
        properties.setProperty(SHOW_SQL_PROPERTY_NAME, SHOW_SQL_PROPERTY_VALUE);

        sessionFactory.setHibernateProperties(properties);
        return sessionFactory;
    }

    @Bean
    public PlatformTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }
}
