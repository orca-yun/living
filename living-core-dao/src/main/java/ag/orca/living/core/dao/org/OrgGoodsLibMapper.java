package ag.orca.living.core.dao.org;

import ag.orca.living.core.entity.org.OrgGoodsLib;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrgGoodsLibMapper {
    List<OrgGoodsLib> findListByOrgId(@Param("orgId") Long orgId);

    int insert(OrgGoodsLib lib);

    int logicDel(@Param("orgId") Long orgId,
                 @Param("ids") List<Long> ids,
                 @Param("dateTime") LocalDateTime dateTime);

    int update(OrgGoodsLib lib);

    List<OrgGoodsLib> findListByOrgIdAndNameLike(@Param("orgId") Long orgId, @Param("name") String name);

    List<OrgGoodsLib> findListByIds(@Param("ids") List<Long> ids);

    OrgGoodsLib findSameNameGoods(Long orgId, String name);
}
