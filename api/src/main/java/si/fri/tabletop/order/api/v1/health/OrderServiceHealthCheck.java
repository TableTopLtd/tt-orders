package si.fri.tabletop.order.api.v1.health;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import si.fri.tabletop.order.services.config.RestProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;


@Health
@ApplicationScoped
public class OrderServiceHealthCheck implements HealthCheck {

    @Inject
    private RestProperties restProperties;

    @Override
    public HealthCheckResponse call(){

        if(restProperties.isHealthy()){
            return HealthCheckResponse.named(OrderServiceHealthCheck.class.getSimpleName()).up().build();
        }
        else {
            return HealthCheckResponse.named(OrderServiceHealthCheck.class.getSimpleName()).down().build();
        }
    }
}
