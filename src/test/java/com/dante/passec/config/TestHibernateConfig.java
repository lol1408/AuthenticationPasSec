package com.dante.passec.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * Created by sergey on 09.01.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfig.class}, loader = AnnotationConfigContextLoader.class)
public class TestHibernateConfig {

    @Test
    public void testWorkingHibernateConfig(){
        //When hibernate config working
    }

}
