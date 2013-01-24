package com.rooney.Mess;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketImpl;
import java.util.Arrays;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Sockets {

    /**
     * @param args
     */
    public static void main(String[] args) {
        if(args.length == 0) {
            runMultihomed(10000, "localhost", "134.64.166.23");
        } else {
            runMultihomed(Integer.valueOf(args[0]), Arrays.copyOfRange(args, 1, args.length)); //"localhost", "134.64.166.23");
        }
        System.out.println("main end");
    }

    public static void runMultihomed(final int listenPort, String... addresses) {
        for (final String hostName : addresses) {
            new Thread() {
                public void run() {
                    setName("Listening on " + hostName);
                    multihomed(hostName, listenPort);
                }
            }.start();
        }
    }

    public static void multihomed(String hostName, int listenPort) {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + "\tStart");
        ServerSocket ss;
        int backlog = 2;
        
        try {
//            ss = new ServerSocket(listenPort, backlog, InetAddress.getByName(hostName));
            ss = new ServerSocket();
            ss.bind(new InetSocketAddress(InetAddress.getByName(hostName), listenPort), backlog);
        } catch (IOException e1) {
            e1.printStackTrace();
            return;
        }
        
        try {
            System.out.println(threadName + "\taccepting: " + ss);

            Socket socket = ss.accept();
            System.out.println(threadName + "\taccepted from " + socket);
            dumpSocketVars(socket);
            
            socket.setSoTimeout(9991);
            socket.setReceiveBufferSize(9992);
            socket.setSendBufferSize(9993);
            socket.setKeepAlive(true);
            socket.setSoLinger(true, 9994);
            socket.setTrafficClass(24); //high throughput and low latency; 
            socket.setReuseAddress(true);
            socket.setTcpNoDelay(true);
            
            dumpSocketVars(socket);
            
            InputStream inputStream = socket.getInputStream();
            
            int i ;
            while((i = inputStream.read()) != 26) { //crtl-z
                System.out.print(hostName.charAt(hostName.length()-1) + ":" +(char)i + " ");
            }
            socket.close();
            ss.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        System.out.println("End");
    }

    public static void dumpSocketVars(Socket socket) throws SocketException {
        System.out.println("getSoTimeout(): " + socket.getSoTimeout());
        System.out.println("getReceiveBufferSize(): " + socket.getReceiveBufferSize());
        System.out.println("getSendBufferSize(): " + socket.getSendBufferSize());
        System.out.println("getKeepAlive(): " + socket.getKeepAlive());
        System.out.println("getSoLinger(): " + socket.getSoLinger());
        System.out.println("getTrafficClass(): " + socket.getTrafficClass());
        System.out.println("getReuseAddress(): " + socket.getReuseAddress());
        System.out.println("getTcpNoDelay(): " + socket.getTcpNoDelay());
    }
    
    public static void simpleAccept(int sleepTime, int listenPort) {
        System.out.println("Start");
        ServerSocket ss;
        try {
            ss = new ServerSocket(listenPort); 
        } catch (IOException e1) {
            e1.printStackTrace();
            return;
        }

        try {
            System.out.println("accepting");
            while (true) {
                Socket accept = ss.accept();
                System.out.println("accepted from " + accept);
                Thread.sleep(sleepTime);            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        System.out.println("End");
    }

    
    
}
