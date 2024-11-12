package ag.orca.living.provider.base;


import ag.orca.living.api.base.OrgAccountService;
import ag.orca.living.common.CommonConvert;
import ag.orca.living.core.convert.OrgAccountConvert;
import ag.orca.living.core.convert.OrgAccountRecordConvert;
import ag.orca.living.core.entity.org.OrgAccount;
import ag.orca.living.core.entity.org.OrgAccountRecord;
import ag.orca.living.core.repo.org.OrgAccountRepo;
import ag.orca.living.core.ro.query.QueryAccountRecordRo;
import ag.orca.living.core.vo.org.OrgAccountRecordVo;
import ag.orca.living.core.vo.org.OrgAccountVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;
import java.util.Optional;

@Slf4j
@DubboService
public class OrgAccountProvider implements OrgAccountService {


    @Resource
    OrgAccountRepo orgAccountRepo;


    @Override
    public Optional<OrgAccountVo> findOrgAccountByOrgId(Long orgId) {
        Optional<OrgAccount> optional = orgAccountRepo.findOrgAccountByOrgId(orgId);
        return optional.map(OrgAccountConvert::entityToVo);
    }

    @Override
    public Pair<List<OrgAccountRecordVo>, Long> findAccountRecordPageList(Long orgId, QueryAccountRecordRo ro) {
        PageHelper.startPage(ro.getPage(), ro.getPageSize());
        List<OrgAccountRecord> records = orgAccountRepo.findAccountRecordListByOrgIdAndRecordType(orgId, ro.getRecordType());
        PageInfo<OrgAccountRecord> pageInfo = new PageInfo<>(records);
        List<OrgAccountRecordVo> vos = CommonConvert.map(pageInfo.getList(), OrgAccountRecordConvert::entityToVo);
        return Pair.of(vos, pageInfo.getTotal());
    }
}
