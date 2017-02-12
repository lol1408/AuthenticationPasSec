package com.dante.passec;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
@ComponentScan
public class Rest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Rest.class, args);
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
