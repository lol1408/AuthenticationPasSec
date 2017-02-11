package com.dante.passec;


import com.dante.passec.config.HibernateConfig;
import com.dante.passec.config.MainConfig;
import com.dante.passec.config.WebConfig;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8090);
        getContext();
        WebAppContext context = new WebAppContext("src/main/webapp", "/");
        context.addEventListener(new ContextLoaderListener(getContext()));
        context.addServlet(getDefaultServlet(), "/");
        server.setHandler(context);
        server.start();
        server.join();
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
    }
}
