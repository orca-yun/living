package ag.orca.living.core.dao.org;

import ag.orca.living.core.entity.org.OrgExemptWord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrgExemptWordMapper {
    List<OrgExemptWord> findListByOrgId(@Param("orgId") Long orgId);

    int updateWordsById(@Param("id") Long id, @Param("name") String name, @Param("words") String words);

    int insert(OrgExemptWord exemptWord);

    int logicDel(@Param("orgId") Long orgId, @Param("ids") List<Long> ids, @Param("dateTime") LocalDateTime dateTime);

    List<OrgExemptWord> findListByOrgIdAndNameLike(@Param("orgId") Long orgId, @Param("name") String name);

    OrgExemptWord findById(@Param("id") Long id);
}
