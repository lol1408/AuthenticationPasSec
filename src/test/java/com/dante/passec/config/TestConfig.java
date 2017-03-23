package com.dante.passec.config;

import com.dante.passec.configs.HibernateConfigT;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Test web config
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class, HibernateConfigT.class}, loader = AnnotationConfigWebContextLoader.class)
public class TestConfig {
    @Test
    public void whenSpringContextIsInstantiated_thenNoExceptions(){
        //When spring mvc config is working
    }
}
