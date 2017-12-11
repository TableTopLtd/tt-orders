package si.fri.tabletop.order.services.config;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ConfigBundle("rest-properties")
public class RestProperties {

    @ConfigValue(value = "external-services.menu-service.enabled", watch = true)
    private boolean menuServiceEnabled;

    private boolean healthy;

    public boolean isHealthy() {
        return healthy;
    }
    
    public boolean isMenuServiceEnabled() {
        return menuServiceEnabled;
    }

    public void setMenuServiceEnabled(boolean menuServiceEnabled) {
        this.menuServiceEnabled = menuServiceEnabled;
    }

    public void setHealthy(boolean healthy) {
        this.healthy = healthy;
    }

}
