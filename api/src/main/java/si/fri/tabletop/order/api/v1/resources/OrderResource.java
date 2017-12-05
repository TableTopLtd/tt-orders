package si.fri.tabletop.order.api.v1.resources;

import si.fri.tabletop.order.models.Order;
import si.fri.tabletop.order.services.OrderBean;
import si.fri.tabletop.order.services.config.RestProperties;

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
public class OrderResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private OrderBean orderBean;

    @Inject
    private RestProperties restProperties;

    @GET
    public Response getOrders() {

        List<Order> orders = orderBean.getOrders(uriInfo);

        return Response.ok(orders).build();
    }

    @GET
    @Path("/{orderId}")
    public Response getOrder(@PathParam("orderId") String placeId) {

        Order order = orderBean.getOrder(placeId);

        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(order).build();
    }

    /*@POST
    public Response createOrder(Order order) {

        if (order.get() == null || order.getTitle().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            place = placesBean.createPlace(place);
        }

        if (place.getId() != null) {
            return Response.status(Response.Status.CREATED).entity(place).build();
        } else {
            return Response.status(Response.Status.CONFLICT).entity(place).build();
        }
    }

    @PUT
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

    @DELETE
    @Path("{placeId}")
    public Response deletePlace(@PathParam("placeId") String placeId) {

        boolean deleted = placesBean.deletePlace(placeId);

        if (deleted) {
            return Response.status(Response.Status.GONE).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }*/

    @GET
    @Path("/config")
    public Response config() {
        String response =
                "{\n" +
                        "    \"endpointEnabled\": \"%b\"\n" +
                        "}";
        response = String.format(response, restProperties.isMenuServiceEnabled());
        return Response.ok(response).build();
    }

}
