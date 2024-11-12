package ag.orca.living.core.convert;

import ag.orca.living.core.bo.OnlineUserInfo;
import ag.orca.living.core.vo.interact.AssiOnlineVo;
import ag.orca.living.core.vo.interact.ShareOnlineVo;

public class OnlineUserInfoConvert {

    public static ShareOnlineVo userInfoToShareOnline(OnlineUserInfo info) {
        return ShareOnlineVo.builder()
                .uid(info.getUid())
                .roomId(info.getRoomId())
                .nickname(info.getNickname())
                .build();
    }

    public static AssiOnlineVo userInfoToAssiOnline(OnlineUserInfo info) {
        return AssiOnlineVo.builder()
                .uid(info.getUid())
                .roomId(info.getRoomId())
                .nickname(info.getNickname())
                .build();
    }
}
