package com.dante.passec;



import com.dante.passec.config.HibernateConfig;
import com.dante.passec.config.MainConfig;
import com.dante.passec.config.WebConfig;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

@Configuration
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
@ComponentScan
public class Rest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Rest.class, args);
    }
    @Bean
    public ServletRegistrationBean root() {
        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(WebConfig.class);
        dispatcherServlet.setApplicationContext(applicationContext);
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(dispatcherServlet, "/*");
        servletRegistrationBean.addUrlMappings( "/*.htm");
        servletRegistrationBean.setName("root");
        return servletRegistrationBean;
    }
}


    /*public static void main(String[] args) throws Exception {
        Server server = new Server(8090);
        WebAppContext context = new WebAppContext("src/main/webapp", "/test");

        context.addEventListener(new ContextLoaderListener(getContext()));
        context.addServlet(getDefaultServlet(), "/");
        server.setHandler(context);
        server.start();
    }
    private static ServletHolder getDefaultServlet(){
        ServletHolder servletHolder = new ServletHolder("test-dispatcher", new DispatcherServlet(getContext()));
        servletHolder.setAsyncSupported(true);
        servletHolder.setInitOrder(1);
        return servletHolder;
    }
    private static WebApplicationContext getContext(){
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(WebConfig.class, HibernateConfig.class, MainConfig.class);
        return context;
    }*/
