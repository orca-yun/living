package ag.orca.living.api.stream;

import ag.orca.living.core.vo.stream.LivingE2eAddressVo;
import ag.orca.living.core.vo.stream.LivingStreamPullAddressVo;
import ag.orca.living.core.vo.stream.LivingStreamPushAddressVo;
import ag.orca.living.core.vo.stream.LivingStreamTransPullAddressVo;


public interface LivingStreamService {

    /**
     * 构造转码拉流地址
     *
     * @param roomId
     * @return
     */
    LivingStreamTransPullAddressVo buildTransPullAddress(Long roomId);

    /**
     * 构造拉流地址
     *
     * @param roomId
     * @return
     */
    LivingStreamPullAddressVo buildPullAddress(Long roomId);

    /**
     * 构造推流地址
     *
     * @param roomId
     * @return
     */
    LivingStreamPushAddressVo buildPushAddress(Long roomId);

    /**
     * 构造端到端地址
     *
     * @param orgId     企业ID
     * @param roomId    房间ID
     * @param channelId 渠道ID
     * @return
     */
    LivingE2eAddressVo buildE2eAddress(Long orgId, Long roomId, Long channelId);


}
