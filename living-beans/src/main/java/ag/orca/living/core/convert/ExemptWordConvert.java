package ag.orca.living.core.convert;

import ag.orca.living.core.entity.org.OrgExemptGroup;
import ag.orca.living.core.entity.org.OrgExemptWord;
import ag.orca.living.core.ro.org.OrgExemptWordRo;
import ag.orca.living.core.vo.org.OrgExemptWordVo;

import java.util.Objects;

public class ExemptWordConvert {

    public static OrgExemptWord roToEntity(OrgExemptWordRo ro) {
        return Objects.isNull(ro) ? null : OrgExemptWord.builder()
                .id(ro.getId())
                .orgId(ro.getOrgId())
                .name(ro.getName())
                .words(ro.getWords())
                .build();
    }

    public static OrgExemptWordVo entityToVo(OrgExemptWord entity) {
        return Objects.isNull(entity) ? null
                : OrgExemptWordVo.builder()
                .id(entity.getId())
                .orgId(entity.getOrgId())
                .name(entity.getName())
                .words(entity.getWordList())
                .build();
    }

    public static OrgExemptGroup buildGroup(OrgExemptWord word) {
        return Objects.isNull(word) ? null : OrgExemptGroup.builder()
                .id(word.getId())
                .name(word.getName())
                .orgId(word.getOrgId())
                .words(word.getWordList())
                .build();
    }
}
