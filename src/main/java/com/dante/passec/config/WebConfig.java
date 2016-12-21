package com.dante.passec.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Web config for RESTfull service
 * @author Dante de Braso
 * @version 1.0
 * Created by We on 22.12.2016.
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.dante.passec")
public class WebConfig{


}
