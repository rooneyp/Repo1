package no.steria.embeddedcontainer.web;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

public class WebServer {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8088);
        server.addHandler(new WebAppContext("src/main/webapp", "/embeddedcontainer"));
        server.start();
    }
}
