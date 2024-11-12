package ag.orca.living.provider.interact;

import ag.orca.living.api.interact.InteractShareOnlineService;
import ag.orca.living.common.CommonConvert;
import ag.orca.living.core.bo.OnlineUserInfo;
import ag.orca.living.core.convert.OnlineUserInfoConvert;
import ag.orca.living.core.repo.interact.ShareOnlineRepo;
import ag.orca.living.core.ro.PageRo;
import ag.orca.living.core.vo.interact.ShareOnlineVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@DubboService
public class InteractShareOnlineProvider implements InteractShareOnlineService {

    @Resource
    ShareOnlineRepo shareOnlineRepo;

    @Override
    public Pair<List<ShareOnlineVo>, Long> findPageShareOnlineList(Long roomId,
                                                                   PageRo ro) {
        int page = ro.getPage();
        int pageSize = ro.getPageSize();
        Long total = shareOnlineRepo.countShareOnline(roomId);
        List<ShareOnlineVo> vos = new ArrayList<>();
        if (total > 0) {
            long start = (long) (page - 1) * pageSize;
            if (start > total) {
                return Pair.of(vos, total);
            }
            long stop = (long) page * pageSize - 1;
            if (stop > total) {
                stop = total - 1;
            }
            List<OnlineUserInfo> authInfos = shareOnlineRepo.findPageShareOnlineList(roomId, start, stop);
            vos = CommonConvert.map(authInfos, OnlineUserInfoConvert::userInfoToShareOnline);
        }
        return Pair.of(vos, total);
    }

    @Override
    public Long totalShareOnline(Long roomId) {
        return shareOnlineRepo.countShareOnline(roomId);
    }
}
