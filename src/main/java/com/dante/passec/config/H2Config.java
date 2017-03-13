/*
package com.dante.passec.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = {"com.dante.passec.db", "com.dante.passec.crypt"}, excludeFilters = { @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Configuration.class)})
@EnableJpaRepositories("com.dante.passec.db.dao")
@EnableTransactionManagement
public class H2Config {
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
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
    */
/**
     * @return JpaTransactionManager for JpaRepository
     * It take been entityManagerFactory
     *//*

    @Bean(name = "transactionManager")
    public JpaTransactionManager jpaTransactionManager(){
        JpaTransactionManager jpaTransaction = new JpaTransactionManager();
        jpaTransaction.setEntityManagerFactory(entityManagerFactory().getObject());
        return jpaTransaction;
    }
    */
/**
     * @return  Properties for hibernate
     * it takes properties for hibernate from 'app.properties' file
     *//*

    public Properties hibernateProperties(){
        Properties properties = new Properties();
        properties.put("hibernate.dialect" , "org.hibernate.dialect.H2Dialect");
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        properties.put("hibernate.show_sql", "true");
        return properties;
    }
}
*/
