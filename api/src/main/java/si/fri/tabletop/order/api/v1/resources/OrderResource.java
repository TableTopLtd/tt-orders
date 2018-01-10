package si.fri.tabletop.order.api.v1.resources;

import com.kumuluz.ee.logs.cdi.Log;
import si.fri.tabletop.order.models.Order;
import si.fri.tabletop.order.services.OrderBean;
import si.fri.tabletop.order.services.config.RestProperties;

import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.Histogram;
import org.eclipse.microprofile.metrics.Meter;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Metric;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@ApplicationScoped
@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Log
public class OrderResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private OrderBean orderBean;

    @Inject
    private RestProperties restProperties;

    @Inject
    @Metric(name = "order_getting_meter")
    private Meter addMeter;

    @GET
    public Response getOrders() {
        addMeter.mark();
        List<Order> orders = orderBean.getOrders(uriInfo);

        return Response.ok(orders).build();
    }

    @GET
    @Timed(name = "long_lasting_method")
    @Path("/{orderId}")
    public Response getOrder(@PathParam("orderId") String orderId) {

        Order order = orderBean.getOrder(orderId);

        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(order).build();
    }

    @GET
    @Path("/check")
    public Response checkService(){
        return Response.status(Response.Status.OK).build();
    }

    @POST
    public Response createOrder(Order order) {
        order = orderBean.createOrder(order);

        if (order.getId() != null) {
            return Response.status(Response.Status.CREATED).entity(order).build();
        } else {
            return Response.status(Response.Status.CONFLICT).entity(order).build();
        }
    }

    @DELETE
    @Path("/{orderId}")
    public Response deletePlace(@PathParam("orderId") String placeId) {

        boolean deleted = orderBean.deleteOrder(placeId);

        if (deleted) {
            return Response.status(Response.Status.GONE).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /*@PUT
    @Path("{placeId}")
    public Response putPlace(@PathParam("placeId") String placeId, Place place) {

        place = placesBean.putPlace(placeId, place);

        if (place == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            if (place.getId() != null)
                return Response.status(Response.Status.OK).entity(place).build();
            else
                return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }



    @GET
    @Path("/config")
    public Response config() {
        String response =
                "{\n" +
                        "    \"endpointEnabled\": \"%b\"\n" +
                        "}";
        response = String.format(response, restProperties.isMenuServiceEnabled());
        return Response.ok(response).build();
    }*/

}
