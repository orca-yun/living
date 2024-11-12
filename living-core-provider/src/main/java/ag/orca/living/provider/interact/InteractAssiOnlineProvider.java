package ag.orca.living.provider.interact;

import ag.orca.living.api.interact.InteractAssiOnlineService;
import ag.orca.living.common.CommonConvert;
import ag.orca.living.core.bo.OnlineUserInfo;
import ag.orca.living.core.convert.OnlineUserInfoConvert;
import ag.orca.living.core.repo.interact.AssiOnlineRepo;
import ag.orca.living.core.vo.interact.AssiOnlineVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

@Slf4j
@DubboService
public class InteractAssiOnlineProvider implements InteractAssiOnlineService {


    @Resource
    AssiOnlineRepo assiOnlineRepo;

    @Override
    public List<AssiOnlineVo> findAssiOnlineList(Long roomId) {
        List<OnlineUserInfo> infos = assiOnlineRepo.findAllAssiOnlineList(roomId);
        return CommonConvert.map(infos, OnlineUserInfoConvert::userInfoToAssiOnline);
    }
}
