package ag.orca.living.core.convert;

import ag.orca.living.core.entity.org.OrgAccount;
import ag.orca.living.core.entity.org.OrgAdmin;
import ag.orca.living.core.entity.org.Organization;
import ag.orca.living.core.enums.AccountStatusEnum;
import ag.orca.living.core.ro.org.OrganizationEditRo;
import ag.orca.living.core.ro.org.OrganizationRo;
import ag.orca.living.core.vo.org.OrganizationVo;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Objects;

public class OrganizationConvert {

    public static Triple<Organization, OrgAdmin, OrgAccount> roToOrgEntity(OrganizationRo ro, Long orgId, Long accountId, String orgCode) {
        Organization organization = Organization.builder()
                .id(orgId)
                .orgLogo(ro.getOrgLogo())
                .orgRoomBg(ro.getOrgRoomBg())
                .name(ro.getName())
                .orgCode(orgCode)
                .notice(ro.getNotice())
                .storagePrice(ro.getStoragePrice())
                .livingPrice(ro.getLivingPrice())
                .videoPrice(ro.getVideoPrice())
                .build();
        OrgAdmin orgAdmin = OrgAdmin.builder().orgId(orgId).account(ro.getAccount()).password(ro.getPwd()).build();
        OrgAccount account = OrgAccount.builder().id(accountId).orgId(orgId).balance(0L).status(AccountStatusEnum.NORMAL.getCode()).build();
        return Triple.of(organization, orgAdmin, account);
    }

    public static OrganizationVo entityToVo(Organization entity) {
        return Objects.isNull(entity) ? null : OrganizationVo.builder()
                .id(entity.getId())
                .name(entity.getName())
                .orgCode(entity.getOrgCode())
                .orgLogo(entity.getOrgLogo())
                .orgRoomBg(entity.getOrgRoomBg())
                .notice(entity.getNotice())
                .build();

    }

    public static Organization editRoToEntity(OrganizationEditRo ro) {
        return Organization.builder()
                .id(ro.getId())
                .orgLogo(ro.getOrgLogo())
                .orgRoomBg(ro.getOrgRoomBg())
                .name(ro.getName())
                .notice(ro.getNotice())
                .build();
    }
}
