package com.dante.passec.configs;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
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
public class HibernateConfigT {

    private static final String PROP_DATABASE_DRIVER = "org.postgresql.Driver";
    private static final String PROP_DATABASE_PASSWORD = "0000";
    private static final String PROP_DATABASE_URL = "jdbc:postgresql://localhost:5432/passec";
    private static final String PROP_DATABASE_USERNAME = "postgres";
    private static final String PROP_HIBERNATE_DIALECT = "db.hibernate.dialect";
    private static final String PROP_HIBERNATE_SHOW_SQL = "db.hibernate.show_sql";
    private static final String PROP_ENTITYMANAGER_PACKEGES_TO_SCAN = "db.entitymanager.packages.to.scan";
    private static final String PROP_HIBERNATE_HBM2DDL_AUTO = "db.hibernate.hbm2ddl.auto";
    @Resource
    private Environment env;

    /**
     * @return DataSource for hibernate.
     * Properties for the DataSource saving in file app.properties.
     */
    @Bean
    DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty(PROP_DATABASE_DRIVER));
        dataSource.setUrl(env.getProperty(PROP_DATABASE_URL));
        dataSource.setPassword(env.getProperty(PROP_DATABASE_PASSWORD));
        dataSource.setUsername(env.getProperty(PROP_DATABASE_USERNAME));
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
        entityManager.setPackagesToScan(env.getRequiredProperty(PROP_ENTITYMANAGER_PACKEGES_TO_SCAN));
        entityManager.setJpaProperties(hibernateProperties());
        return entityManager;
    }

    /**
     * @return JpaTransactionManager for JpaRepository
     * It take been entityManagerFactory
     */
    @Bean
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
        properties.put(PROP_HIBERNATE_DIALECT, env.getRequiredProperty(PROP_DATABASE_USERNAME));
        properties.put(PROP_HIBERNATE_HBM2DDL_AUTO, env.getRequiredProperty(PROP_HIBERNATE_HBM2DDL_AUTO));
        properties.put(PROP_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROP_HIBERNATE_SHOW_SQL));
        return properties;
    }


}
