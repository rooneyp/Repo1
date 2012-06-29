package com.rooney.mina;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.protobuf.ProtobufCodecFactory;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.rooney.pbuff.KitchenSinkProtos.KitchenSink;

public class GPBClient {

	public static class GPBClientSessionHandler extends IoHandlerAdapter {
		@Override
		public void sessionOpened(IoSession session) {
			// send summation requests
			for (int i = 0; i < 5; i++) {
				KitchenSink kitchenSink = KitchenSink.newBuilder().setMyFixed32(i).build();
				session.write(kitchenSink);
			}
		}

		@Override
		public void messageReceived(IoSession session, Object message) {
			System.out.println("Received " + message);

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
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(ProtobufCodecFactory.newInstance(KitchenSink.getDefaultInstance())));
		// connector.getFilterChain().addLast("logger", new LoggingFilter());

		connector.setHandler(new GPBClientSessionHandler());

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
