package ag.orca.living.core.dao.org;

import ag.orca.living.core.entity.org.OrgAccountRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrgAccountRecordMapper {

    int insert(OrgAccountRecord record);

    List<OrgAccountRecord> findListByOrgIdAndRecordType(@Param("orgId") Long orgId,
                                                        @Param("recordType") Integer recordType);

    List<OrgAccountRecord> findListByAccountIdAndRecordType(@Param("accountId") Long accountId,
                                                            @Param("recordType") Integer recordType);
}
