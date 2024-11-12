package ag.orca.living.core.convert;

import ag.orca.living.common.CommonConvert;
import ag.orca.living.core.entity.org.OrgSensitiveGroup;
import ag.orca.living.core.entity.org.OrgSensitiveWord;
import ag.orca.living.core.ro.org.OrgBatchSensitiveWordRo;
import ag.orca.living.core.vo.org.OrgSensitiveWordVo;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Objects;

public class SensitiveWordConvert {

    public static List<OrgSensitiveWord> batchRoToEntity(OrgBatchSensitiveWordRo ro) {
        Long orgId = ro.getOrgId();
        return CommonConvert.map(ro.getWords(), s -> OrgSensitiveWord.builder()
                .orgId(orgId)
                .words(s).build());
    }

    public static OrgSensitiveWordVo entityToVo(OrgSensitiveWord entity) {
        return Objects.isNull(entity) ? null
                : OrgSensitiveWordVo.builder()
                .id(entity.getId())
                .orgId(entity.getOrgId())
                .words(entity.getWords())
                .build();
    }

    public static OrgSensitiveGroup buildGroup(Long orgId, List<OrgSensitiveWord> words) {
        return CollectionUtils.isEmpty(words)
                ? null
                : OrgSensitiveGroup.builder()
                .orgId(orgId)
                .words(CommonConvert.map(words, OrgSensitiveWord::getWords)).build();
    }
}
