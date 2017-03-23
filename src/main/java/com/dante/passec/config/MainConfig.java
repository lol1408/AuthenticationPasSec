package com.dante.passec.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;


/**
 * Main config
 * @author Dante de Braso
 * @version 0.1
 */
@Configuration
@ComponentScan(basePackages = {"com.dante.passec.crypt"}, excludeFilters = { @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Configuration.class)})
public class MainConfig {

}
