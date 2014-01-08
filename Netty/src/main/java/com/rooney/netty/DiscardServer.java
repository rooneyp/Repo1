package com.rooney.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
    
/**
 * Discards any incoming data.
 */
public class DiscardServer {
    
    private int port;
    
    public DiscardServer(int port) {
        this.port = port;
    }
    
    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // set num threads
        EventLoopGroup workerGroup = new NioEventLoopGroup(); // set num threads
        try {
            ServerBootstrap b = new ServerBootstrap(); // helper
            b.group(bossGroup, workerGroup) //
             .channel(NioServerSocketChannel.class) // also has Old io ver
             .childHandler(new ChannelInitializer<SocketChannel>() { // special handler to allow init of pipeline of handlers
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
                     ch.pipeline().addLast(new DiscardServerHandler()); //add a handler to the channel handler pipeline
                     System.out.println(ch);
                     System.out.println(ch.config().getReceiveBufferSize());
                 }
             })
             .option(ChannelOption.SO_BACKLOG, 128)          // option for serversocket accepting connections
             .childOption(ChannelOption.SO_KEEPALIVE, true); // option for worker sockers serving connections
//             .childOption(ChannelOption.SO_RCVBUF, 146809);
    
            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync(); // (7)
    
            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
    
    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        new DiscardServer(port).run();
    }
}