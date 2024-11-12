package ag.orca.living.core.convert;

import ag.orca.living.core.entity.org.OrgAccount;
import ag.orca.living.core.vo.org.OrgAccountVo;

import java.util.Objects;

public class OrgAccountConvert {

    public static OrgAccountVo entityToVo(OrgAccount entity) {
        return Objects.isNull(entity) ? null :
                OrgAccountVo.builder()
                        .id(entity.getId())
                        .orgId(entity.getOrgId())
                        .balance(entity.getBalance())
                        .status(entity.getStatus())
                        .build();
    }
}
