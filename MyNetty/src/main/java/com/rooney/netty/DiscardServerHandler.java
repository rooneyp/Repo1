package com.rooney.netty;

import java.util.concurrent.atomic.AtomicInteger;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Handles a server-side channel.
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter { // (1)
	AtomicInteger bytesRead = new AtomicInteger();
	
    public DiscardServerHandler(final int port) {;
	    new Thread() {
            public void run() {
            	try {
	                Thread.sleep(1000 * 60);
                } catch (InterruptedException e) {
	                e.printStackTrace();
                }
				System.out.println("Port=" + port + ", Read Bytes/Min=" + bytesRead.getAndSet(0));
            }
	    }.start();
    }

	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
        // Discard the received data silently.
    	bytesRead.getAndAdd(((ByteBuf) msg).readableBytes());
        ((ByteBuf) msg).release(); // (3)
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
    
    
}
