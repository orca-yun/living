package ag.orca.living.api.room;

import ag.orca.living.core.vo.room.LivingRoomShareVo;
import ag.orca.living.core.vo.stream.LivingKeyDecryptVo;
import org.apache.commons.lang3.tuple.Pair;

public interface LivingRoomExtendService {

    /**
     * 根据加密KEY解码房间信息
     *
     * @param key 加密KEY
     * @return
     */
    Pair<LivingKeyDecryptVo, LivingRoomShareVo> decryptToRoomInfo(String key);


    LivingKeyDecryptVo decryptToVo(String key);

}
