package com.rooney.Mess;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketImpl;

public class Sockets {

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Start");
        ServerSocket ss;
        try {
            int listenPort = 10000;
            ss = new ServerSocket(listenPort); 
        } catch (IOException e1) {
            e1.printStackTrace();
            return;
        }

        try {
            System.out.println("accepting");
            Socket accept = ss.accept();
            System.out.println("accepted from " + accept);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        System.out.println("End");
    }

}
