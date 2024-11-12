package ag.orca.living.core.repo.shortdomain;

import ag.orca.living.common.CommonConvert;
import ag.orca.living.common.OrcaAssert;
import ag.orca.living.core.dao.org.OrgShortDomainMapper;
import ag.orca.living.core.dao.sys.SysShortDomainMapper;
import ag.orca.living.core.entity.org.OrgShortDomain;
import ag.orca.living.core.entity.sys.SysShortDomain;
import ag.orca.living.util.I18nUtil;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Repository
public class ShortDomainRepo {

    @Resource
    SysShortDomainMapper sysShortDomainMapper;

    @Resource
    OrgShortDomainMapper orgShortDomainMapper;


    public String randomShortDomain(Long orgId) {
        List<String> domains = allShortDomain(orgId);
        int index = ThreadLocalRandom.current().nextInt(domains.size());
        return domains.get(index);
    }

    List<String> allShortDomain(Long orgId) {
        List<OrgShortDomain> orgShortDomains = orgShortDomainMapper.findAllDomainByOrgId(orgId);
        if (CollectionUtils.isNotEmpty(orgShortDomains)) {
            return CommonConvert.map(orgShortDomains, OrgShortDomain::getDomain);
        }
        List<SysShortDomain> domains = sysShortDomainMapper.findAllDomain();
        OrcaAssert.match(CollectionUtils.isEmpty(domains), I18nUtil.getMessage("sys.short.domain.empty"));
        return CommonConvert.map(domains, SysShortDomain::getDomain);
    }


}
