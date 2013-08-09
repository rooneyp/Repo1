package no.steria.embeddedcontainer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sql.DataSource;

import org.hsqldb.jdbc.jdbcDataSource;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.plus.naming.EnvEntry;
import org.mortbay.jetty.webapp.WebAppContext;

public class ServerControl {

    public static void main(String[] args) throws Exception {
        readPropertyFile("embeddedcontainer.properties");

        if (getBooleanProperty("server.checkStatus", false)) {
            boolean status = StatusHandler.checkStatus(getIntProperty("server.port", 8080));
            System.out.println(status ? "running" : "not running");
            System.exit(status ? 0 : -1);
        }

        if (getBooleanProperty("server.attemptShutdown", true)) {
            ShutdownHandler.attemptShutdown(getIntProperty("server.port", 8080), getShutdownCookie());
        }

        new EnvEntry("jdbc/foo", getDataSource("foo"));
        new EnvEntry("jdbc/bar", getDataSource("bar"));

        Server server = new Server(getIntProperty("server.port", 8080));
        server.addHandler(new WebAppContext(WarExtractor.extractWar("lib/embeddedcontainer-web-1.0-SNAPSHOT.war", "embeddedcontainer.war"), "/embeddedcontainer"));
        server.addHandler(new ShutdownHandler(server, getShutdownCookie()));
        server.addHandler(new StatusHandler());

        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private static DataSource getDataSource(String dsName) {
        String serverUrl = System.getProperty("datasource.url." + dsName, "jdbc:hsqldb:mem:" + dsName);
        String username = System.getProperty("datasource.username." + dsName, dsName);
        String password = System.getProperty("datasource.password." + dsName, username);

        jdbcDataSource dataSource = new jdbcDataSource();
        dataSource.setDatabase(serverUrl);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    private static boolean getBooleanProperty(String propertyName, boolean defaultValue) {
        return Boolean.parseBoolean(System.getProperty(propertyName, Boolean.toString(defaultValue)));
    }

    private static String getShutdownCookie() {
        return System.getProperty("server.shutdowncookie", "dsgnlsdnlaknoirnlsnvlenoi");
    }

    private static void readPropertyFile(String filename) {
        try {
            FileInputStream inputStream = new FileInputStream(filename);
            System.getProperties().load(inputStream);
        } catch (FileNotFoundException e) {
            System.err.println("Could not find property file " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getIntProperty(String propertyName, int defaultValue) {
        return Integer.parseInt(System.getProperty(propertyName, Integer.toString(defaultValue)));
    }

}
