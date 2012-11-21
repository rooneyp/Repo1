package com.rooney.LearnNIO;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Shows sender blocking when client doesn't read data. Uses blocking IO
 * @author prooney
 *
 */
public class BlockingIOClientAndServer {
    public static void main(String[] args) throws Exception {
        new Thread(new Server(1111), "Server").start();
        Thread.sleep(1 * 1000);
        new Thread(new Client(1111), "Client").start();
    }


    public static class Client implements Runnable {
        private int serverPort;

        public Client(int serverPort) {
            this.serverPort = serverPort;
        }

        public void run() {
            try {
                Socket socket = new Socket("localhost", serverPort);
                //socket.setSoTimeout(5); only for reading
                System.out.println("send buffer size is " + socket.getSendBufferSize());
//                socket.setSendBufferSize(socket.getSendBufferSize() * 2);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                System.out.println("Client sending");
                String x = "Hello";
                out.println(x);
                
                int numCharsToSend = 1000;
                char[] charsToSend = new char[numCharsToSend];
                for (int i = 0; i < numCharsToSend; i++) {
                    charsToSend[i] = 1;
                }
                
                System.out.println("Client sleeping ");
                for (int i = 0; i < 1000; i++) {
                    Thread.sleep(1 * 1000);
                    System.out.println("Client sending chars of size: " + numCharsToSend + ", loop: " + i);
                    out.println(charsToSend);
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            } 
            System.out.println("Client Finishing");
        }
    }
    
    public static class Server implements Runnable {
        int serverPort;
        
        public Server(int serverPort) {
            this.serverPort = serverPort;
        }

        public void run() {
            try {
                ServerSocket ss;
                ss = new ServerSocket(serverPort);
                int connectionCount = 0;
                while (true) {
                    System.out.println("Server Waiting for connection on serverPort: " + ss.getLocalPort());
                    Socket accept = ss.accept();
                    System.out.println("Server Connection made");

                    Thread t = new Thread(new ThreadedSocketHandler(accept), "Handler " + (++connectionCount));
                    t.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class ThreadedSocketHandler implements Runnable {
        private final Socket socket;
        private boolean halt = false;

        /**
         * The handler to call for each KPIMessages that is read from the socket.
         */
        public ThreadedSocketHandler(Socket socket) {
            super();
            this.socket = socket;
        }

        public void run() {
            System.out.println("Server Handler Thread [" + Thread.currentThread().getName() + "] started");
            try {
                while (!halt) {
                    InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
                    char[] chars = new char[5];
                    inputStreamReader.read(chars);
                    System.out.println("Server Handler read : " + String.copyValueOf(chars));
                    System.out.println("Server Sleeping: to find out what happens when to client when it keeps sending");
                    Thread.sleep(200 * 1000);
                    
                    // ReadableByteChannel newChannel = Channels.newChannel(socket.getInputStream());
                }
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
