package ag.orca.living.provider.room;

import ag.orca.living.api.room.LivingRoomExtendService;
import ag.orca.living.core.repo.room.manage.LivingRoomManage;
import ag.orca.living.core.vo.room.LivingRoomShareVo;
import ag.orca.living.core.vo.stream.LivingKeyDecryptVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboService;

@Slf4j
@DubboService
public class LivingRoomExtendProvider implements LivingRoomExtendService {

    @Resource
    LivingRoomManage livingRoomManage;

    @Override
    public Pair<LivingKeyDecryptVo, LivingRoomShareVo> decryptToRoomInfo(String key) {
        return livingRoomManage.decryptToRoomInfo(key);
    }

    @Override
    public LivingKeyDecryptVo decryptToVo(String key) {
        return livingRoomManage.decryptKeyToVo(key);
    }


}
