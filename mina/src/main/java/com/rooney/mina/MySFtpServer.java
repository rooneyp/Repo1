package com.rooney.mina;

import java.util.Arrays;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.sshd.SshServer;
import org.apache.sshd.common.NamedFactory;
import org.apache.sshd.server.Command;
import org.apache.sshd.server.PasswordAuthenticator;
import org.apache.sshd.server.command.ScpCommandFactory;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.session.ServerSession;
import org.apache.sshd.server.sftp.SftpSubsystem;

public class MySFtpServer {

	public static void main(String[] args) throws Exception {
		new MySFtpServer().sftpServer();
	}

	public void sftpServer() throws Exception {
		SshServer sshd = SshServer.setUpDefaultServer();
		sshd.setPasswordAuthenticator(new MyPasswordAuthenticator());
		sshd.setPort(10022);
		sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider("hostkey.ser"));
		// sshd.setShellFactory(new ProcessShellFactory(new String[] {
		// "/bin/sh", "-i", "-l" }));
		sshd.setSubsystemFactories(Arrays.<NamedFactory<Command>> asList(new SftpSubsystem.Factory()));
		sshd.setCommandFactory(new ScpCommandFactory());
		sshd.start();
	}

	public class MyPasswordAuthenticator implements PasswordAuthenticator {
		public boolean authenticate(String username, String password, ServerSession session) {
			return true;
		}
	}

	// ignore
	private void apacheFtpServer() throws FtpException {
		FtpServerFactory serverFactory = new FtpServerFactory();

		ListenerFactory factory = new ListenerFactory();

		// set the port of the listener
		factory.setPort(2221);

		// replace the default listener
		serverFactory.addListener("default", factory.createListener());

		// start the server
		FtpServer server = serverFactory.createServer();

		server.start();
	}

}
