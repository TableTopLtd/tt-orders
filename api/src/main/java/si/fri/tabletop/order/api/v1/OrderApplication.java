package si.fri.tabletop.order.api.v1;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import com.kumuluz.ee.discovery.annotations.RegisterService;

@ApplicationPath("/v1")
@RegisterService
public class OrderApplication extends Application {
}
