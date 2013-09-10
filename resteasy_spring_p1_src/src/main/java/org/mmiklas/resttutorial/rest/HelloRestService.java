package org.mmiklas.resttutorial.rest;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.mmiklas.resttutorial.model.HelloMessage;
import org.mmiklas.resttutorial.model.HelloResponse;
import org.mmiklas.resttutorial.server.HelloSpringService;

@Named
@Path("/Hello")
public class HelloRestService {
	
//    public HelloRestService() {
//    	System.err.println("CREATING HelloRestService instance " + this);
//	}

	@Inject
    private HelloSpringService halloService;

    // curl http://localhost:8080/resteasy_spring_p1/rest/Hello/text?msg=Hi%20There
    @GET
    @Path("text")
    @Produces(MediaType.APPLICATION_FORM_URLENCODED)
    public Response sayTextHello(@QueryParam("msg") String msg) {
        String resp = halloService.sayTextHello(msg);
        return Response.ok(resp).build();
    }

    // *NIX: curl -X POST -H "Content-Type: application/json" -d '{"msg":"Hi There","gender":"MALE"}' http://localhost:8080/resteasy_spring_p1/rest/Hello/javabean
    //  WIN: curl -i -X POST -H "Content-Type: application/json" -d "{\"msg\":\"Hi There\",\"gender\":\"MALE\"}"  http://localhost:8080/resteasy_spring_p1/rest/Hello/javabean
    //  WIN: curl -i -X POST -H "Content-Type: application/json" -d @\msg.txt  http://134.64.166.12:8080/resteasy_spring_p1/rest/Hello/javabean
    @POST
    @Path("javabean")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response sayJavaBeanHello(HelloMessage msg) {
        HelloResponse resp = halloService.sayJavaBeanHello(msg);
        return Response.ok(resp).build();
    }
}
