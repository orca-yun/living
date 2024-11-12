package ag.orca.living.core.event;

import ag.orca.living.core.BaseBean;
import ag.orca.living.core.vo.room.LivingRoomMarketGiftItemVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class GiftSendEvent extends BaseBean {


    /**
     * 房间
     */
    private Long roomId;

    /**
     * 发送人Uid
     */
    private String senderUid;

    /**
     * 发送人昵称
     */
    private String senderName;


    /**
     * 发送人类型
     */
    private Integer senderType;


    /**
     * 发送人头像
     */
    private String senderHeadIco;

    /**
     * 送出的礼物
     */
    private LivingRoomMarketGiftItemVo giftItem;
}
