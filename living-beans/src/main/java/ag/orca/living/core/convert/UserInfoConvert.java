package ag.orca.living.core.convert;

import ag.orca.living.core.vo.wx.WxUserStateInfoVo;

public class UserInfoConvert {

    /**
     * Triple<RoomID, OrgId, ChannelId> Pair<Random, platform>
     *
     * @param parts
     * @return
     */
    public static WxUserStateInfoVo buildStateVo(String[] parts) {
        return WxUserStateInfoVo.builder()
                .roomId(Long.parseLong(parts[0]))
                .orgId(Long.parseLong(parts[1]))
                .channelId(Long.parseLong(parts[2]))
                .random(Long.parseLong(parts[3]))
                .platform(Integer.parseInt(parts[4]))
                .build();
    }
}
