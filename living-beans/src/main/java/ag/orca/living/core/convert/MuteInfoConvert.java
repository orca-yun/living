package ag.orca.living.core.convert;

import ag.orca.living.core.bo.MuteUserInfo;
import ag.orca.living.core.ro.interact.MuteUserRo;
import ag.orca.living.core.vo.interact.MuteUserVo;

import java.util.Objects;

public class MuteInfoConvert {
    public static MuteUserVo infoToVo(MuteUserInfo info) {
        return Objects.isNull(info) ? null : MuteUserVo.builder()
                .uid(info.getUid())
                .nickname(info.getNickname())
                .roomId(info.getRoomId())
                .build();
    }

    public static MuteUserInfo roToInfo(MuteUserRo ro) {
        return Objects.isNull(ro) ? null : MuteUserInfo.builder()
                .uid(ro.getUid())
                .nickname(ro.getNickname())
                .roomId(ro.getRoomId())
                .build();
    }
}
