package ag.orca.living.core.convert;

import ag.orca.living.core.vo.org.OrganizationVo;
import ag.orca.living.core.vo.room.LivingRoomShareVo;
import ag.orca.living.core.vo.share.LiveShareExtVo;
import ag.orca.living.core.vo.share.LiveShareVo;

public class LivingRoomShareConvert {

    public static LiveShareVo build(Long orgId,
                                    Long roomId,
                                    Long channelId,
                                    LivingRoomShareVo roomShareVo,
                                    OrganizationVo organization) {
        LiveShareVo shareVo = LiveShareVo.builder()
                .orgId(orgId)
                .roomId(roomId)
                .channelId(channelId)
                .orgName(organization.getName())
                .orgLogo(organization.getOrgLogo())
                .build();
        shareVo.setRoom(roomShareVo);
        return shareVo;
    }

    public static LiveShareExtVo shareVoToExtVo(LiveShareVo shareVo,
                                                Long totalShare,
                                                OrganizationVo organizationVo) {
        return LiveShareExtVo.builder()
                .livingStatus(shareVo.getRoom().getStatus())
                .roomId(shareVo.getRoomId())
                .orgId(shareVo.getOrgId())
                .orgName(shareVo.getOrgName())
                .orgLogo(shareVo.getOrgLogo())
                .channelId(shareVo.getChannelId())
                .room(shareVo.getRoom())
                .totalOnlineUser(totalShare)
                .notice(organizationVo.getNotice())
                .build();
    }
}
