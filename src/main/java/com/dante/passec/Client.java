package com.dante.passec;



import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class Client {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8090);
        WebAppContext context = new WebAppContext("src/main/webapp", "/");
        server.setHandler(context);
        server.start();
    }
}
