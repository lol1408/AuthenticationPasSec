package com.dante.passec.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


/**
 * Initializer for api
 */
public class Initializer extends AbstractAnnotationConfigDispatcherServletInitializer {
        @Override
        protected Class<?>[] getRootConfigClasses() {
            return new Class[]{WebConfig.class};
        }

        @Override
        protected Class<?>[] getServletConfigClasses() {
            return new Class[]{};
        }

        @Override
        protected String[] getServletMappings() {
            return new String[]{"/"};
        }
    }

