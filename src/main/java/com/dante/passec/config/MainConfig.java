package com.dante.passec.config;

import com.dante.passec.crypt.CryptService;
import com.dante.passec.crypt.CryptServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * Main config
 * @author Dante de Braso
 * @version 0.1
 */
@Configuration
@ComponentScan(basePackages = {"com.dante.passec"}, excludeFilters = { @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Configuration.class)})
public class MainConfig {
    @Bean
    public CryptService helloCrypt() throws Exception {
        return new CryptServiceImpl();
    }
}
