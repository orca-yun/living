package ag.orca.living.core.dao.org;

import ag.orca.living.core.entity.org.OrgSensitiveWord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrgSensitiveWordMapper {
    List<OrgSensitiveWord> findListByOrgId(@Param("orgId") Long orgId);

    int updateWordsById(@Param("id") Long id, @Param("words") String words);


    int logicDel(@Param("orgId") Long orgId,
                 @Param("ids") List<Long> ids,
                 @Param("dateTime") LocalDateTime dateTime);

    List<OrgSensitiveWord> findListByOrgIdAndWordsLike(@Param("orgId") Long orgId, @Param("words") String words);

    int batchInsert(@Param("sensitives") List<OrgSensitiveWord> sensitives);
}
