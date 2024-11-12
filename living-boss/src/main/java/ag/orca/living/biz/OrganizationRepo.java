package ag.orca.living.biz;

import ag.orca.living.api.seq.LivingSeqService;
import ag.orca.living.common.CommonConvert;
import ag.orca.living.common.OrcaAssert;
import ag.orca.living.core.convert.ChannelInfoConvert;
import ag.orca.living.core.convert.OrgGiftLibConvert;
import ag.orca.living.core.convert.OrgRobotConvert;
import ag.orca.living.core.convert.OrganizationConvert;
import ag.orca.living.core.dao.channel.ChannelInfoMapper;
import ag.orca.living.core.dao.org.*;
import ag.orca.living.core.dao.sys.SysGiftMapper;
import ag.orca.living.core.dao.sys.SysRobotMapper;
import ag.orca.living.core.entity.channel.ChannelInfo;
import ag.orca.living.core.entity.org.*;
import ag.orca.living.core.entity.sys.SysGift;
import ag.orca.living.core.entity.sys.SysRobot;
import ag.orca.living.core.ro.org.OrganizationRo;
import ag.orca.living.util.EncryptUtil;
import ag.orca.living.util.I18nUtil;
import ag.orca.living.util.RandomUtil;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class OrganizationRepo {

    @Resource
    OrgAdminMapper adminMapper;

    @Resource
    OrganizationMapper organizationMapper;

    @Resource
    OrgAccountMapper orgAccountMapper;

    @Resource
    SysRobotMapper sysRobotMapper;

    @Resource
    OrgRobotMapper orgRobotMapper;

    @Resource
    SysGiftMapper sysGiftMapper;

    @Resource
    OrgGiftLibMapper orgGiftLibMapper;

    @Resource
    ChannelInfoMapper channelInfoMapper;

    @DubboReference
    LivingSeqService seqService;


    @Transactional(rollbackFor = Exception.class)
    public void saveOrganization(OrganizationRo ro) {
        if (Objects.nonNull(ro)) {
            Optional<OrgAdmin> optional = Optional.ofNullable(adminMapper.findOrgAdminByAccount(ro.getAccount()));
            OrcaAssert.match(optional.isPresent(), I18nUtil.getMessage("account.exists"));
            Long orgId = seqService.nextId();
            Long accountId = seqService.nextId();
            String orgCode = seqService.uuid();
            Triple<Organization, OrgAdmin, OrgAccount> triple = OrganizationConvert.roToOrgEntity(ro, orgId, accountId, orgCode);
            saveOrganization(triple);
        }
    }

    private void saveOrganization(Triple<Organization, OrgAdmin, OrgAccount> triple) {
        Organization organization = triple.getLeft();
        organizationMapper.insert(organization);
        OrgAdmin admin = triple.getMiddle();
        admin.setPassword(EncryptUtil.sha256Encrypt(admin.getPassword()));
        adminMapper.insert(admin);

        OrgAccount account = triple.getRight();
        orgAccountMapper.insert(account);

        List<SysRobot> robots = sysRobotMapper.findAll();
        List<OrgRobot> orgRobots = CommonConvert.map(robots, s -> OrgRobotConvert.sysToEntity(s, organization.getId(), seqService.uuid()));
        if (CollectionUtils.isNotEmpty(orgRobots)) {
            orgRobotMapper.batchInsert(orgRobots);
        }

        List<SysGift> gifts = sysGiftMapper.findAll();
        List<OrgGiftLib> orgGifts = CommonConvert.map(gifts, s -> OrgGiftLibConvert.sysToEntity(s, organization.getId()));
        if (CollectionUtils.isNotEmpty(orgGifts)) {
            orgGiftLibMapper.batchInsert(orgGifts);
        }
        Long channelId;
        do {
            channelId = RandomUtil.randomId();
        } while (channelInfoMapper.existsChannelId(channelId) != 0);
        ChannelInfo channelInfo = ChannelInfoConvert.buildDefaultChannel(organization.getId(), "默认渠道", channelId);
        channelInfoMapper.insert(channelInfo);
    }

}
