package com.rooney.mina;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class EchoClient {

	public static class EchoClientSessionHandler extends IoHandlerAdapter {
		@Override
		public void sessionOpened(IoSession session) {
			// send summation requests
			for (int i = 0; i < 5; i++) {
				session.write("hello" + i);
			}
		}

		@Override
		public void messageReceived(IoSession session, Object message) {
			String str = (String) message;
			System.out.println("client received: " + str);
			if ("quit".equals(str)) {
				session.close(true);
			}

		}

		@Override
		public void exceptionCaught(IoSession session, Throwable cause) {
			session.close(true);
		}
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		NioSocketConnector connector = new NioSocketConnector();

		connector.setConnectTimeoutMillis(1000);
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
		// connector.getFilterChain().addLast("logger", new LoggingFilter());

		connector.setHandler(new EchoClientSessionHandler());

		IoSession session;
		for (;;) {
			try {
				ConnectFuture future = connector.connect(new InetSocketAddress("localhost", 9123));
				future.awaitUninterruptibly();
				session = future.getSession();
				break;
			} catch (RuntimeIoException e) {
				System.err.println("Failed to connect.");
				e.printStackTrace();
				Thread.sleep(5000);
			}
		}

		// wait until the summation is done
		session.getCloseFuture().awaitUninterruptibly();

		connector.dispose();
	}

}
