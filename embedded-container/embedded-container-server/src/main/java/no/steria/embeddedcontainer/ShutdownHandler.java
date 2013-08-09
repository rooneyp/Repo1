package no.steria.embeddedcontainer;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.jetty.HttpException;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.AbstractHandler;

public class ShutdownHandler extends AbstractHandler {

    private static Logger logger = Logger.getLogger(ShutdownHandler.class.getName());

    private final String shutdownCookie;

    private final Server server;

    public ShutdownHandler(Server server, String shutdownCookie) {
        this.server = server;
        this.shutdownCookie = shutdownCookie;
    }

    public void handle(String target, HttpServletRequest request, HttpServletResponse response,
            int dispatch) throws IOException, ServletException {
        if (!target.equals("/shutdown")) {
            return;
        }

        if (!request.getMethod().equals("POST")) {
            throw new HttpException(HttpServletResponse.SC_BAD_REQUEST);
        }
        if (request.getParameter("cookie") == null || !request.getParameter("cookie").equals(shutdownCookie)) {
            throw new HttpException(HttpServletResponse.SC_UNAUTHORIZED);
        }

        logger.info("Shutting down");

        try {
            server.stop();
            System.exit(0);
        } catch (Exception e) {
            throw new RuntimeException("Shutting down server", e);
        }
    }

    public static void attemptShutdown(int port, String shutdownCookie) {
        try {
            URL url = new URL("http://localhost:" + port + "/shutdown?cookie=" + shutdownCookie);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.getResponseCode();
            logger.info("Shutting down " + url + ": " + connection.getResponseMessage());
        } catch (SocketException e) {
            logger.fine("Not running");
            // Okay - the server is not running
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
