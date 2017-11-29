package si.fri.tabletop.order.api.v1.resources;

import com.kumuluz.ee.common.runtime.EeRuntime;
import com.sun.org.apache.xpath.internal.operations.Bool;
import si.fri.tabletop.order.api.v1.configuration.RestProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("health")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class HealthResource {

    @Inject
    private RestProperties restProperties;

    @GET
    @Path("instanceid")
    public Response getInstanceId(){
        String instanceId = "{\"instanceId\" : \"" + EeRuntime.getInstance().getInstanceId() + "\"}";

        return Response.ok(instanceId).build();
    }

    @POST
    @Path("healthy")
    public Response setHealth(String healthy){
        restProperties.setHealthy(true);
        return Response.ok().build();
    }
}
