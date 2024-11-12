package ag.orca.living.core.convert;

import ag.orca.living.core.bo.BlacklistUserInfo;
import ag.orca.living.core.ro.interact.BlacklistUserRo;
import ag.orca.living.core.vo.interact.BlacklistUserVo;

import java.util.Objects;

public class BlacklistInfoConvert {

    public static BlacklistUserVo infoToVo(BlacklistUserInfo info) {
        return Objects.isNull(info)
                ? null
                : BlacklistUserVo.builder()
                .uid(info.getUid())
                .nickname(info.getNickname())
                .roomId(info.getRoomId())
                .build();
    }

    public static BlacklistUserInfo roToInfo(BlacklistUserRo ro) {
        return Objects.isNull(ro)
                ? null
                : BlacklistUserInfo.builder()
                .uid(ro.getUid())
                .nickname(ro.getNickname())
                .roomId(ro.getRoomId())
                .build();
    }
}
