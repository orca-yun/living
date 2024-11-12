package ag.orca.living.core.repo.org;

import ag.orca.living.core.dao.org.OrgAdminMapper;
import ag.orca.living.core.entity.org.OrgAdmin;
import ag.orca.living.util.EncryptUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;


@Repository
public class OrgAdminRepo {

    @Resource
    OrgAdminMapper adminMapper;

    public Optional<OrgAdmin> findOrgAdminByAccount(String account) {
        return Optional.ofNullable(adminMapper.findOrgAdminByAccount(account));
    }

    @Transactional(rollbackFor = Exception.class)
    public void resetOrgAdminPwd(String account, String password) {
        Optional<OrgAdmin> optional = findOrgAdminByAccount(account);
        optional.ifPresent(oa -> {
            oa.setPassword(EncryptUtil.sha256Encrypt(password));
            adminMapper.resetOrgAdminPwdById(oa.getId(), oa.getPassword(), LocalDateTime.now());
        });
    }


}
