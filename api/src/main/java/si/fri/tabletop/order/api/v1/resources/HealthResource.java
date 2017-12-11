package si.fri.tabletop.order.api.v1.resources;

import com.kumuluz.ee.common.runtime.EeRuntime;
import com.kumuluz.ee.logs.cdi.Log;
import si.fri.tabletop.order.services.config.RestProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

@Path("health")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
@Log
public class HealthResource {

    private Logger log = Logger.getLogger(HealthResource.class.getName());

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
    public Response setHealth(Boolean healthy){
        log.info("Setting health to " + healthy);
        restProperties.setHealthy(healthy);
        return Response.ok().build();
    }
}
