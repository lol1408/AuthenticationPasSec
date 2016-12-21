package com.dante.passec.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * Created by We on 22.12.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class}, loader = AnnotationConfigContextLoader.class)
public class TestConfig {
    @Test
    public void whenSpringContextIsInstantiated_thenNoExceptions(){
        // When
    }
}
