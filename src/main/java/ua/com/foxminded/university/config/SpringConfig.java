package ua.com.foxminded.university.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jndi.JndiTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan("ua.com.foxminded.university")
public class SpringConfig {

    private static final String DATA_SOURCE_NAME = "java:comp/env/jdbc/University";

    private static final String PACKAGE_NAME_FOR_SCAN = "ua.com.foxminded.university";
    private static final String DIALECT_PROPERTY_NAME = "hibernate.dialect";
    private static final String DIALECT_PROPERTY_VALUE = "org.hibernate.dialect.PostgreSQL10Dialect";
    private static final String SHOW_SQL_PROPERTY_NAME = "hibernate.show_sql";
    private static final String SHOW_SQL_PROPERTY_VALUE = "true";

    @Bean
    public DataSource dataSource() throws NamingException {
        return (DataSource) new JndiTemplate().lookup(DATA_SOURCE_NAME);
    }

    @Bean
    public JdbcTemplate jdbcTemplate() throws NamingException {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    @Scope("prototype")
    public SimpleJdbcInsert jdbcInsert() throws NamingException {
        return new SimpleJdbcInsert(dataSource());
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() throws NamingException {
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
    public PlatformTransactionManager hibernateTransactionManager() throws NamingException {
        HibernateTransactionManager transactionManager
            = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }
}