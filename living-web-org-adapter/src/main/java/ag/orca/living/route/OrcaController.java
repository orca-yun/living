package ag.orca.living.route;

import ag.orca.living.core.auth.AuthInfo;
import ag.orca.living.interceptors.AuthInfoThreadLocal;
import org.springframework.validation.annotation.Validated;

@Validated
public abstract class OrcaController {

    public Long getOrgId() {
        AuthInfo authInfo = AuthInfoThreadLocal.get();
        return authInfo.getOrgId();
    }


}
