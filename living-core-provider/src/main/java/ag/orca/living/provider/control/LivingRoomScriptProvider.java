package ag.orca.living.provider.control;

import ag.orca.living.api.control.LivingRoomScriptService;
import ag.orca.living.common.CommonConvert;
import ag.orca.living.core.convert.ControlScriptConvert;
import ag.orca.living.core.entity.control.ControlScript;
import ag.orca.living.core.repo.control.LivingRoomScriptRepo;
import ag.orca.living.core.ro.control.AddScriptRo;
import ag.orca.living.core.ro.control.UpdateScriptRo;
import ag.orca.living.core.ro.query.QueryScriptRo;
import ag.orca.living.core.vo.control.ControlScriptVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

/**
 * 
 * @date 2024-04-26
 */
@DubboService
@RequiredArgsConstructor
@Slf4j
public class LivingRoomScriptProvider implements LivingRoomScriptService {

    private final LivingRoomScriptRepo livingRoomScriptRepo;

    @Override
    public void addScript(AddScriptRo ro) {
        livingRoomScriptRepo.addScript(ro);
    }

    @Override
    public Pair<List<ControlScriptVo>, Long> findPageList(QueryScriptRo ro) {
        PageHelper.startPage(ro.getPage(), ro.getPageSize());
        List<ControlScript> scripts = livingRoomScriptRepo.listByRoomId(ro.getRoomId());
        PageInfo<ControlScript> pageInfo = new PageInfo<>(scripts);
        List<ControlScriptVo> vos = CommonConvert.map(pageInfo.getList(), ControlScriptConvert::entityToVo);
        return Pair.of(vos, pageInfo.getTotal());
    }

    @Override
    public void remove(List<Long> ids) {
        if (isNotEmpty(ids)) {
            livingRoomScriptRepo.remove(ids);
        }
    }

    @Override
    public void saveScriptAndSend(UpdateScriptRo ro) {
        livingRoomScriptRepo.saveScriptAndSend(ro);
    }

    @Override
    public void saveScript(UpdateScriptRo ro) {
        livingRoomScriptRepo.saveScript(ro);
    }

    @Override
    public void batchToSend(List<Long> ids) {
        if (isNotEmpty(ids)) {
            livingRoomScriptRepo.batchToSend(ids);
        }
    }
}
