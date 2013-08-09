Getting started:

1. svn checkout http://svn.brodwall.com/demo/embedded-container
2. cd .../embedded-container
3. mvn eclipse:eclipse (or idea:idea, or whatever)
4. Import the project into Eclipse
5. Start the class no.steria.embeddedcontainer.web.WebServer as a main class
   (Notice that you can start it in the debugger as well)
6. Point your web browser to http://localhost:8088/embeddedcontainer/

Testing (Running time < 2 seconds):

1-4. As above
5. Run no.steria.embeddedcontainer.web.HelloWebTest in embeddedcontainer-web as a JUnit test

Deploying

1-2. As above
3. mvn install
5. copy .../embedded-container/embedded-container-server/target/embeddedcontainer-server-1.0-SNAPSHOT.one-jar.jar
	to the physical application server or to a local directory on your machine or whereever
6. Start the server with java -jar embeddedcontainer-server-1.0-SNAPSHOT.one-jar.jar
7. You can override properties in the server by creating an embeddedcontainer.properties file
	in the current working directory where you start the server.
	(Which properties are available is currently undocumented - see the source code)
	
