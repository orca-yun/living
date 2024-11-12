package ag.orca.living.core.dao.org;

import ag.orca.living.core.entity.org.OrgGiftLib;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrgGiftLibMapper {
    List<OrgGiftLib> findListByOrgId(Long orgId);

    int insert(OrgGiftLib lib);

    int logicDel(@Param("orgId") Long orgId,
                 @Param("ids") List<Long> ids,
                 @Param("dateTime") LocalDateTime dateTime);

    int update(OrgGiftLib lib);

    OrgGiftLib findById(@Param("id") Long id);

    List<OrgGiftLib> findListByOrgIdAndNameLike(@Param("orgId") Long orgId, @Param("name") String name);

    int batchInsert(@Param("gifts") List<OrgGiftLib> gifts);

    OrgGiftLib findSameNameGift(@Param("orgId") Long orgId, @Param("name") String name);
}
