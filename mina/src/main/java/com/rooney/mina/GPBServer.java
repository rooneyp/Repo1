package com.rooney.mina;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.mina.filter.codec.protobuf.ProtobufCodecFactory;

import com.rooney.pbuff.KitchenSinkProtos.KitchenSink;

public class GPBServer {
	private static final int PORT = 9123;

	/**
	 * The server implementation. It's based on TCP, and uses a logging filter
	 * plus a text line decoder.
	 * @throws Exception 
	 */
	public static void main(String[] args) throws IOException, Exception {
		gpbServer();
	}
	

	private static void gpbServer() throws Exception {
		IoAcceptor acceptor = new NioSocketAcceptor();
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(ProtobufCodecFactory.newInstance(KitchenSink.getDefaultInstance())));

		acceptor.setHandler(new GPBHandler());
		
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		
		acceptor.bind(new InetSocketAddress(PORT));	
	}

	public static class GPBHandler extends IoHandlerAdapter {
		@Override
		public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
			cause.printStackTrace();
		}

		@Override
		public void messageReceived(IoSession session, Object message) throws Exception {
			System.out.println("Message received " + message);
			session.write(message);
		}

		@Override
		public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
			System.out.println("IDLE " + session.getIdleCount(status));
		}
	}	
	


}
