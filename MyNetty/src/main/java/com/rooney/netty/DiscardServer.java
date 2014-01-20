package com.rooney.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
    
/**
 * Discards any incoming data.
 */
public class DiscardServer {
    
    private static int[] ports;
    private static int rcvbuf = -1;
    
    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // set num threads
        EventLoopGroup workerGroup = new NioEventLoopGroup(); // set num threads
        try {
            ServerBootstrap b = new ServerBootstrap(); // helper
            b.group(bossGroup, workerGroup) //
             .channel(NioServerSocketChannel.class) // also has Old io ver
             .childHandler(new ChannelInitializer<SocketChannel>() { // special handler to allow init of pipeline of handlers
                 public void initChannel(SocketChannel ch) throws Exception {
                     ch.pipeline().addLast(new DiscardServerHandler(ch.localAddress().getPort())); //add a handler to the channel handler pipeline
                     System.out.println(ch + " - SO_RCVBUF: " + ch.config().getReceiveBufferSize());
                 }
             })
             .option(ChannelOption.SO_BACKLOG, 128)          // option for serversocket accepting connections
             .childOption(ChannelOption.SO_KEEPALIVE, true); // option for worker sockers serving connections
            
            if(rcvbuf > 0) {
            	b.childOption(ChannelOption.SO_RCVBUF, rcvbuf);
            }
    
            List<ChannelFuture> channelFutures = new ArrayList<ChannelFuture>();
            for (int port : ports) {
              channelFutures.add(b.bind(port));
            }
            
            for (ChannelFuture channelFuture : channelFutures) {
              channelFuture.sync();
              channelFuture.channel().closeFuture().sync();
            }
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
    
    public static void main(String[] args) throws Exception {
    	final Options options = createCommandLineOptions();
    	
    	if (args.length > 0) {
    		CommandLine cl = new PosixParser().parse(options, args, false);
    		
    		if(cl.hasOption("h")) {
    			new HelpFormatter().printHelp("DiscardServer", options);
    			return;
    		}
    		
    		if(cl.hasOption("b")) {
    			rcvbuf = Integer.parseInt(cl.getOptionValue("b"));
    		}
    		
    		if(cl.hasOption("p")) {
    			String[] stringPorts = cl.getOptionValue("p").split(",");
    		
    			ports = new int[stringPorts.length];
	            for (int j = 0; j < stringPorts.length; j++) {
	              ports[j] = Integer.parseInt(stringPorts[j].trim());
	            }
            	new DiscardServer().run();
    		}
        } else {
        	 new HelpFormatter().printHelp("DiscardServer", options);
        }
    }
    
    private static Options createCommandLineOptions() {
        final Options options = new Options();
        options.addOption(new Option("p", "ports", true, "comma separated list of one or more ports to listen on (no spaces)"));
        options.addOption(new Option("b", "rcvbuf", true, "receive buffer size or worker socket"));
        options.addOption(new Option("h", "help", false, "display help"));
        return options;
    }
}