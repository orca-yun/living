package ag.orca.living.route;


import ag.orca.living.common.OrcaResult;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static ag.orca.living.common.OrcaConst.SPLIT;

@RestController
@Hidden
public class HealthController {

    @Value("${spring.application.name:-}")
    private String appName;

    @GetMapping(value = {"/", "/health"})
    public OrcaResult<String> health() {
        return OrcaResult.success("GoodLuck!" + appName + SPLIT + System.currentTimeMillis());
    }
}
