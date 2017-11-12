package si.fri.tabletop.order.services;

import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.tabletop.order.services.config.RestProperties;
import si.fri.tabletop.order.models.Order;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class OrderBean {

    private Logger log = LogManager.getLogger(OrderBean.class.getName());

    @Inject
    private RestProperties restProperties;

    @Inject
    private EntityManager em;

    @Inject
    private OrderBean orderBean;

    private Client httpClient;

    // TODO: Change when we have config server
    //@Inject
    //@DiscoverService("tt-menus")
    //private Optional<String> baseUrl;
    private String baseUrl = "http://localhost:8081";

    @PostConstruct
    private void init() {
        httpClient = ClientBuilder.newClient();
    }

    public List<Order> getOrders(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery())
                .defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, Order.class, queryParameters);

    }

    public Order getOrder(String placeId) {

        Order order = em.find(Order.class, placeId);

        if (order == null) {
            throw new NotFoundException();
        }

        // TODO: Change when we have config server
        //if (restProperties.isMenuServiceEnabled()) {
        /*List<Menu> menus = orderBean.getMenus(placeId);
        order.setMenus(menus);*/
        //}

        return order;
    }

    public Order createPlace(Order order) {

        try {
            beginTx();
            em.persist(order);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return order;
    }

    public Order putPlace(String placeId, Order order) {

        Order c = em.find(Order.class, placeId);

        if (c == null) {
            return null;
        }

        try {
            beginTx();
            order.setId(c.getId());
            order = em.merge(order);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return order;
    }

    public boolean deletePlace(String placeId) {

        Order order = em.find(Order.class, placeId);

        if (order != null) {
            try {
                beginTx();
                em.remove(order);
                commitTx();
            } catch (Exception e) {
                rollbackTx();
            }
        } else
            return false;

        return true;
    }

    private void beginTx() {
        if (!em.getTransaction().isActive())
            em.getTransaction().begin();
    }

    private void commitTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().commit();
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().rollback();
    }
}
