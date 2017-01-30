package com.dante.passec.configs;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Hibernate configuration for tests
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.dante.passec.services"})
@EnableJpaRepositories("com.dante.passec.dao")
public class HibernateConfigT {

    private static final String PROP_DATABASE_DRIVER = "org.postgresql.Driver";
    private static final String PROP_DATABASE_PASSWORD = "0000";
    private static final String PROP_DATABASE_URL = "jdbc:postgresql://localhost:5432/test";
    private static final String PROP_DATABASE_USERNAME = "postgres";
    private static final String PROP_HIBERNATE_DIALECT = "org.hibernate.dialect.PostgreSQL92Dialect";
    private static final String PROP_HIBERNATE_SHOW_SQL = "true";
    private static final String PROP_ENTITYMANAGER_PACKAGES_TO_SCAN = "com.dante.passec.model";
    private static final String PROP_HIBERNATE_HBM2DDL_AUTO = "create-drop";
    @Resource
    private Environment env;

    /**
     * @return DataSource for hibernate.
     * Properties for the DataSource saving in file app.properties.
     */
    @Bean
    DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(PROP_DATABASE_DRIVER);
        dataSource.setUrl(PROP_DATABASE_URL);
        dataSource.setPassword(PROP_DATABASE_PASSWORD);
        dataSource.setUsername(PROP_DATABASE_USERNAME);
        return dataSource;
    }

    /**
     * @return EntityManager. set DataSource, PersistenceProvider, PackagesToScan and JpaProperties
     * It get DataSource from bean
     * It get PersistenceProvider from HibernatePersistenceProvider.class
     * It get PackagesToScan from app.properties file
     * It get JpaProperties from method of the class
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource());
        entityManager.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManager.setPackagesToScan(PROP_ENTITYMANAGER_PACKAGES_TO_SCAN);
//        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        entityManager.setJpaVendorAdapter(vendorAdapter);
        entityManager.setJpaProperties(hibernateProperties());
        return entityManager;
    }
    /**
     * @return JpaTransactionManager for JpaRepository
     * It take been entityManagerFactory
     */
    @Bean(name = "transactionManager")
    public JpaTransactionManager jpaTransactionManager(){
        JpaTransactionManager jpaTransaction = new JpaTransactionManager();
        jpaTransaction.setEntityManagerFactory(entityManagerFactory().getObject());
        return jpaTransaction;
    }
    /**
     * @return  Properties for hibernate
     * it takes properties for hibernate from 'app.properties' file
     */
    public Properties hibernateProperties(){
        Properties properties = new Properties();
        properties.put("hibernate.dialect" , PROP_HIBERNATE_DIALECT);
        properties.put("hibernate.hbm2ddl.auto", PROP_HIBERNATE_HBM2DDL_AUTO);
        properties.put("hibernate.show_sql", PROP_HIBERNATE_SHOW_SQL);
        return properties;
    }
}
