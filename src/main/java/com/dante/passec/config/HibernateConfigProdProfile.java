package com.dante.passec.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;


@Profile(value = "proxd")
@Configuration
@EnableTransactionManagement
public class HibernateConfigProdProfile {

    @Value("${prod.datasource.url}")
    String url;
    @Value("${prod.datasource.username}")
    String userName;
    @Value("${prod.datasource.password}")
    String password;
    @Value("${prod.datasource.driver-class-name}")
    String driverClassName;

    @Value("${prod.jpa.hibernate.ddl-auto}")
    String ddlAuto;
    @Value("${prod.jpa.show-sql}")
    String showSql;
    @Value("${prod.hibernate.dialect}")
    String hibernateDialect;

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(url);
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource());
        entityManager.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManager.setPackagesToScan("com.dante.passec.model");
        entityManager.setJpaProperties(hibernateProperties());
        return entityManager;
    }

    @Bean
    public JpaTransactionManager transactionManager(){
        JpaTransactionManager jpaTransaction = new JpaTransactionManager();
        jpaTransaction.setEntityManagerFactory(entityManagerFactory().getObject());
        return jpaTransaction;
    }
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.show_sql", showSql);
        properties.put("hibernate.hbm2ddl.auto", ddlAuto);
        properties.put("hibernate.dialect", hibernateDialect);
        return properties;
    }

}
