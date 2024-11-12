package ag.orca.living.core.dao.media;

import ag.orca.living.core.entity.media.LivingMediaLib;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface LivingMediaLibMapper {

    List<LivingMediaLib> findListByOrgId(@Param("orgId") Long orgId);

    int insert(LivingMediaLib mediaLib);

    int logicDel(@Param("orgId") Long orgId, @Param("ids") List<Long> ids, @Param("dateTime") LocalDateTime dateTime);

    int update(LivingMediaLib lib);

    List<LivingMediaLib> findListByOrgIdAndNameLike(@Param("orgId") Long orgId, @Param("name") String name);

    LivingMediaLib findById(@Param("id") Long id);


    Long totalStorage(@Param("orgId") Long orgId);

    LivingMediaLib findSameNameMedia(Long orgId, String name);
}
